package control.asistencia.QRCheck.controlers;
import control.asistencia.QRCheck.services.iterfaces.IQRService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Base64;

@Controller
public class QRController {

    @Autowired
    private IQRService qrService;

    @GetMapping("/generateQR")
    public String generateQRCode(@RequestParam(required = false) String text,
                                 @RequestParam(defaultValue = "200") int width,
                                 @RequestParam(defaultValue = "200") int height, Model model) {
        if (text != null && !text.trim().isEmpty()) {
            byte[] qrCode = qrService.generateQRCode(text, width, height);
            if (qrCode != null) {
                String qrCodeBase64 = Base64.getEncoder().encodeToString(qrCode);
                model.addAttribute("qrCode", qrCodeBase64);
                model.addAttribute("text", text);
            } else {
                model.addAttribute("error", "Error generating QR code");
            }
        } else {
            model.addAttribute("error", "Texto para el QR es requerido");
        }
        return "generateQR/index"; // Cambia a la ruta correcta aquí
    }

}
