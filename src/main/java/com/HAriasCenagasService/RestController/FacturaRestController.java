package com.HAriasCenagasService.RestController;

import com.HAriasCenagasService.JPA.Contrato;
import com.HAriasCenagasService.JPA.Factura;
import com.HAriasCenagasService.JPA.Result;
import com.HAriasCenagasService.Repository.ContratoRepository;
import com.HAriasCenagasService.Service.FacturaService;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("facturaApi")
public class FacturaRestController {

    @Autowired
    private FacturaService facturaService;
    @Autowired
    private ContratoRepository contratoRepository;

    @GetMapping("/factura")
    public ResponseEntity<List<Factura>> getAll() {
        try {
            Result result = facturaService.getAllFactura();
            if (!result.correct || result.objects == null || result.objects.isEmpty()) {
                return ResponseEntity.status(204).build();
            }
            List<Factura> facturas = result.objects.stream()
                    .map(obj -> (Factura) obj)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(facturas);
        } catch (Exception ex) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @PostMapping("/add")
    public ResponseEntity ContratoAdd(@RequestBody Factura Factura) {
        try {
            Result result = facturaService.AddFactura(Factura);
            if (result.correct) {
                return ResponseEntity.ok(result);
            } else {
                return ResponseEntity.badRequest().build();
            }
        } catch (Exception ex) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/{idFactura}")
    public ResponseEntity<Factura> getFacturaById(@PathVariable Long idFactura) {
        Factura factura = facturaService.getFacturaById(idFactura);
        if (factura != null) {
            return ResponseEntity.ok(factura);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PostMapping("/update")
    public ResponseEntity FacturaUpdate(@RequestBody Factura factura, @RequestParam Long idFactura){
        try {
            Result result = facturaService.updateFactura(idFactura, factura);
            if(result.correct){
                return ResponseEntity.ok(result);
            }else{
                return ResponseEntity.badRequest().build();
            }
        } catch (Exception ex) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @PostMapping("/getAllDinamico")
    public ResponseEntity getAllDinamico(@RequestBody Factura factura) {
        try {
            Result result = facturaService.getAllDinamico(factura);
            if (!result.correct || result.objects == null || result.objects.isEmpty()) {
                return ResponseEntity.status(204).body(null);
            }
            List<Factura> facturas = result.objects.stream()
                    .map(obj -> (Factura) obj)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(facturas);
        } catch (Exception ex) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @PostMapping("/CargaMasiva/procesar")
    public ResponseEntity Procesar(@RequestBody String absolutepath) {
        Result result = new Result();
        try {
            List<Factura> listaFactura = new ArrayList<>();
            listaFactura = LecturaArchivoExcel(new File(absolutepath));
            for (Factura factura : listaFactura) {
                facturaService.AddFactura(factura);
            }
            result.correct = true;

        } catch (Exception ex) {
            result.correct = false;
            result.objects = null;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return ResponseEntity.ok(result);
    }

    public List<Factura> LecturaArchivoExcel(File archivo) {
        List<Factura> listaFacturas = new ArrayList<>();
        try (XSSFWorkbook workbook = new XSSFWorkbook(archivo);) {
            for (Sheet sheet : workbook) {
                for (Row row : sheet) {
                    Factura factura = new Factura();
                    Contrato contrato = new Contrato();
                    Cell cell = row.getCell(0);
                    Date fecha;
                    if (cell.getCellType() == CellType.NUMERIC) {
                        fecha = cell.getDateCellValue();
                    } else {
                        String fechaCadena = cell.toString();
                        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
                        fecha = formato.parse(fechaCadena);

                    }
                    String contratoNombre = row.getCell(1).getStringCellValue();
                    Long idContrato = contratoRepository.buscarIdByNombre(contratoNombre);
                    contrato.setIdContrato(idContrato);
                    factura.setFecha(fecha);
                    factura.setContrato(contrato);
                    factura.setCantidadnominadarecepcion(row.getCell(9).getNumericCellValue());
                    factura.setCantidadasignadarecepcion(row.getCell(10).getNumericCellValue());
                    factura.setCantidadnominadaentrega(row.getCell(11).getNumericCellValue());
                    factura.setCantidadasignadaentrega(row.getCell(12).getNumericCellValue());
                    factura.setGasexceso(row.getCell(13).getNumericCellValue());
                    factura.setTarifaexcesofirme(row.getCell(14).getNumericCellValue());
                    factura.setTarifausoininterrumpible(row.getCell(15).getNumericCellValue());
                    factura.setCargouso(row.getCell(16).getNumericCellValue());
                    factura.setCargagasexceso(row.getCell(17).getNumericCellValue());
                    factura.setTotalfacturar(row.getCell(18).getNumericCellValue());

                    listaFacturas.add(factura);
                }
            }
        } catch (Exception e) {
        }

        return listaFacturas;
    }

    @GetMapping("/minFecha")
    public ResponseEntity fechaMin() {
        try {
            Result result = facturaService.minFecha();
            if (!result.correct || result.object == null) {
                return ResponseEntity.status(204).body(null);
            }

            return ResponseEntity.ok(result);
        } catch (Exception ex) {
            return ResponseEntity.status(500).body(null);
        }

    }

    @GetMapping("/maxFecha")
    public ResponseEntity fechaMax() {
        try {
            Result result = facturaService.maxFecha();
            if (!result.correct || result.object == null) {
                return ResponseEntity.status(204).body(null);
            }
            return ResponseEntity.ok(result);
        } catch (Exception ex) {
            return ResponseEntity.status(500).body(null);
        }
    }

}
