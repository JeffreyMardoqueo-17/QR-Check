package control.asistencia.QRCheck.services.iterfaces;

import control.asistencia.QRCheck.models.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IUsuarioService {
    Page<Usuario> buscarTodosPaginados(Pageable pageable);

    List<Usuario> obtenerTodos();

    Optional<Usuario> buscarPorId(Integer id);

    Usuario createOrEditOne(Usuario usuario);

    void eliminarPorId(Integer usuario);


}