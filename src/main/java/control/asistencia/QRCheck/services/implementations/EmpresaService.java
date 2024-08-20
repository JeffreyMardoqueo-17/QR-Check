package control.asistencia.QRCheck.services.implementations;

import control.asistencia.QRCheck.models.Empresa;
import control.asistencia.QRCheck.models.Roles;
import control.asistencia.QRCheck.repositories.IEmpresaRepository;
import control.asistencia.QRCheck.services.iterfaces.IEmpresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmpresaService implements IEmpresaService {

    @Autowired
    private IEmpresaRepository empresaRepository;

    @Override
    public Page<Empresa> buscarTodosPaginados(Pageable pageable) {
        return empresaRepository.findAll(pageable);
    }

    @Override
    public List<Empresa> obtenerTodos() {
        return empresaRepository.findAll();
    }

    @Override
    public Optional<Empresa> buscarPorId(Integer id) {
        return empresaRepository.findById(id);
    }

    @Override
    public Empresa crearOEditar(Empresa empresa) {
        return empresaRepository.save(empresa);
    }

    @Override
    public void eliminarPorId(Integer id) {
        empresaRepository.deleteById(id);
    }
}
