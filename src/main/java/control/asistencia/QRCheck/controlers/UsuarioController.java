package control.asistencia.QRCheck.controlers;

import control.asistencia.QRCheck.models.Empresa;
import control.asistencia.QRCheck.models.Roles;
import control.asistencia.QRCheck.models.Usuario;
import control.asistencia.QRCheck.services.iterfaces.IEmpresaService;
import control.asistencia.QRCheck.services.iterfaces.IRolesService;
import control.asistencia.QRCheck.services.iterfaces.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("usuarios")
public class UsuarioController {
    @Autowired
    private IUsuarioService usuarioService;

    @Autowired
    private IEmpresaService empresaService;

    @Autowired
    private IRolesService rolesService; // Inyección del servicio de roles

    @GetMapping
    public String index(Model model, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1) - 1;
        int pageSize = size.orElse(5);
        Pageable pageable = PageRequest.of(currentPage, pageSize);

        Page<Usuario> usuarios = usuarioService.buscarTodosPaginados(pageable);
        model.addAttribute("usuarios", usuarios);

        int totalPages = usuarios.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "usuario/index";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("empresas", empresaService.obtenerTodos());
        model.addAttribute("roles", rolesService.obtenerTodos()); // Asegúrate de que este método exista en IRolesService
        return "usuario/create";
    }




    @PostMapping("/save")
    public String saveUsuario(@ModelAttribute("usuario") Usuario usuario, BindingResult result) {
        if (result.hasErrors()) {
            return "usuario/create";
        }

        Usuario usuarioPorDefecto = usuarioService.buscarPorId(1).orElse(null);
        if (usuarioPorDefecto == null) {
            return "usuario/create";
        }

        usuario.setCreadoPor(usuarioPorDefecto);
        usuario.setModificadoPor(usuarioPorDefecto);

        usuario.setFechaCreacion(LocalDateTime.now());
        usuario.setFechaModificacion(LocalDateTime.now());

        Optional<Empresa> optionalEmpresa = empresaService.buscarPorId(usuario.getEmpresa().getId());
        Empresa empresa = optionalEmpresa.orElseThrow(() -> new RuntimeException("Empresa no encontrada"));
        usuario.setEmpresa(empresa);

        // Aquí también debes manejar el rol del usuario. Ejemplo:
        // Suponiendo que el rol es una entidad relacionada con un ID
        Roles rol = rolesService.buscarPorId(usuario.getRol().getId()).orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        usuario.setRol(rol);

        usuarioService.createOrEditOne(usuario);
        return "redirect:/usuarios";
    }
}
