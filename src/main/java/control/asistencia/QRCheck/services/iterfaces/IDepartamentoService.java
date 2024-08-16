package control.asistencia.QRCheck.services.iterfaces;

import control.asistencia.QRCheck.models.Departamento;
import control.asistencia.QRCheck.models.Roles;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IDepartamentoService {

    Page<Departamento> buscarTodosPaginados(Pageable pageable);

    List<Departamento> obtenerTodos();

    Optional<Departamento> buscarPorId(Integer id);

    Departamento crearOEditar(Departamento departamentos);

    void eliminarPorId(Integer id);
}