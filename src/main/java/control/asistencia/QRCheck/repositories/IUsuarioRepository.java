package control.asistencia.QRCheck.repositories;

import control.asistencia.QRCheck.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUsuarioRepository extends JpaRepository<Usuario, Integer> {
}
