package s2.proyecto.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import s2.proyecto.model.Empresa;
import s2.proyecto.repository.RepositorioEmpresa;

@Service
public class ServicioEmpresa {
    
    @Autowired
    private RepositorioEmpresa empresaRepository;

    public List<Empresa> findAll() {
        return empresaRepository.findAll();
    }

    public Empresa findByNombre(String nombre) {
        return empresaRepository.findByNombre(nombre);
    }

    public Empresa findByEmail(String email) {
        return empresaRepository.findByEmail(email);
    }

    public Empresa findByTelefono(String telefono) {
        return empresaRepository.findByTelefono(telefono);
    }

    public Empresa findById(long id) {
        return empresaRepository.findById(id);
    }

    public void guardarEmpresa(Empresa empresa) {
        empresaRepository.save(empresa);
    }

    public void deleteById(long id) {
        empresaRepository.deleteById(id);
    }
}
