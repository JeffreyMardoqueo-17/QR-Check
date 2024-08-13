package control.asistencia.QRCheck.services.implementations;

import control.asistencia.QRCheck.repositories.ITrabajadorRepository;
import control.asistencia.QRCheck.services.iterfaces.ITrabajadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TrabajadorService implements ITrabajadorService {

    @Autowired
    private ITrabajadorRepository trabajadorRepository;

    @Override
    public Page<Trabajador> buscarTodosPaginados(Pageable pageable) {
        return trabajadorRepository.findAll(pageable);    }

    @Override
    public List<Trabajador> obtenerTodos() {
        return trabajadorRepository.findAll();
    }

    @Override
    public Optional<Trabajador> buscarPorId(Integer id) {
        return trabajadorRepository.findById(id);
    }

    @Override
    public Trabajador crearOEditOne(Trabajador trabajador) {
        return  trabajadorRepository.save(trabajador);
    }

    @Override
    public void eliminarPorId(Integer id) {
        trabajadorRepository.deleteById(id);

    }
}
