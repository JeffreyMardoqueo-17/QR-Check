package control.asistencia.QRCheck.repositories;

import control.asistencia.QRCheck.models.Asistencia;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAsistenciaRepository extends JpaRepository<Asistencia, Integer> {
    // Puedes añadir métodos de consulta personalizados aquí si es necesario
}
