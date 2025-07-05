
package com.HAriasCenagasService.JPA;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "UGTP_TBL_Medicion")
@Getter
@Setter
public class Medicion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idmedicion")
    private Integer idMedicion;
    @Column(name = "fecha")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fecha;
    @JoinColumn(name = "idcontrato")
    @ManyToOne
    public Contrato contrato;
    @JoinColumn(name = "idnodorecepcion")
    @ManyToOne
    public NodoComRecepcion nodoComRecepcion;
    @JoinColumn(name = "idnodoentrega")
    @ManyToOne
    public NodoComEntrega nodoComEntrega;
    @JoinColumn(name = "idtarifa")
    @OneToOne
    public Tarifas tarifas;
    @JoinColumn(name = "idzonatarifainyeccion")
    @ManyToOne
    public ZonaTarifa zonaTarifaInyeccion;
    @JoinColumn(name = "idzonatarifaextraccion")
    @ManyToOne
    public ZonaTarifa zonaTarifaExtraccion;
    @JoinColumn(name = "idcantidadentrega")
    @ManyToOne
    public CantidadEntrega cantidadEntrega;
    @JoinColumn(name = "idcantidadrecepcion")
    @ManyToOne
    public CantidadRecepcion cantidadRecepcion;
    @Column(name = "gasexceso")
    private Double gasExceso;
    
    
    
}
