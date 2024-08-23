package control.asistencia.QRCheck.services.iterfaces;

public interface IQRService {
    byte[] generateQRCode(String text, int width, int height);
}
