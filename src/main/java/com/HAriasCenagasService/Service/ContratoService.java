package com.HAriasCenagasService.Service;

import com.HAriasCenagasService.JPA.Contrato;
import com.HAriasCenagasService.JPA.NodoComEntrega;
import com.HAriasCenagasService.JPA.NodoComRecepcion;
import com.HAriasCenagasService.JPA.Result;
import com.HAriasCenagasService.JPA.Usuario;
import com.HAriasCenagasService.Repository.ContratoRepository;
import com.HAriasCenagasService.Repository.NodoComEntregaRepository;
import com.HAriasCenagasService.Repository.NodoComRecepcionRepository;
import com.HAriasCenagasService.Repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContratoService {

    @Autowired
    private ContratoRepository contratoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private NodoComEntregaRepository nodoComEntregaRepository;
    @Autowired
    private NodoComRecepcionRepository nodoComRecepcionRepository;

    @Transactional
    public Result AddContrato(Contrato contrato) {
        Result result = new Result();
        try {
            contratoRepository.save(contrato);
            result.correct = true;
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }

    @Transactional
    public Result updateContrato(Long idContrato, Contrato contrato) {
        Result result = new Result();
        try {
            Contrato contratoExistente = contratoRepository.findById(idContrato).orElseThrow();

            contratoExistente.setNombre(contrato.getNombre());

            Usuario usuarioActualizado = usuarioRepository.findById(contrato.usuario.getIdUsuario()).orElseThrow();
            contratoExistente.setUsuario(usuarioActualizado);

            NodoComEntrega nodoEntregaActualizado = nodoComEntregaRepository.findById(
                    contrato.nodoComEntrega.getIdNodoEntrega()
            ).orElseThrow();
            contratoExistente.setNodoComEntrega(nodoEntregaActualizado);

            NodoComRecepcion nodoRecepcionActualizado = nodoComRecepcionRepository.findById(
                    contrato.nodoComRecepcion.getIdNodoRecepcion()
            ).orElseThrow();
            contratoExistente.setNodoComRecepcion(nodoRecepcionActualizado);

            contratoRepository.save(contratoExistente);
            result.object = contratoExistente;
            result.correct = true;
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }

    @Transactional
    public Contrato getContratoById(Long idContrato) {
        return contratoRepository.findById(idContrato).orElse(null);
    }

    public Result getAllContrato() {
        Result result = new Result();
        result.objects = new ArrayList<>();

        try {
            List<Contrato> contratos = contratoRepository.findAll();
            if (!contratos.isEmpty()) {
                result.objects.addAll(contratos.stream().sorted(Comparator.comparing(Contrato::getIdContrato)).map(c -> (Object) c).toList());
                result.correct = true;
            } else {
                result.correct = false;
                result.errorMessage = "No se han encontrado contratos";
            }
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }

    public Result getAllDinamico(Contrato contrato) {
        Result result = new Result();
        result.objects = new ArrayList<>();

        try {
            List<Contrato> contratos = contratoRepository.findAll();
            List<Contrato> listaContratos = contratos.stream()
                    .filter(c -> contrato.getNombre() == null || contrato.getNombre().isEmpty() || c.getNombre().toLowerCase().contains(contrato.getNombre().toLowerCase()))
                    .filter(c -> contrato.usuario != null && contrato.usuario.getIdUsuario() > 0 ? Objects.equals(c.usuario.getIdUsuario(), contrato.usuario.getIdUsuario()) : true)
                    .filter(c -> contrato.nodoComRecepcion != null && contrato.nodoComRecepcion.getIdNodoRecepcion() > 0 ? c.nodoComRecepcion.getIdNodoRecepcion() == contrato.nodoComRecepcion.getIdNodoRecepcion() : true)
                    .filter(c -> contrato.nodoComRecepcion != null && contrato.nodoComRecepcion.zonaTarifa != null && contrato.nodoComRecepcion.zonaTarifa.getIdZonaTarifa() > 0 ? c.nodoComRecepcion.zonaTarifa.getIdZonaTarifa() == contrato.nodoComRecepcion.zonaTarifa.getIdZonaTarifa() : true)
                    .filter(c -> contrato.nodoComEntrega != null && contrato.nodoComEntrega.getIdNodoEntrega() > 0 ? c.nodoComEntrega.getIdNodoEntrega() == contrato.nodoComEntrega.getIdNodoEntrega() : true)
                    .filter(c -> contrato.nodoComEntrega != null && contrato.nodoComEntrega.zonaTarifa != null && contrato.nodoComEntrega.zonaTarifa.getIdZonaTarifa() > 0 ? c.nodoComEntrega.zonaTarifa.getIdZonaTarifa() == contrato.nodoComEntrega.zonaTarifa.getIdZonaTarifa() : true)
                    .collect(Collectors.toList());
            if (!contratos.isEmpty()) {
                result.objects.addAll(listaContratos.stream().map(c -> (Object) c).toList());
                result.correct = true;
            } else {
                result.correct = false;
                result.errorMessage = "No se encontraron contratos.";
            }
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }
}
