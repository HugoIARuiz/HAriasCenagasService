
package com.HAriasCenagasService.Repository;

import com.HAriasCenagasService.JPA.NodoComEntrega;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface NodoComEntregaRepository extends JpaRepository<NodoComEntrega, Long> {
    
    @Query("select idNodoEntrega from UGTP_TBL_NODOCOMENTREGA where nombre = :nombre")
    Long buscarIdByNombre(String nombre);
}
