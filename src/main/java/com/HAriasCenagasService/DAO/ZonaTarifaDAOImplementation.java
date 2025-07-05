
package com.HAriasCenagasService.DAO;

import com.HAriasCenagasService.JPA.Result;
import com.HAriasCenagasService.JPA.ZonaTarifa;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ZonaTarifaDAOImplementation implements IZonaTarifa{
    @Autowired
    private EntityManager entityManager;
    @Transactional
    @Override
    public Result Add(ZonaTarifa zonaTarifa) {
        Result result = new Result();
        try {
            entityManager.persist(zonaTarifa);
            result.correct = true;
        } catch (Exception ex) {
        result.correct = false;
        result.errorMessage = ex.getLocalizedMessage();
        result.ex = ex;
        }
        return result;
    }
    
   
}
