package control.asistencia.QRCheck.config;

import control.asistencia.QRCheck.services.iterfaces.IAsistenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class QRGenerationScheduler {

    @Autowired
    private IAsistenciaService asistenciaService;

    @Scheduled(cron = "0 0 7 * * MON-FRI")
    public void generarQRCodeDiario() {
        // Obtén la información del usuario (esto es solo un ejemplo, ajusta según tu lógica)
        Integer usuarioId = obtenerUsuarioActualId(); // Método ficticio para obtener el ID del usuario actual
        asistenciaService.generarQRCodeAsistencia(usuarioId);
        // Puedes guardar el QR generado en una base de datos o en el sistema de archivos si es necesario
    }

    private Integer obtenerUsuarioActualId() {
        // Implementa la lógica para obtener el ID del usuario actual
        // Por ejemplo, desde la sesión, base de datos, etc.
        return 1; // Valor ficticio, reemplaza con la lógica real
    }
}
