package control.asistencia.QRCheck.repositories;

import control.asistencia.QRCheck.models.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRolesRepository extends JpaRepository<Roles, Integer> {
}
