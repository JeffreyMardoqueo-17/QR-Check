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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

// Indica que es un controlador
@Controller

// Mapea todas las solicitudes que empiezan con usuarios a este controlador
@RequestMapping("usuarios")
public class UsuarioController {

    // Inyecta automáticamente una instancia de IUsuarioService en esta clase.
    @Autowired
    private IUsuarioService usuarioService;

    // Inyecta automáticamente una instancia de IEmpresaService en esta clase.
    @Autowired
    private IEmpresaService empresaService;

    // Inyecta automáticamente una instancia de IRolesService en esta clase.
    @Autowired
    private IRolesService rolesService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    // Maneja las solicitudes GET a la ruta "usuarios".
    @GetMapping
    public String index(Model model, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size) {

        // La numeración de páginas empieza en 0, por eso se resta 1.
        int currentPage = page.orElse(1) - 1;

        // Obtengo el tamaño de la página solicitada, o 5 si no se proporciona.
        int pageSize = size.orElse(5); // Se define cuántos elementos se mostrarán por página.

        // Creo un objeto Pageable para manejar la paginación.
        Pageable pageable = PageRequest.of(currentPage, pageSize);

        // Obtengo una lista paginada de usuarios desde el servicio.
        Page<Usuario> usuarios = usuarioService.buscarTodosPaginados(pageable);

        // Añado la lista de usuarios paginada al modelo para pasarla a la vista.
        model.addAttribute("usuarios", usuarios);

        // Calculo el número total de páginas.
        int totalPages = usuarios.getTotalPages();
        if (totalPages > 0) {
            // Creo una lista de números de página del 1 al total de páginas.
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());

            // Añado la lista de números de página al modelo para que la vista pueda mostrarlos.
            model.addAttribute("pageNumbers", pageNumbers);
        }

        // Devuelvo la vista "usuario/index" para mostrar la lista de usuarios.
        return "usuario/index";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {

        model.addAttribute("usuario", new Usuario());
        model.addAttribute("empresas", empresaService.obtenerTodos());
        model.addAttribute("roles", rolesService.obtenerTodos());

        return "usuario/create";
    }

    @PostMapping("/save")
    public String saveUsuario(@ModelAttribute("usuario") Usuario usuario, BindingResult result) {

        if (result.hasErrors()) {
            return "usuario/create";
        }

        // Obtengo un usuario por defecto desde la base de datos con el ID 1.
        Usuario usuarioPorDefecto = usuarioService.buscarPorId(1).get();
        if (usuarioPorDefecto == null) { // Verifico si el usuario por defecto existe.
            // Si no existe, regreso al formulario de creación.
            return "usuario/create";
        }

        // Asigno el usuario por defecto como creador y modificador del nuevo usuario.
        usuario.setCreadoPor(usuarioPorDefecto);
        usuario.setModificadoPor(usuarioPorDefecto);

        // Asigno la fecha actual como fecha de creación y modificación del nuevo usuario.
        usuario.setFechaCreacion(LocalDateTime.now());
        usuario.setFechaModificacion(LocalDateTime.now());

        // Busco la empresa seleccionada en la base de datos por su ID.
        Optional<Empresa> optionalEmpresa = empresaService.buscarPorId(usuario.getEmpresa().getId());

        // Si no se encuentra la empresa, lanzo una excepción.
        Empresa empresa = optionalEmpresa.orElseThrow(() -> new RuntimeException("Empresa no encontrada"));

        // Verifico si la empresa existe.
        if (empresa == null) {
            // Si no existe, regreso al formulario de creación.
            return "usuario/create";
        }

        usuario.setEmpresa(empresa);

        Roles role = rolesService.buscarPorId(usuario.getRol().get(0).getId())
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

        if (usuario.getRol() == null) {
            usuario.setRol(new ArrayList<>());
        }
        usuario.getRol().clear(); // Limpiamos la lista existente.
        usuario.getRol().add(role); // Añadimos el rol encontrado.

        PasswordEncoder encoder = new BCryptPasswordEncoder();
        usuario.setPass(encoder.encode(usuario.getPass()));

        // Guardo o actualizo el usuario en la base de datos usando el servicio.
        usuarioService.createOrEditOne(usuario);


        // Redirijo a la lista de usuarios después de guardar.
        return "redirect:/usuarios";
    }

    @GetMapping("/details/{id}")
    public String details(@PathVariable("id") Integer id, Model model) {

        // Busco un usuario por su ID en la base de datos.
        Usuario usuario = usuarioService.buscarPorId(id).orElse(null);

        // Añado el usuario al modelo para mostrar sus detalles en la vista.
        model.addAttribute("usuario", usuario);

        // Devuelvo la vista "usuario/details" para mostrar los detalles del usuario.
        return "usuario/details";
    }


    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) {

        // Busco el usuario en la base de datos por su ID.
        Optional<Usuario> optionalUsuario = usuarioService.buscarPorId(id);


        model.addAttribute("roles", rolesService.obtenerTodos());

        // Verifico si el usuario existe.
        if (!optionalUsuario.isPresent()) {
            return "redirect:/usuario";
        }
        Usuario usuario = optionalUsuario.get();
        model.addAttribute("usuario", usuario);
        model.addAttribute("empresas", empresaService.obtenerTodos());

        // Devuelvo la vista "usuario/edit" para mostrar el formulario de edición.
        return "usuario/edit";
    }


    @GetMapping("/remove/{id}")
    public String remove(@PathVariable("id") Integer id, Model model) {

        // Busco un usuario por su ID en la base de datos.
        Usuario usuario = usuarioService.buscarPorId(id).orElse(null);

        // Añado el usuario al modelo para confirmar su eliminación.
        model.addAttribute("usuario", usuario);

        // Devuelvo la vista "usuario/delete" para confirmar la eliminación del usuario.
        return "usuario/delete";
    }


    // Maneja las solicitudes POST a la ruta "usuarios/delete".
    @PostMapping("/delete")
    public String delete(@RequestParam("id") Long id, Usuario usuario, RedirectAttributes redirectAttributes) {

        // Elimino el usuario de la base de datos usando su ID.
        usuarioService.eliminarPorId(usuario.getId());

        // Añado un mensaje de éxito que se mostrará en la vista siguiente.
        redirectAttributes.addFlashAttribute("success", "Usuario eliminado exitosamente.");

        // Redirijo a la lista de usuarios después de eliminar.
        return "redirect:/usuarios";
    }


}