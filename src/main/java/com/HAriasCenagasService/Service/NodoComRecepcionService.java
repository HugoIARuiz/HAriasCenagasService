
package com.HAriasCenagasService.Service;

import com.HAriasCenagasService.JPA.NodoComRecepcion;
import com.HAriasCenagasService.JPA.Result;
import com.HAriasCenagasService.Repository.NodoComRecepcionRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NodoComRecepcionService {
    
    @Autowired
    private NodoComRecepcionRepository nodoComRecepcionRepository;
    
    public Result AddNodoRecepcion(NodoComRecepcion nodoComRecepcion){
        Result result = new Result();
        
        try {
            nodoComRecepcionRepository.save(nodoComRecepcion);
            result.correct = true;
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage(); 
            result.ex = ex;
        }
        
        return result;
    }
    
    public Result getAllNodoRecepcion(){
        Result result = new Result();
        result.objects = new ArrayList<>();
        
        try {
            List<NodoComRecepcion> nodos = nodoComRecepcionRepository.findAll();
            if (!nodos.isEmpty()) {
                result.objects.addAll(nodos.stream().map(u -> (Object) u).toList());
                result.correct = true;
            }else{
                result.correct = false;
                result.errorMessage = "No se encontraron nodos de Recepción";
            }
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
            result.objects = null;
        }
        
        
        return result;
    }
}
