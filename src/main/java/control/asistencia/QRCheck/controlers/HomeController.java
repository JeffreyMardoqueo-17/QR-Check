package control.asistencia.QRCheck.controlers;

import control.asistencia.QRCheck.models.Empresa;
import control.asistencia.QRCheck.models.Usuario;
import control.asistencia.QRCheck.services.iterfaces.IEmpresaService;
import control.asistencia.QRCheck.services.iterfaces.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;
@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    private IUsuarioService usuarioService;

    @Autowired
    private IEmpresaService empresaService;

    @GetMapping
    public String index(Model model) {
        // Aquí deberías obtener el usuario autenticado. Por simplicidad, asumimos un usuario con ID 3.
        Integer usuarioId = obtenerUsuarioActualId();
        Optional<Usuario> usuarioOpt = usuarioService.buscarPorId(usuarioId);

        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            Empresa empresa = usuario.getEmpresa();

            // Pasar las coordenadas del usuario y la empresa al modelo para la vista
            model.addAttribute("usuarioLatitud", usuario.getLatitud());
            model.addAttribute("usuarioLongitud", usuario.getLongitud());
            model.addAttribute("empresaLatitud", empresa.getLatitud());
            model.addAttribute("empresaLongitud", empresa.getLongitud());

            // Obtener todos los usuarios para mostrarlos en el mapa
            List<Usuario> trabajadores = usuarioService.obtenerTodos();
            model.addAttribute("trabajadores", trabajadores);
        } else {
            // Manejo de caso donde el usuario no es encontrado
            model.addAttribute("error", "Usuario no encontrado");
            return "error"; // Redirigir a una página de error o manejarlo como prefieras
        }

        return "home/index";
    }

    private Integer obtenerUsuarioActualId() {
        return 3; // Ejemplo de ID de usuario, deberías cambiarlo por el ID real del usuario autenticado
    }
}
