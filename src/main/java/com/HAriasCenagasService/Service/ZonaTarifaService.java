
package com.HAriasCenagasService.Service;

import com.HAriasCenagasService.JPA.Result;
import com.HAriasCenagasService.JPA.ZonaTarifa;
import com.HAriasCenagasService.Repository.ZonaTarifaRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ZonaTarifaService {
    
    @Autowired
    private ZonaTarifaRepository zonaTarifaRepository;
    
    public Result AddZona(ZonaTarifa zonaTarifa){
        Result result = new Result();
        try {
            zonaTarifa.setNombre(zonaTarifa.getNombre());
            zonaTarifaRepository.save(zonaTarifa);
            result.correct = true;
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }
    
    public Result getAllZona(){
        Result result = new Result();
        result.objects = new ArrayList<>();
        
        try {
            List<ZonaTarifa> zonas = zonaTarifaRepository.findAll();
            if(!zonas.isEmpty()){
                result.objects.addAll(zonas.stream().map(z -> (Object) z).toList());
                result.correct = true;
            }else{
                result.correct = false;
                result.errorMessage = "No se encontraron Zonas Tarifa";
                
            }
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
            
        }
        
        return result;
    }
}
