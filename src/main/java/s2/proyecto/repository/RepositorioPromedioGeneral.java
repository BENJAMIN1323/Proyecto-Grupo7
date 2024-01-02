package s2.proyecto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import s2.proyecto.model.EstadoPromedio;
import s2.proyecto.model.PromedioGeneral;

import java.util.List;

@Repository
public interface RepositorioPromedioGeneral extends JpaRepository<PromedioGeneral, Long> {
    List<PromedioGeneral> findByEmpresaId(Long empresaId);

    List<PromedioGeneral> findByVisadorId(Long visadorId);

    List<PromedioGeneral> findByEstado(EstadoPromedio estado);

    List<PromedioGeneral> findByEmpresaIdAndEstado(Long empresaId, EstadoPromedio estado);

    

}