
package com.HAriasCenagasService.JPA;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Table;

@Entity
@Table(name = "UGTP_TBL_Tarifas")
@Getter
@Setter
public class Tarifas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idtarifa")
    private Integer idTarifa;
    @Column(name = "tarifaexcesofirme")
    private Double tarifaExcesoFirme;
    @Column(name = "tarifausoninterrumpible")
    private Double tarifaUsoIninterrumpible;
    @Column(name = "cargouso")
    private Double cargoUso;
    @Column(name = "cargagasexceso")
    private Double cargaGasExceso;
    @Column(name = "totalfacturar")
    private Double totalFacturar;
}
