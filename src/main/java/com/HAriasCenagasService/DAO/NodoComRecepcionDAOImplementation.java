
package com.HAriasCenagasService.DAO;

import com.HAriasCenagasService.JPA.NodoComRecepcion;
import com.HAriasCenagasService.JPA.Result;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class NodoComRecepcionDAOImplementation implements INodoComRecepcion{
    @Autowired
    private EntityManager entityManager;
    
    @Transactional
    @Override
    public Result Add(NodoComRecepcion nodoComRecepcion) {
        Result result = new Result();
        try {
            entityManager.persist(nodoComRecepcion);
            result.correct = true;
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        
        return result;
    }
    
    
}
