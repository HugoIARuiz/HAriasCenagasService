package com.HAriasCenagasService.RestController;

import com.HAriasCenagasService.JPA.Contrato;
import com.HAriasCenagasService.JPA.NodoComEntrega;
import com.HAriasCenagasService.JPA.NodoComRecepcion;
import com.HAriasCenagasService.JPA.Result;
import com.HAriasCenagasService.JPA.ResultFile;
import com.HAriasCenagasService.JPA.Usuario;
import com.HAriasCenagasService.Repository.NodoComEntregaRepository;
import com.HAriasCenagasService.Repository.NodoComRecepcionRepository;
import com.HAriasCenagasService.Repository.UsuarioRepository;
import com.HAriasCenagasService.Service.ContratoService;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("contratoApi")
public class ContratoRestController {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private NodoComEntregaRepository nodoComEntregaRepository;
    @Autowired
    private NodoComRecepcionRepository nodoComRecepcionRepository;
    @Autowired
    private ContratoService contratoService;

    @GetMapping("/contrato")
    public ResponseEntity<List<Contrato>> getAll() {
        try {
            Result result = contratoService.getAllContrato();
            if (!result.correct || result.objects == null || result.objects.isEmpty()) {
                return ResponseEntity.status(204).build();
            }
            List<Contrato> contratos = result.objects.stream()
                    .map(obj -> (Contrato) obj)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(contratos);
        } catch (Exception ex) {
            return ResponseEntity.status(500).body(null);
        }

    }
    
    @PostMapping("/add")
    public ResponseEntity ContratoAdd(@RequestBody Contrato contrato){
        try {
            Result result = contratoService.AddContrato(contrato);
            if (result.correct) {
                return ResponseEntity.ok(result);
            }else{
               return  ResponseEntity.badRequest().build();
            }
        } catch (Exception ex) {
            return ResponseEntity.status(500).body(null);
        }
    }
    
    @GetMapping("/{idContrato}")
    public ResponseEntity<Contrato> getContratoById(@PathVariable Long idContrato){
        Contrato contrato = contratoService.getContratoById(idContrato);
        if (contrato != null) {
            return ResponseEntity.ok(contrato);
        }else{
            return ResponseEntity.notFound().build();
        }
        
    }
    @PostMapping("/update")
    public ResponseEntity ContratoUpdate(@RequestBody Contrato contrato, @RequestParam Long idContrato){
        try {
            Result result = contratoService.updateContrato(idContrato, contrato);
            if (result.correct) {
                return ResponseEntity.ok(result);
            }else{
                return ResponseEntity.badRequest().build();
            }
        } catch (Exception ex) {
            return ResponseEntity.status(500).body(null);
        }
    }
    
    

    @PostMapping("/getAllDinamico")
    public ResponseEntity getAllDinamico(@RequestBody Contrato contrato) {
        try {
            Result result = contratoService.getAllDinamico(contrato);
            if (!result.correct || result.objects == null || result.objects.isEmpty()) {
                return ResponseEntity.status(204).body(null);
            }
            List<Contrato> contratos = result.objects.stream()
                    .map(obj -> (Contrato) obj)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(contratos);
        } catch (Exception ex) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @PostMapping("/CargaMasiva/procesar")
    public ResponseEntity Procesar(@RequestBody String absolutePath) {
        Result result = new Result();
        try {
            List<Contrato> listaContrato = new ArrayList<>();
            listaContrato = LecturaArchivoExcel(new File(absolutePath));
            for (Contrato contrato : listaContrato) {
                contratoService.AddContrato(contrato);
            }
            result.correct = true;
        } catch (Exception ex) {
            result.correct = false;
            result.objects = null;
        }
        return ResponseEntity.ok(result);
    }

    public List<ResultFile> ValidarArchivo(List<Contrato> listaContratos) {
        List<ResultFile> listaErrores = new ArrayList<>();
        if (listaContratos == null) {
            listaErrores.add(new ResultFile(0, "La lista es nula", "La lista es nula"));
        } else if (listaContratos.isEmpty()) {
            listaErrores.add(new ResultFile(0, "La lista está vacía", "La lista está vacía"));
        } else {
            int fila = 1;
            for (Contrato contrato : listaContratos) {
                if (contrato.getNombre() == null || contrato.getNombre().equals("")) {
                    listaErrores.add(new ResultFile(fila, contrato.getNombre(), "El nombre es un campo obligatorio"));
                }
                fila++;
            }
        }

        return listaErrores;
    }

    public List<Contrato> LecturaArchivoExcel(File archivo) {
        Set<String> contratoUnico = new HashSet<>();
        List<Contrato> listaContratos = new ArrayList<>();
        try (XSSFWorkbook workbook = new XSSFWorkbook(archivo);) {
            for (Sheet sheet : workbook) {
                for (Row row : sheet) {
                    Contrato contrato = new Contrato();
                    Usuario usuario = new Usuario();
                    NodoComRecepcion nodoComRecepcion = new NodoComRecepcion();
                    NodoComEntrega nodoComEntrega = new NodoComEntrega();
                    String contratoNombre = row.getCell(1).getStringCellValue();
                    String usuarioNombre = row.getCell(2).getStringCellValue();
                    String nodoRecepcionNombre = row.getCell(3).getStringCellValue();
                    String nodoEntregaNombre = row.getCell(5).getStringCellValue();
                    Long idUsuario = usuarioRepository.buscarIdByNombre(usuarioNombre);
                    Long idNodoRecepcion = nodoComRecepcionRepository.buscarIdByNombre(nodoRecepcionNombre);
                    Long idNodoEntrega = nodoComEntregaRepository.buscarIdByNombre(nodoEntregaNombre);
                    usuario.setIdUsuario(idUsuario);
                    nodoComRecepcion.setIdNodoRecepcion(idNodoRecepcion);
                    nodoComEntrega.setIdNodoEntrega(idNodoEntrega);
                    if (!contratoUnico.contains(contratoNombre)) {
                        contratoUnico.add(contratoNombre);
                        contrato.setNombre(contratoNombre);
                        contrato.setUsuario(usuario);
                        contrato.setNodoComRecepcion(nodoComRecepcion);
                        contrato.setNodoComEntrega(nodoComEntrega);
                        listaContratos.add(contrato);

                    }

                }
            }
        } catch (Exception e) {
            listaContratos = null;
        }
        return listaContratos;
    }

}
