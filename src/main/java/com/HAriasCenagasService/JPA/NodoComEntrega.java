
package com.HAriasCenagasService.JPA;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "UGTP_TBL_NODOCOMENTREGA")
@Getter
@Setter
public class NodoComEntrega {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idnodoentrega")
    private Integer idNodoEntrega;
    @Column(name = "nombre")
    private String nombreNodoEntrega;
    @Column(name = "descripcion")
    private String descripcionNEntrega;
    
}
