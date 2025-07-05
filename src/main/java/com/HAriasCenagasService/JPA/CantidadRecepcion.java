
package com.HAriasCenagasService.JPA;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Table;

@Entity
@Table(name = "UGTP_TBL_CantidadRecepcion")
@Getter
@Setter
public class CantidadRecepcion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idcantidadrecepcion")
    private Integer idCantidadRecepcion;
    @JoinColumn(name = "idtipo")
    @ManyToOne
    public TipoCantidad tipoCantidad;
    @Column(name = "cantidad")
    private Double cantidad;
}
