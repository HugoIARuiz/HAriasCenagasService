package com.HAriasCenagasService.Service;

import com.HAriasCenagasService.JPA.Contrato;
import com.HAriasCenagasService.JPA.Factura;
import com.HAriasCenagasService.JPA.Result;
import com.HAriasCenagasService.Repository.ContratoRepository;
import com.HAriasCenagasService.Repository.FacturaRepository;
import jakarta.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FacturaService {

    @Autowired
    private FacturaRepository facturaRepository;
    @Autowired
    private ContratoRepository contratoRepository;
    
    @Transactional
    public Result AddFactura(Factura factura) {
        Result result = new Result();
        try {
            facturaRepository.save(factura);
            result.correct = true;
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }
    
    @Transactional
    public Result updateFactura(Long idFactura, Factura factura){
        Result result = new Result();
        try {
            Factura facturaExistente = facturaRepository.findById(idFactura).orElseThrow();
            facturaExistente.setFecha(factura.getFecha());
            Contrato contratoActualizado = contratoRepository.findById(factura.contrato.getIdContrato()).orElseThrow();
            facturaExistente.setContrato(contratoActualizado);
            facturaExistente.setCantidadnominadarecepcion(factura.getCantidadnominadarecepcion());
            facturaExistente.setCantidadasignadarecepcion(factura.getCantidadasignadarecepcion());
            facturaExistente.setCantidadnominadaentrega(factura.getCantidadnominadaentrega());
            facturaExistente.setCantidadasignadaentrega(factura.getCantidadasignadaentrega());
            facturaExistente.setGasexceso(factura.getGasexceso());
            facturaExistente.setTarifaexcesofirme(factura.getTarifaexcesofirme());
            facturaExistente.setTarifausoininterrumpible(factura.getTarifausoininterrumpible());
            facturaExistente.setCargouso(factura.getCargouso());
            facturaExistente.setCargagasexceso(factura.getCargagasexceso());
            facturaExistente.setTotalfacturar(factura.getTotalfacturar());
            
            facturaRepository.save(facturaExistente);
            result.object = facturaExistente;
            result.correct = true;
            
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }
    
     @Transactional
    public Factura getFacturaById(Long idFactura){
        return facturaRepository.findById(idFactura).orElse(null);
    }

    public Result getAllFactura() {
        Result result = new Result();
        result.objects = new ArrayList<>();

        try {
            List<Factura> facturas = facturaRepository.findAll();
            if (!facturas.isEmpty()) {
                result.objects.addAll(facturas.stream().sorted(Comparator.comparing(Factura::getIdFactura)).map(f -> (Object) f).toList());
                result.correct = true;
            } else {
                result.correct = false;
                result.errorMessage = "No se han encontrado facturas";
            }
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }
    
    
   
    

    public Result getAllDinamico(Factura factura) {
        Result result = new Result();
        result.objects = new ArrayList<>();

        try {
            List<Factura> facturas = facturaRepository.findAll();
            List<Factura> listaFacturas = facturas.stream()
                    .filter(f -> {
                        if (factura.getFecha() == null) {
                            return true;
                        }
                        LocalDate fechaBase = f.getFecha().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                        LocalDate fechaFiltro = factura.getFecha().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                        return fechaBase.equals(fechaFiltro);
                    })
                    .filter(f -> factura.contrato != null && factura.contrato.getIdContrato() > 0 ? Objects.equals(f.contrato.getIdContrato(), factura.contrato.getIdContrato()) : true)
                    .filter(f -> factura.contrato.usuario != null && factura.contrato.usuario.getIdUsuario() > 0 ? f.contrato.usuario.getIdUsuario() == factura.contrato.usuario.getIdUsuario() : true)
                    .collect(Collectors.toList());
            if (!facturas.isEmpty()) {
                result.objects.addAll(listaFacturas.stream().map(f -> (Object) f).toList());
                result.correct = true;
            } else {
                result.correct = false;
                result.errorMessage = "No se encontraron facturas.";
            }
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }

    public Result minFecha() {
    Result result = new Result();
    try {
        List<Factura> facturas = facturaRepository.findAll();
        if (facturas == null || facturas.isEmpty()) {
            return null;
        }
        Date minDate = facturas.stream().map(Factura::getFecha).min(Date::compareTo).get();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String soloFecha = sdf.format(minDate);

        result.object = soloFecha; 
        result.correct = true;

    } catch (Exception ex) {
        result.correct = false;
        result.errorMessage = ex.getLocalizedMessage();
        result.ex = ex;
    }

    return result;
}
    public Result maxFecha(){
        Result result = new Result();
        try {
            List<Factura> facturas = facturaRepository.findAll();
            if (facturas == null || facturas.isEmpty())  {
                return null;
            }
            Date maxDate = facturas.stream().map(Factura::getFecha).max(Date::compareTo).get();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String soloFecha = sdf.format(maxDate);
            result.object = soloFecha;
            result.correct = true;
            
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }
}
