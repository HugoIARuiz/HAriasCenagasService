
package com.HAriasCenagasService.DAO;

import com.HAriasCenagasService.JPA.NodoComEntrega;
import com.HAriasCenagasService.JPA.Result;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class NodoComEntregaDAOImplementation implements INodoComEntrega{
    @Autowired
    private EntityManager entityManager;

    @Transactional
    @Override
    public Result Add(NodoComEntrega nodoComEntrega) {
        Result result = new Result();
        try {
            entityManager.persist(nodoComEntrega);
            result.correct = true;
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        
        return result;
    }
    
}
