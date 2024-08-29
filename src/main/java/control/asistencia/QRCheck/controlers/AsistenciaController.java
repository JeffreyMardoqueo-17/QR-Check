package control.asistencia.QRCheck.controlers;

import control.asistencia.QRCheck.models.Asistencia;
import control.asistencia.QRCheck.services.iterfaces.IAsistenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Base64;
import java.util.List;

@Controller
public class AsistenciaController {

    @Autowired
    private IAsistenciaService asistenciaService;

    @GetMapping("/asistencias")
    public String listarAsistencias(Model model) {
        List<Asistencia> asistencias = asistenciaService.obtenerTodasAsistencias();
        model.addAttribute("asistencias", asistencias);
        return "asistencia/index";
    }

    @GetMapping("/generarQRAsistencia")
    public String generarQRAsistencia(Model model) {
        Integer usuarioId = obtenerUsuarioActualId(); // Obtener el ID del usuario actual
        byte[] qrCode = asistenciaService.generarQRCodeAsistencia(usuarioId);
        if (qrCode != null) {
            String qrCodeBase64 = Base64.getEncoder().encodeToString(qrCode);
            model.addAttribute("qrCode", qrCodeBase64);
        } else {
            model.addAttribute("error", "Error generando el código QR");
        }
        return "asistencia/generarQR";
    }

    @GetMapping("/marcarAsistencia")
    public String marcarAsistencia(@RequestParam Integer asistenciaId, Model model) {
        // Lógica para marcar la asistencia usando el ID
        Asistencia asistencia = asistenciaService.marcarAsistencia(asistenciaId);
        model.addAttribute("asistencia", asistencia);
        return "asistencia/marcada"; // Vista para mostrar que la asistencia ha sido marcada
    }

    private Integer obtenerUsuarioActualId() {
        return 3; // Valor ficticio porque aeiu capturare lo del usario logeuado
    }

    @GetMapping("/scanQRCode")
    public String scanQRCode() {
        return "asistencia/scanQRCode";
    }

}
