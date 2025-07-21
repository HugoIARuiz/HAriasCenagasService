
package com.HAriasCenagasService.RestController;


import com.HAriasCenagasService.JPA.Result;
import com.HAriasCenagasService.JPA.ZonaTarifa;
import com.HAriasCenagasService.Service.ZonaTarifaService;
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
@RequestMapping("/zonaTarifaApi")
public class ZonaTarifaRestController {
    
    @Autowired
    private ZonaTarifaService zonaTarifaService;
    
    @PostMapping("/CargaMasiva/procesar")
    public ResponseEntity Procesar(@RequestBody String absolutePath) {
        Result result = new Result();
        try {
            List<ZonaTarifa> listaZonaTarifa = new ArrayList<>();
            listaZonaTarifa = LecturaArchivoExcel(new File(absolutePath));
            for (ZonaTarifa zona : listaZonaTarifa) {
                zonaTarifaService.AddZona(zona);
            }
            result.correct = true;
        } catch (Exception ex) {
            result.correct = false;
            result.objects = null;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
            
        }

        return ResponseEntity.ok(result);
    }
    
    public List<ZonaTarifa> LecturaArchivoExcel(File archivo){
        Set<String> zonaUnica = new HashSet<>();
        List<ZonaTarifa> listaZona = new ArrayList<>();
        try (XSSFWorkbook workbook = new XSSFWorkbook(archivo)){
             for (Sheet sheet : workbook) {
                 for (Row row : sheet) {
                     ZonaTarifa zonaTarifa = new ZonaTarifa();
                     String zonaNombre = row.getCell(8).getStringCellValue();
                     if (!zonaUnica.contains(zonaNombre)) {
                         zonaUnica.add(zonaNombre);
                         zonaTarifa.setNombre(zonaNombre);
                         listaZona.add(zonaTarifa); 
                     }
                 }
            }
        } catch (Exception e) {
        }
        
        return listaZona;
        
    }
    
    @GetMapping("/zona")
    public ResponseEntity<List<ZonaTarifa>> getAll(){
        try {
            Result result = zonaTarifaService.getAllZona();
            if (!result.correct || result.objects == null || result.objects.isEmpty()) {
                return ResponseEntity.status(204).build();
            }
            List<ZonaTarifa> zonas = result.objects.stream()
                    .map(obj -> (ZonaTarifa) obj)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(zonas);
        } catch (Exception ex) {
            return ResponseEntity.status(500).body(null);
        }
    }
}
