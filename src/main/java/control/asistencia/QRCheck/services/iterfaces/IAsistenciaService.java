    package control.asistencia.QRCheck.services.iterfaces;

    import control.asistencia.QRCheck.models.Asistencia;
    import java.util.List;

    public interface IAsistenciaService {
        Asistencia marcarAsistencia(Integer usuarioId);
        List<Asistencia> obtenerTodasAsistencias();
        byte[] generarQRCodeAsistencia(Integer asistenciaId);  // Nuevo método
    }
