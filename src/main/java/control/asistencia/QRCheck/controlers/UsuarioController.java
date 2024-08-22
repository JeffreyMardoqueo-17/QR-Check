package control.asistencia.QRCheck.controlers;

import control.asistencia.QRCheck.models.Empresa;
import control.asistencia.QRCheck.models.Roles;
import control.asistencia.QRCheck.models.Usuario;
import control.asistencia.QRCheck.services.iterfaces.IEmpresaService;
import control.asistencia.QRCheck.services.iterfaces.IUsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @GetMapping
    public String index(Model model, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size) {
        // Pagina la lista de empresas, mostrando 5 por defecto.
        int currentPage = page.orElse(1) - 1; // Si no se elige la página, se va a la primera.
        int pageSize = size.orElse(5); // Si no se elige el tamaño, se muestran 5 empresas.
        Pageable pageable = PageRequest.of(currentPage, pageSize);

        // Busca todas las empresas de forma paginada.
        Page<Usuario> usuarios = usuarioService.buscarTodosPaginados(pageable);
        model.addAttribute("usuarios", usuarios);

        // Calcula el total de páginas y las muestra.
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
    public String showCreateForm(Model model) {
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("empresas", empresaService.obtenerTodos());
        return "usuario/create";
    }


    // Este método guarda una nueva empresa después de que el usuario llena el formulario.
   /* @PostMapping("/save")
    public String save(Usuario usuario, BindingResult result, Model model, RedirectAttributes attributes) {
        // Si hay algún error en el formulario, no guarda y regresa al formulario.
        if (result.hasErrors()) {
            model.addAttribute("empresas", empresaService.obtenerTodos());
            model.addAttribute(usuario);
            attributes.addFlashAttribute("error", "No se pudo guardar debido a un error.");
            return "usuario/create";
        }

        // Guarda o edita la empresa y muestra un mensaje de éxito.
        usuarioService.createOrEditOne(usuario);
        attributes.addFlashAttribute("msg", "Usuario creada correctamente");
        return "redirect:/usuarios";
    }*/

    @PostMapping("/save")
    public String saveUsuario(@ModelAttribute("usuario") Usuario usuario, BindingResult result) {
        if (result.hasErrors()) {
            return "usuario/create"; // Volver al formulario si hay errores
        }

        // Busca un usuario existente en la base de datos para asignarlo a creadoPor y modificadoPor
        Usuario usuarioPorDefecto = usuarioService.buscarPorId(1).get();
        if (usuarioPorDefecto == null) {
            // Manejar el caso en que el usuario no sea encontrado, si es necesario
            // Por ejemplo, lanzar una excepción o devolver un mensaje de error
            return "usuario/create";
        }

        usuario.setCreadoPor(usuarioPorDefecto);
        usuario.setModificadoPor(usuarioPorDefecto);

        // Asignar fecha de creación y modificación si es necesario
        usuario.setFechaCreacion(LocalDateTime.now());
        usuario.setFechaModificacion(LocalDateTime.now());

        // Busca la empresa en la base de datos y la asigna al usuario
        Optional<Empresa> optionalEmpresa = empresaService.buscarPorId(usuario.getEmpresa().getId());
        Empresa empresa = optionalEmpresa.orElseThrow(() -> new RuntimeException("Empresa no encontrada"));

        if (empresa == null) {
            // Manejar el caso en que la empresa no sea encontrada, si es necesario
            return "usuario/create";
        }
        usuario.setEmpresa(empresa);

        usuarioService.createOrEditOne(usuario);
        return "redirect:/usuarios";
    }





}
