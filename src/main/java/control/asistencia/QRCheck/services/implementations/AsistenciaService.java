package control.asistencia.QRCheck.services.implementations;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.common.BitMatrix;
import control.asistencia.QRCheck.models.Asistencia;
import control.asistencia.QRCheck.repositories.IAsistenciaRepository;
import control.asistencia.QRCheck.services.iterfaces.IAsistenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.Base64;
import java.util.List;

@Service
public class AsistenciaService implements IAsistenciaService {

    @Autowired
    private IAsistenciaRepository asistenciaRepository; // Dependencia para el acceso a datos

    @Override
    public Asistencia marcarAsistencia(Integer usuarioId) {
        // Lógica para marcar la asistencia
        // Aquí deberías implementar la lógica para registrar la asistencia del usuario en la base de datos.
        // Este es solo un ejemplo.
        Asistencia asistencia = new Asistencia();
        // Asigna la información a la asistencia según sea necesario.
        return asistenciaRepository.save(asistencia);
    }

    @Override
    public byte[] generarQRCodeAsistencia(Integer asistenciaId) {
        String qrData = "http://localhost:8080/asistencias/marcar?asistenciaId=" + asistenciaId;
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        try {
            BitMatrix bitMatrix = qrCodeWriter.encode(qrData, BarcodeFormat.QR_CODE, 300, 300);
            BufferedImage image = new BufferedImage(300, 300, BufferedImage.TYPE_INT_RGB);
            for (int x = 0; x < 300; x++) {
                for (int y = 0; y < 300; y++) {
                    image.setRGB(x, y, bitMatrix.get(x, y) ? 0x000000 : 0xFFFFFF);
                }
            }
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(image, "png", outputStream);
            return outputStream.toByteArray();
        } catch (WriterException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Asistencia> obtenerTodasAsistencias() {
        return asistenciaRepository.findAll(); // Obtiene todas las asistencias del repositorio
    }
}
