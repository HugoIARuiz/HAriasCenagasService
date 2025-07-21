
package com.HAriasCenagasService.JPA;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "UGTP_TBL_CONTRATO")
@Getter
@Setter

public class Contrato {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idcontrato")
    private Long idContrato;
    
    @NotNull(message = "El nombre no puede ser nulo")
    @Size(min=1, max= 100, message = "Entre 1 y 100 caracteres")
    @Column(name = "nombre")
    private String nombre;
    
    @JoinColumn(name = "idusuario")
    @ManyToOne
    @JsonIgnoreProperties("contratos")
    public Usuario usuario;
    
    @JoinColumn(name = "idnodorecepcion")
    @ManyToOne
    public NodoComRecepcion nodoComRecepcion;
    
    @JoinColumn(name = "idnodoentrega")
    @ManyToOne
    public NodoComEntrega nodoComEntrega;
    
}
