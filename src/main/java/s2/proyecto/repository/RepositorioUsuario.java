package s2.proyecto.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import s2.proyecto.model.Roles;
import s2.proyecto.model.Usuario;

@Repository
public interface RepositorioUsuario extends JpaRepository<Usuario, Long> {

    Usuario findByEmail(String email);

    Usuario findByEmailAndPassword(String email, String password);

    Usuario findById(long id);

    List<Usuario> findByRol(Roles rol);

}
