package control.asistencia.QRCheck.services.implementations;

import control.asistencia.QRCheck.models.Usuario;
import control.asistencia.QRCheck.repositories.IUsuarioRepository;
import control.asistencia.QRCheck.services.iterfaces.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class UsuarioService implements IUsuarioService {

//    @Autowired
//    private PasswordEncoder passwordEncoder;

    // Inyecto una instancia de IUsuarioRepository para interactuar con la base de datos.
    @Autowired
    private IUsuarioRepository usuarioRepository;

    // Método para buscar todos los usuarios de manera paginada.
    @Override
    public Page<Usuario> buscarTodosPaginados(Pageable pageable) {

        // Retorno una página de usuarios usando el método findAll de usuarioRepository con paginación.
        return usuarioRepository.findAll(pageable);
    }


    // Método para obtener una lista con todos los usuarios.
    @Override
    public List<Usuario> obtenerTodos() {

        // Retorno todos los usuarios de la base de datos.
        return usuarioRepository.findAll();
    }


    // Método para buscar un usuario por su ID.
    @Override
    public Optional<Usuario> buscarPorId(Integer id) {
        // Retorno un Optional que puede o no contener un usuario con el ID especificado.
        return usuarioRepository.findById(id);
    }


    // Método para crear o editar un usuario.
    @Override
    public Usuario createOrEditOne(Usuario usuario) {

//        String encodedPassword = passwordEncoder.encode(usuario.getPass());
//        usuario.setPass(encodedPassword);

        // Verifico si el usuario tiene un ID, lo que indicaría que ya existe.
        if(usuario.getId() != null){

            // Busco el usuario existente en la base de datos usando su ID.
            Usuario existingUser = usuarioRepository.findById(usuario.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

            // Verifico si la contraseña no fue proporcionada.
            if (usuario.getPass() == null || usuario.getPass().isEmpty()) {

                // Si no se proporciona una nueva contraseña, mantengo la existente.
                usuario.setPass(existingUser.getPass());
            }
        }

        // Guardo el usuario (ya sea nuevo o actualizado) en la base de datos y lo retorno.
        return usuarioRepository.save(usuario);
    }


    // Método para eliminar un usuario por su ID.
    @Override
    public void eliminarPorId(Integer id) {

        // Busco el usuario en la base de datos usando su ID.
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);

        // Verifico si el usuario existe.
        if (usuarioOpt.isPresent()) {

            // Si existe, lo elimino de la base de datos.
            usuarioRepository.delete(usuarioOpt.get());
        } else {

            // Si no existe, lanzo una excepción indicando que el usuario no fue encontrado.
            throw new IllegalArgumentException("Usuario no encontrado");
        }
    }

}