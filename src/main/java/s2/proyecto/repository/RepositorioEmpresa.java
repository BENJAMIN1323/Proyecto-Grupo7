package s2.proyecto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import s2.proyecto.model.Empresa;

@Repository
public interface RepositorioEmpresa extends JpaRepository<Empresa, Long> {

    Empresa findById(long id);

    Empresa findByNombre(String nombre);

    Empresa findByEmail(String email);

    Empresa findByTelefono(String telefono);

}
