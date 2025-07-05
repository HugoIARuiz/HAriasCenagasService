
package com.HAriasCenagasService.DAO;

import com.HAriasCenagasService.JPA.Result;
import com.HAriasCenagasService.JPA.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UsuarioDAOImplementation implements IUsuarioDAO {
    
    @Autowired
    private EntityManager entityManager;

    @Transactional
    @Override
    public Result Add(Usuario usuario) {
        Result result = new Result();
        try {
            entityManager.persist(usuario);
            result.correct = true;
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }

    @Override
    public Result GetAll() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    
}
