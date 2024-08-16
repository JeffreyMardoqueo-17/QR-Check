package control.asistencia.QRCheck.repositories;

import control.asistencia.QRCheck.models.Departamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IDepartamentoRepository extends JpaRepository<Departamento, Integer> {
}
