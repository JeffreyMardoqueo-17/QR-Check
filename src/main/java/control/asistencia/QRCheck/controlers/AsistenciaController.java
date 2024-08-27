package control.asistencia.QRCheck.controlers;

import control.asistencia.QRCheck.models.Asistencia;
import control.asistencia.QRCheck.services.iterfaces.IAsistenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Base64;
import java.util.List;

@Controller
public class AsistenciaController {

    @Autowired
    private IAsistenciaService asistenciaService;

    @GetMapping("/asistencias/qr")
    public String generateQR(Model model) {
        try {
            // Obtén la información del usuario actual
            Integer usuarioId = obtenerUsuarioActualId(); // Método ficticio para obtener el ID del usuario actual

            // Llama al servicio para marcar la asistencia y obtener el ID de la asistencia
            Asistencia asistencia = asistenciaService.marcarAsistencia(usuarioId);
            if (asistencia != null) {
                Integer asistenciaId = asistencia.getId(); // Asumiendo que 'Asistencia' tiene un método getId()

                // Obtén el QR para la asistencia
                byte[] qrCode = asistenciaService.generarQRCodeAsistencia(asistenciaId);
                if (qrCode != null) {
                    String qrCodeBase64 = Base64.getEncoder().encodeToString(qrCode);
                    model.addAttribute("qrCode", qrCodeBase64);
                    model.addAttribute("usuarioId", usuarioId);
                    return "asistencia/qr"; // Vista para mostrar el QR
                } else {
                    model.addAttribute("error", "Error generating QR code");
                    return "error"; // Vista para manejar errores
                }
            } else {
                model.addAttribute("error", "Error marking attendance");
                return "error"; // Vista para manejar errores
            }
        } catch (Exception e) {
            model.addAttribute("error", "Error generating QR code");
            return "error"; // Vista para manejar errores
        }
    }

    @PostMapping("/asistencias/marcar")
    public String marcarAsistencia(Model model) {
        try {
            // Obtén la información del usuario actual
            Integer usuarioId = obtenerUsuarioActualId(); // Método ficticio para obtener el ID del usuario actual

            // Marca la asistencia
            Asistencia asistencia = asistenciaService.marcarAsistencia(usuarioId);
            model.addAttribute("asistencia", asistencia);
            return "asistencia/marcada"; // Vista para confirmar la asistencia registrada
        } catch (Exception e) {
            model.addAttribute("error", "Error marking attendance");
            return "error"; // Vista para manejar errores
        }
    }

    @GetMapping("/asistencias")
    public String listarAsistencias(Model model) {
        List<Asistencia> asistencias = asistenciaService.obtenerTodasAsistencias();
        model.addAttribute("asistencias", asistencias);
        return "asistencia/index"; // Vista para listar todas las asistencias
    }

    private Integer obtenerUsuarioActualId() {
        // Implementa la lógica para obtener el ID del usuario actual
        // Por ejemplo, desde la sesión, base de datos, etc.
        return 1; // Valor ficticio, reemplaza con la lógica real
    }
}
