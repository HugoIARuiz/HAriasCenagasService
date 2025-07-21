
package com.HAriasCenagasService.Repository;

import com.HAriasCenagasService.JPA.NodoComRecepcion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface NodoComRecepcionRepository  extends JpaRepository<NodoComRecepcion, Long>{
    
    @Query("select idNodoRecepcion from UGTP_TBL_NODOCOMRECEPCION where nombre = :nombre")
    Long buscarIdByNombre(String nombre);
}
