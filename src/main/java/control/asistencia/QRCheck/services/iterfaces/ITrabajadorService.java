package control.asistencia.QRCheck.services.iterfaces;

import control.asistencia.QRCheck.models.Trabajador;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ITrabajadorService {

    Page<Trabajador> buscarTodosPaginados(Pageable pageable);

    List<Trabajador> obtenerTodos();

    Optional<Trabajador> buscarPorId(Integer id);

        Trabajador crearOEditOne(Trabajador trabajador);

    void eliminarPorId(Integer id);
}
