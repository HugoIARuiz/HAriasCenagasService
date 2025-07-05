
package com.HAriasCenagasService.RestController;

import com.HAriasCenagasService.DAO.NodoComRecepcionDAOImplementation;
import com.HAriasCenagasService.JPA.NodoComRecepcion;
import com.HAriasCenagasService.JPA.Result;
import com.HAriasCenagasService.JPA.Usuario;
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
@RequestMapping("/nodoComRecepcionApi")
public class NodoComRecepcionRestController {
    @Autowired
    private NodoComRecepcionDAOImplementation nodoComRecepcionDAOImplementation;
    
    @PostMapping("/CargaMasiva/procesar")
    public ResponseEntity Procesar(@RequestBody String absolutePath){
        Result result = new Result();
        try {
            List<NodoComRecepcion> listaNodo = new ArrayList<>();
            listaNodo = LecturaArchivoExcel(new File(absolutePath));
            for (NodoComRecepcion nodoComRecepcion : listaNodo) {
                nodoComRecepcionDAOImplementation.Add(nodoComRecepcion);
            }
            result.correct = true;
        } catch (Exception ex) {
            result.correct = false;
            result.objects = null;
        }
        
        return ResponseEntity.ok(result);
    }
    
    public List<NodoComRecepcion> LecturaArchivoExcel(File archivo) {
        Set<String> nodoUnico = new HashSet<>();
        List<NodoComRecepcion> listaNodos = new ArrayList<>();
        try (XSSFWorkbook workbook = new XSSFWorkbook(archivo);) {
            for (Sheet sheet : workbook) {
                for (Row row : sheet) {
                    NodoComRecepcion nodo = new NodoComRecepcion();
                    String nodoNombre = row.getCell(3).getStringCellValue();
                    
                    if (!nodoUnico.contains(nodoNombre)) {
                        nodoUnico.add(nodoNombre);
                        nodo.setNombreNodoRecepcion(nodoNombre);
                        nodo.setDescripcionNRecepcion(row.getCell(4).toString());
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
