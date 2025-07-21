package com.HAriasCenagasService.RestController;

import com.HAriasCenagasService.JPA.Result;
import com.HAriasCenagasService.JPA.ResultFile;
import com.HAriasCenagasService.JPA.Usuario;
import com.HAriasCenagasService.Repository.UsuarioRepository;
import com.HAriasCenagasService.Service.UsuarioService;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
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
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/usuarioApi")
public class UsuarioRestController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/usuario")
    public ResponseEntity<List<Usuario>> getAll() {
        try {
            Result result = usuarioService.getAllUsuario();

            if (!result.correct || result.objects == null || result.objects.isEmpty()) {
                return ResponseEntity.status(204).build();
            }

            List<Usuario> usuarios = result.objects.stream()
                    .map(obj -> (Usuario) obj)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(usuarios);

        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @PostMapping("/add")
    public ResponseEntity UsuarioAdd(@RequestBody Usuario usuario) {
        try {
            Result result = usuarioService.addUsuario(usuario);

            if (result.correct) {
                return ResponseEntity.ok(result);
            } else {
                return ResponseEntity.badRequest().build();
            }
        } catch (Exception ex) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/{idUsuario}")
    public ResponseEntity<Usuario> getUsuarioById(@PathVariable Long idUsuario) {
        Usuario usuario = usuarioService.getUsuarioById(idUsuario);
        if (usuario != null) {
            return ResponseEntity.ok(usuario);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PostMapping("/update")
    public ResponseEntity UsuarioUpdate(@RequestBody Usuario usuario, @RequestParam Long idUsuario) {
        try {
            Result result = usuarioService.updateUsuario(idUsuario, usuario);
            if (result.correct) {
                return ResponseEntity.ok(result);
            } else {
                return ResponseEntity.badRequest().build();
            }
        } catch (Exception ex) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @PostMapping("/getAllDinamico")
    public ResponseEntity getAllDinamico(@RequestBody Usuario usuario) {
        try {
            Result result = usuarioService.getAllDinamico(usuario);
            if (!result.correct || result.objects == null || result.objects.isEmpty()) {
                return ResponseEntity.status(204).body(null);
            }
            List<Usuario> usuarios = result.objects.stream()
                    .map(obj -> (Usuario) obj)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(usuarios);

        } catch (Exception ex) {
            return ResponseEntity.status(500).body(null);
        }
    }

    

    @PostMapping("/CargaMasiva/procesar")
    public ResponseEntity Procesar(@RequestBody String absolutePath) {
        Result result = new Result();
        try {
            List<Usuario> listaUsuario = new ArrayList<>();
            listaUsuario = LecturaArchivoExcel(new File(absolutePath));
            for (Usuario usuario : listaUsuario) {
                usuarioService.addUsuario(usuario);
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

    public List<Usuario> LecturaArchivoExcel(File archivo) {
        Set<String> usuarioUnico = new HashSet<>();
        List<Usuario> listaUsuarios = new ArrayList<>();
        try (XSSFWorkbook workbook = new XSSFWorkbook(archivo);) {
            for (Sheet sheet : workbook) {
                for (Row row : sheet) {
                    Usuario usuario = new Usuario();
                    String usuarioNombre = row.getCell(2).getStringCellValue();
                    if (!usuarioUnico.contains(usuarioNombre)) {
                        usuarioUnico.add(usuarioNombre);
                        usuario.setNombre(usuarioNombre);
                        listaUsuarios.add(usuario);
                    }

                }
            }
        } catch (Exception ex) {
            listaUsuarios = null;

        }

        return listaUsuarios;
    }

    @PostMapping("/CargaMasiva")
    public ResponseEntity CargaMasiva(@RequestParam("archivo") MultipartFile archivo) {
        try {
            Result result = new Result();
            String root = System.getProperty("user.dir");
            String path = "src/main/resources/static/archivos";
            String fecha = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmSS"));
            String absolutePath = root + "/" + path + "/" + fecha + archivo.getOriginalFilename();
            archivo.transferTo(new File(absolutePath));
            List<Usuario> listaUsuarios = new ArrayList<>();
            listaUsuarios = LecturaArchivoExcel(new File(absolutePath));
            List<ResultFile> listaErrores = ValidarArchivo(listaUsuarios);
            if (listaErrores.isEmpty()) {
                result.correct = true;
                result.object = absolutePath;
                return ResponseEntity.ok(result);
            } else {
                result.correct = false;
                result.objects = new ArrayList<>();
                for (ResultFile error : listaErrores) {
                    result.objects.add(error);
                }
                return ResponseEntity.status(500).body(null);
            }
        } catch (Exception e) {
        }
        return ResponseEntity.status(400).body(null);
    }

    public List<ResultFile> ValidarArchivo(List<Usuario> listaUsuarios) {
        List<ResultFile> listaErrores = new ArrayList<>();

        if (listaUsuarios == null) {
            listaErrores.add(new ResultFile(0, "La lista es nula", "La lista es nula"));
        } else if (listaUsuarios.isEmpty()) {
            listaErrores.add(new ResultFile(0, "La lista está vacía", "La lista esta vacía"));
        } else {
            int fila = 1;
            for (Usuario usuario : listaUsuarios) {
                if (usuario.getNombre() == null || usuario.getNombre().equals(usuario)) {
                    listaErrores.add(new ResultFile(fila, usuario.getNombre(), "El nombre es un campo Obligatorio"));
                }
                fila++;
            }
        }
        return listaErrores;
    }

}
