
package com.HAriasCenagasService.JPA;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Table;

@Entity(name = "UGTP_TBL_ZONATARIFA")
@Getter 
@Setter
public class ZonaTarifa {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idzonatarifa")
    private Integer idZonaTarifa;
    @Column(name = "nombre")
    private String nombreZona;
    
}
