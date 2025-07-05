package com.HAriasCenagasService.RestController;

import com.HAriasCenagasService.DAO.NodoComEntregaDAOImplementation;
import com.HAriasCenagasService.JPA.NodoComEntrega;
import com.HAriasCenagasService.JPA.Result;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/nodoComEntregaApi")
public class NodoComEntregaRestController {

    @Autowired
    private NodoComEntregaDAOImplementation nodoComEntregaDAOImplementation;

    @PostMapping("/CargaMasiva/procesar")
    public ResponseEntity Procesar(@RequestBody String absolutePath) {
        Result result = new Result();
        try {
            List<NodoComEntrega> listaNodo = new ArrayList<>();
            listaNodo = LecturaArchivoExcel(new File(absolutePath));
            for (NodoComEntrega nodoComEntrega : listaNodo) {
                nodoComEntregaDAOImplementation.Add(nodoComEntrega);
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
                        nodo.setNombreNodoEntrega(nodoNombre);
                        nodo.setDescripcionNEntrega(row.getCell(6).toString());
                        listaNodos.add(nodo);
                    }
                }

            }

        } catch (Exception ex) {
            listaNodos = null;
        }
        return listaNodos;
    }
}
