package com.HAriasCenagasService.Repository;


import com.HAriasCenagasService.JPA.Usuario;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;




public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
    
      Optional<Usuario> findByNombre(String nombre);
      
      @Query("select idUsuario from UGTP_TBL_USUARIO where nombre = :nombre")
      Long buscarIdByNombre(String nombre);
  
    
}
