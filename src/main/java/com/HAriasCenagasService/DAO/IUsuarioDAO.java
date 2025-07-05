
package com.HAriasCenagasService.DAO;

import com.HAriasCenagasService.JPA.Result;
import com.HAriasCenagasService.JPA.Usuario;


public interface IUsuarioDAO {
    Result Add(Usuario usuario);
    Result GetAll();
}
