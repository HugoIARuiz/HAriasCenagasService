
package com.HAriasCenagasService.JPA;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Entity(name = "UGTP_TBL_ZONATARIFA")
@Getter 
@Setter
public class ZonaTarifa {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idzonatarifa")
    private Integer idZonaTarifa;
    @NotBlank(message = "Ingresa un nombre, ")
    @Size(min = 3, max = 50, message = "Entre 3 y 50 caracteres")
    @Column(name = "nombre")
    private String nombre;
    
}
