
package com.HAriasCenagasService.JPA;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

@Entity(name = "UGTP_TBL_FACTURA")
@Getter
@Setter
public class Factura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idfactura")
    private Long idFactura;
    @Column(name = "fecha")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Past(message = "La fecha de factura debe estar en el pasado")
    private Date fecha;
    @JoinColumn(name = "idcontrato")
    @ManyToOne
    public Contrato contrato;
    @DecimalMin("0.00")
    @Column(name = "cantidadnominadarecepcion")
    private Double cantidadnominadarecepcion;
    @DecimalMin("0.00")
    @Column(name = "cantidadasignadarecepcion")
    private Double cantidadasignadarecepcion;
    @DecimalMin("0.00")
    @Column(name = "cantidadnominadaentrega")
    private Double cantidadnominadaentrega;
    @DecimalMin("0.00")
    @Column(name = "cantidadasignadaentrega")
    private Double cantidadasignadaentrega;
    @DecimalMin("0.00")
    @Column(name = "gasexceso")
    private Double gasexceso;
    @DecimalMin("0.00")
    @Column(name = "tarifaexcesofirme")
    private Double tarifaexcesofirme;
    @DecimalMin("0.00")
    @Column(name = "tarifausoininterrumpible")
    private Double tarifausoininterrumpible;
    @DecimalMin("0.00")
    @Column(name = "cargouso")
    private Double cargouso;
    @DecimalMin("0.00")
    @Column(name = "cargagasexceso")
    private Double cargagasexceso;
    @DecimalMin("0.00")
    @Column(name = "totalfacturar")
    private Double totalfacturar;
    
}
