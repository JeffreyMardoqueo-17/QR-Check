package control.asistencia.QRCheck.services.implementations;

import control.asistencia.QRCheck.models.Asistencia;
import control.asistencia.QRCheck.models.Usuario;
import control.asistencia.QRCheck.repositories.IAsistenciaRepository;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import control.asistencia.QRCheck.services.iterfaces.IAsistenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AsistenciaService implements IAsistenciaService {

    @Autowired
    private IAsistenciaRepository asistenciaRepository;

    @Override
    public Asistencia marcarAsistencia(Integer usuarioId) {
        Asistencia asistencia = new Asistencia();
        Usuario usuario = new Usuario();  // Crear una nueva instancia de Usuario
        usuario.setId(usuarioId);  // Establecer el ID del usuario
        asistencia.setUsuario(usuario); // Asignar el usuario a la asistencia
        asistencia.setFecha(LocalDate.now());
        asistencia.setHoraEntrada(LocalTime.now());
        return asistenciaRepository.save(asistencia);
    }

    @Override
    public List<Asistencia> obtenerTodasAsistencias() {
        return asistenciaRepository.findAll();
    }

    @Override
    public byte[] generarQRCodeAsistencia(Integer usuarioId) {
        // Crear la asistencia
        Asistencia nuevaAsistencia = marcarAsistencia(usuarioId);

        // Generar la URL para el QR usando el ID recién creado
        String url = "http://localhost:8080/marcarAsistencia?asistenciaId=" + nuevaAsistencia.getId();
        int width = 200;
        int height = 200;

        try {
            Map<EncodeHintType, ErrorCorrectionLevel> hints = new HashMap<>();
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(url, BarcodeFormat.QR_CODE, width, height, hints);
            ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
            return pngOutputStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}