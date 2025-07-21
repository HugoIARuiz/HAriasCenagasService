
package com.HAriasCenagasService.Repository;

import com.HAriasCenagasService.JPA.Contrato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface ContratoRepository extends JpaRepository<Contrato, Long>{
    @Query("select idContrato from UGTP_TBL_CONTRATO where nombre = :nombre")
    Long buscarIdByNombre(String nombre);
}
