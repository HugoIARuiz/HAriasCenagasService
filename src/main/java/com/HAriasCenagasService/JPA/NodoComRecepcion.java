
package com.HAriasCenagasService.JPA;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "UGTP_TBL_NODOCOMRECEPCION")
@Getter
@Setter
public class NodoComRecepcion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idnodorecepcion")
    private Long idNodoRecepcion;
    @NotBlank(message = "Ingresa un nombre, ")
    @Size(min = 1, max = 50, message = "Entre 1 y 50 caracteres")
    @Column(name = "nombre")
    private String nombre;
    @NotBlank(message = "Ingresa una descripcion, ")
    @Size(min = 1, max = 50, message = "Entre 1 y 80 caracteres")
    @Column(name = "descripcion")
    private String descripcion;
    @JoinColumn(name = "idzonatarifainyeccion")
    @ManyToOne
    public ZonaTarifa zonaTarifa;
    
    
}
