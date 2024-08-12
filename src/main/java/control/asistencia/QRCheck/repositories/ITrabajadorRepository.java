package control.asistencia.QRCheck.repositories;

import control.asistencia.QRCheck.models.Trabajador;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITrabajadorRepository extends JpaRepository <Trabajador, Integer> {
}
