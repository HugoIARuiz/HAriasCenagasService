
package com.HAriasCenagasService;

import com.HAriasCenagasService.JPA.Contrato;
import com.HAriasCenagasService.JPA.Factura;
import com.HAriasCenagasService.JPA.NodoComEntrega;
import com.HAriasCenagasService.JPA.NodoComRecepcion;
import com.HAriasCenagasService.JPA.Result;
import com.HAriasCenagasService.JPA.Usuario;
import com.HAriasCenagasService.JPA.ZonaTarifa;
import com.HAriasCenagasService.Repository.ContratoRepository;
import com.HAriasCenagasService.Repository.FacturaRepository;
import com.HAriasCenagasService.Repository.NodoComEntregaRepository;
import com.HAriasCenagasService.Repository.NodoComRecepcionRepository;
import com.HAriasCenagasService.Repository.UsuarioRepository;
import com.HAriasCenagasService.Repository.ZonaTarifaRepository;
import com.HAriasCenagasService.Service.ContratoService;
import com.HAriasCenagasService.Service.FacturaService;
import com.HAriasCenagasService.Service.NodoComEntregaService;
import com.HAriasCenagasService.Service.NodoComRecepcionService;
import com.HAriasCenagasService.Service.UsuarioService;
import com.HAriasCenagasService.Service.ZonaTarifaService;
import java.util.Date;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JUnitCenagas {
    @Mock
    private UsuarioRepository usuarioRepository;
    @InjectMocks
    private UsuarioService usuarioService;
    @Mock
    private ZonaTarifaRepository zonaTarifaRepository;
    @InjectMocks
    private ZonaTarifaService zonaTarifaService;
    @Mock
    private NodoComRecepcionRepository nodoComRecepcionRepository;
    @InjectMocks
    private NodoComRecepcionService nodoComRecepcionService;
    @Mock
    private NodoComEntregaRepository nodoComEntregaRepository;
    @InjectMocks
    private NodoComEntregaService nodoComEntregaService;
    @Mock
    private ContratoRepository contratoRepository;
    @InjectMocks
    private ContratoService contratoService;
    @Mock
    private FacturaRepository facturaRepository;
    @InjectMocks
    private FacturaService facturaService;
    
    @Test
    public void testAddUsuario(){
        
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(1L);
        usuario.setNombre("Gascentro");
        Mockito.when(usuarioRepository.save(usuario)).thenReturn(usuario);
        Result result = usuarioService.addUsuario(usuario);
         Assertions.assertNotNull(result, " El result viene vacío");
        Assertions.assertTrue(result.correct, "El result.correct es false");
        Assertions.assertNull(result.objects, "El result.object viene vacío");
        Assertions.assertNull(result.ex, "El result.ex trae una excepcion");
        Assertions.assertNull(result.object, "El result.object no es nulo");
        Assertions.assertNull(result.errorMessage, "El result.errormessager no viene vacío");
        Mockito.verify(usuarioRepository, Mockito.atLeast(1)).save(usuario);
    }
    @Test
    public void testAddZona(){
        ZonaTarifa zonaTarifa = new ZonaTarifa();
        zonaTarifa.setNombre("Zona Test");
        Mockito.when(zonaTarifaRepository.save(zonaTarifa)).thenReturn(zonaTarifa);
        Result result = zonaTarifaService.AddZona(zonaTarifa);
        Assertions.assertNotNull(result, " El result viene vacío");
        Assertions.assertTrue(result.correct, "El result.correct es false");
        Assertions.assertNull(result.objects, "El result.object viene vacío");
        Assertions.assertNull(result.ex, "El result.ex trae una excepcion");
        Assertions.assertNull(result.object, "El result.object no es nulo");
        Assertions.assertNull(result.errorMessage, "El result.errormessager no viene vacío");
        Mockito.verify(zonaTarifaRepository,Mockito.atLeast(1)).save(zonaTarifa);

    }
    
    @Test
    public void testAddNodoRecepcion(){
        NodoComRecepcion nodoRecepcion = new NodoComRecepcion();
        ZonaTarifa zonaTarifa = new ZonaTarifa();
        nodoRecepcion.setNombre("vTest");
        nodoRecepcion.setDescripcion("Nodo de Pruebas");
        zonaTarifa.setIdZonaTarifa(1);
        nodoRecepcion.setZonaTarifa(zonaTarifa);
        Mockito.when(nodoComRecepcionRepository.save(nodoRecepcion)).thenReturn(nodoRecepcion); 
        Result result = nodoComRecepcionService.AddNodoRecepcion(nodoRecepcion);
        Assertions.assertNotNull(result, " El result viene vacío");
        Assertions.assertTrue(result.correct, "El result.correct es false");
        Assertions.assertNull(result.objects, "El result.object viene vacío");
        Assertions.assertNull(result.ex, "El result.ex trae una excepcion");
        Assertions.assertNull(result.object, "El result.object no es nulo");
        Assertions.assertNull(result.errorMessage, "El result.errormessager no viene vacío");
        Mockito.verify(nodoComRecepcionRepository, Mockito.atLeast(1)).save(nodoRecepcion);
    }
    @Test
    public void testAddNodoEntrega(){
        NodoComEntrega nodoEntrega = new NodoComEntrega();
        ZonaTarifa zonaTarifa = new ZonaTarifa();
        nodoEntrega.setNombre("vTest");
        nodoEntrega.setDescripcion("Nodo de Pruebas");
        zonaTarifa.setIdZonaTarifa(1);
        nodoEntrega.setZonaTarifa(zonaTarifa);
        Mockito.when(nodoComEntregaRepository.save(nodoEntrega)).thenReturn(nodoEntrega); 
        Result result = nodoComEntregaService.AddNodoEntrega(nodoEntrega);
        Assertions.assertNotNull(result, " El result viene vacío");
        Assertions.assertTrue(result.correct, "El result.correct es false");
        Assertions.assertNull(result.objects, "El result.object viene vacío");
        Assertions.assertNull(result.ex, "El result.ex trae una excepcion");
        Assertions.assertNull(result.object, "El result.object no es nulo");
        Assertions.assertNull(result.errorMessage, "El result.errormessager no viene vacío");
        Mockito.verify(nodoComEntregaRepository, Mockito.atLeast(1)).save(nodoEntrega);
    }
    
    @Test
    public void testAddContrato(){
        Contrato contrato = new Contrato();
        Usuario usuario = new Usuario();
        NodoComEntrega nodoComEntrega = new NodoComEntrega();
        NodoComRecepcion nodoComRecepcion = new NodoComRecepcion();
        usuario.setIdUsuario(1L);
        nodoComEntrega.setIdNodoEntrega(1L);
        nodoComRecepcion.setIdNodoRecepcion(1L); 
        contrato.setNombre("CENAGAS/TEST/27/09");
        contrato.setUsuario(usuario);
        contrato.setNodoComRecepcion(nodoComRecepcion);
        contrato.setNodoComEntrega(nodoComEntrega); 
        
        Mockito.when(contratoRepository.save(contrato)).thenReturn(contrato);
        Result result = contratoService.AddContrato(contrato);
        Assertions.assertNotNull(result, " El result viene vacío");
        Assertions.assertTrue(result.correct, "El result.correct es false");
        Assertions.assertNull(result.objects, "El result.object viene vacío");
        Assertions.assertNull(result.ex, "El result.ex trae una excepcion");
        Assertions.assertNull(result.object, "El result.object no es nulo");
        Assertions.assertNull(result.errorMessage, "El result.errormessager no viene vacío");
        Mockito.verify(contratoRepository, Mockito.atLeast(1)).save(contrato);
    }
    
    @Test
    public void testAddFactura(){
        Factura factura = new Factura();
        Contrato contrato = new Contrato();
        contrato.setIdContrato(1L);
        
        factura.setIdFactura(1L);
        factura.setFecha(new Date(-630720000000L));
        factura.setContrato(contrato);
        factura.setCantidadnominadarecepcion(1563.02);
        factura.setCantidadasignadarecepcion(1589.02);
        factura.setCantidadnominadaentrega(1789.63);
        factura.setCantidadasignadaentrega(1788.56);
        factura.setGasexceso(20.00);
        factura.setTarifaexcesofirme(0.00);
        factura.setTarifausoininterrumpible(0.00);
        factura.setCargouso(0.00);
        factura.setCargagasexceso(0.00);
        factura.setTotalfacturar(1900.00);
        Mockito.when(facturaRepository.save(factura)).thenReturn(factura);
        Result result = facturaService.AddFactura(factura);
        Assertions.assertNotNull(result, " El result viene vacío");
        Assertions.assertTrue(result.correct, "El result.correct es false");
        Assertions.assertNull(result.objects, "El result.object viene vacío");
        Assertions.assertNull(result.ex, "El result.ex trae una excepcion");
        Assertions.assertNull(result.object, "El result.object no es nulo");
        Assertions.assertNull(result.errorMessage, "El result.errormessager no viene vacío");
        Mockito.verify(facturaRepository,Mockito.atLeast(1)).save(factura);
    }
}
