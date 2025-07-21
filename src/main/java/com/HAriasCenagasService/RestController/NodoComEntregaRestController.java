package com.HAriasCenagasService.RestController;

import com.HAriasCenagasService.JPA.NodoComEntrega;
import com.HAriasCenagasService.JPA.Result;
import com.HAriasCenagasService.Service.NodoComEntregaService;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/nodoComEntregaApi")
public class NodoComEntregaRestController {

    @Autowired
    private NodoComEntregaService nodoComEntregaService;

    @PostMapping("/CargaMasiva/procesar")
    public ResponseEntity Procesar(@RequestBody String absolutePath) {
        Result result = new Result();
        try {
            List<NodoComEntrega> listaNodo = new ArrayList<>();
            listaNodo = LecturaArchivoExcel(new File(absolutePath));
            for (NodoComEntrega nodoComEntrega : listaNodo) {
                nodoComEntregaService.AddNodoEntrega(nodoComEntrega);
            }
            result.correct = true;
        } catch (Exception ex) {
            result.correct = false;
            result.objects = null;
        }

        return ResponseEntity.ok(result);
    }

    public List<NodoComEntrega> LecturaArchivoExcel(File archivo) {
        Set<String> nodoUnico = new HashSet<>();
        List<NodoComEntrega> listaNodos = new ArrayList<>();
        try (XSSFWorkbook workbook = new XSSFWorkbook(archivo);) {
            for (Sheet sheet : workbook) {
                for (Row row : sheet) {
                    NodoComEntrega nodo = new NodoComEntrega();
                    String nodoNombre = row.getCell(5).getStringCellValue();
                    if (!nodoUnico.contains(nodoNombre)) {
                        nodoUnico.add(nodoNombre);
                        nodo.setNombre(nodoNombre);
                        nodo.setDescripcion(row.getCell(6).toString());
                        listaNodos.add(nodo);
                    }
                }

            }

        } catch (Exception ex) {
            listaNodos = null;
        }
        return listaNodos;
    }
    
    @GetMapping("/nodoEntrega")
    public ResponseEntity<List<NodoComEntrega>> getAll(){
        try {
            Result result = nodoComEntregaService.getAllNodoEntrega();
            if (!result.correct || result.objects == null || result.objects.isEmpty()) {
                return ResponseEntity.status(204).build();
            }
            List<NodoComEntrega> nodos = result.objects.stream()
                    .map(obj -> (NodoComEntrega) obj)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(nodos);
        } catch (Exception ex) {
            return ResponseEntity.status(500).body(null);
        }
    }
}
