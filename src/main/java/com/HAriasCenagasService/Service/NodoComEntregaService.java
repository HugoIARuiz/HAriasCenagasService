
package com.HAriasCenagasService.Service;

import com.HAriasCenagasService.JPA.NodoComEntrega;
import com.HAriasCenagasService.JPA.Result;
import com.HAriasCenagasService.Repository.NodoComEntregaRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NodoComEntregaService {
    @Autowired
    private NodoComEntregaRepository nodoComEntregaRepository;
    
    public Result AddNodoEntrega(NodoComEntrega nodoComEntrega){
        Result result = new Result();
        try {
            nodoComEntregaRepository.save(nodoComEntrega);
            result.correct = true;
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }
    
    public Result getAllNodoEntrega(){
        Result result = new Result();
        result.objects = new ArrayList<>();
        
        try {
            List<NodoComEntrega> nodos = nodoComEntregaRepository.findAll();
            if (!nodos.isEmpty()) {
                result.objects.addAll(nodos.stream().map(u -> (Object) u).toList());
                result.correct = true;
                
            }else{
                result.correct = false;
                result.errorMessage = "No se encontrarron nodos de Entrega";
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
