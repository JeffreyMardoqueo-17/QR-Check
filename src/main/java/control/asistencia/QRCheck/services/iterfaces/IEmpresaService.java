package control.asistencia.QRCheck.services.iterfaces;

import control.asistencia.QRCheck.models.Empresa;
import control.asistencia.QRCheck.models.Roles;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IEmpresaService {
    Page<Empresa> buscarTodosPaginados(Pageable pageable);

    List<Empresa> obtenerTodos();

    Optional<Empresa> buscarPorId(Integer id);

    Empresa crearOEditar(Empresa Empresa);

    void eliminarPorId(Integer id);
}
