
package com.HAriasCenagasService.JPA;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Table;

@Entity(name = "UGTP_TBL_NODOCOMRECEPCION")
@Getter
@Setter
public class NodoComRecepcion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idnodorecepcion")
    private Integer idNodoRecepcion;
    @Column(name = "nombre")
    private String nombreNodoRecepcion;
    @Column(name = "descripcion")
    private String descripcionNRecepcion;
    
}
