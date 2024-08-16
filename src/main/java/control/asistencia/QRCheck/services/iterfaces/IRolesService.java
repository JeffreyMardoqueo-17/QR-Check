package control.asistencia.QRCheck.services.iterfaces;

import control.asistencia.QRCheck.models.Roles;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IRolesService {
    Page<Roles> buscarTodosPaginados(Pageable pageable);

    List<Roles> obtenerTodos();

    Optional<Roles> buscarPorId(Integer id);

    Roles crearOEditar(Roles roles);

    void eliminarPorId(Integer id);
}
