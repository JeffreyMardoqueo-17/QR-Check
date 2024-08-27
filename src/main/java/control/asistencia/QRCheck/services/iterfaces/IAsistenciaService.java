package control.asistencia.QRCheck.services.iterfaces;
import java.util.List;

import control.asistencia.QRCheck.models.Asistencia;

public interface IAsistenciaService {
    Asistencia marcarAsistencia(Integer usuarioId);
    byte[] generarQRCodeAsistencia(Integer asistenciaId);
    List<Asistencia> obtenerTodasAsistencias();

}
