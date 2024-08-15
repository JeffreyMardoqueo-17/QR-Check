package control.asistencia.QRCheck.repositories;

import control.asistencia.QRCheck.models.Empresa;
import control.asistencia.QRCheck.models.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IEmpresaRepository extends JpaRepository<Empresa, Integer> {
}
