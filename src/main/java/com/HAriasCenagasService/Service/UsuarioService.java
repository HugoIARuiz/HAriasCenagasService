package com.HAriasCenagasService.Service;

import com.HAriasCenagasService.JPA.Result;
import com.HAriasCenagasService.JPA.Usuario;
import com.HAriasCenagasService.Repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    public Result addUsuario(Usuario usuario) {
        Result result = new Result();
        try {
            usuarioRepository.save(usuario);
            result.correct = true;

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }

    @Transactional
    public Result updateUsuario(Long idUsuario, Usuario usuario) {
        Result result = new Result();
        try {
            Usuario usuarioExistente = usuarioRepository.findById(idUsuario).orElseThrow();
            usuarioExistente.setNombre(usuario.getNombre());
            usuarioRepository.save(usuarioExistente);
            result.object = usuarioExistente;
            result.correct = true;
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }

    public Result getAllUsuario() {
        Result result = new Result();
        result.objects = new ArrayList<>();

        try {
            List<Usuario> usuarios = usuarioRepository.findAll();
            if (!usuarios.isEmpty()) {
                result.objects.addAll(usuarios.stream().sorted(Comparator.comparing(Usuario::getIdUsuario)).map(u -> (Object) u).toList());
                result.correct = true;
            } else {
                result.correct = false;
                result.errorMessage = "No se encontraron usuarios.";
            }
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }

    public Result getAllDinamico(Usuario usuario) {
        Result result = new Result();
        result.objects = new ArrayList<>();

        try {
            List<Usuario> usuarios = usuarioRepository.findAll();
            List<Usuario> listaUsuarios = usuarios.stream().filter(u -> u.getNombre() == null || u.getNombre().isEmpty() || u.getNombre().toLowerCase().contains(usuario.getNombre().toLowerCase()))
                    .collect(Collectors.toList());
            if (!usuarios.isEmpty()) {
                result.objects.addAll(listaUsuarios.stream().map(u -> (Object) u).toList());
                result.correct = true;
            } else {
                result.correct = false;
                result.errorMessage = "No se encontraron usuarios.";
            }
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }

    @Transactional
    public Usuario getUsuarioById(Long idUsuario) {
        return usuarioRepository.findById(idUsuario).orElse(null);
    }
}
