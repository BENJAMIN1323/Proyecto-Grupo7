package s2.proyecto.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import s2.proyecto.model.Evaluacion;

public interface RepositorioEvaluacion extends JpaRepository<Evaluacion, Long> {
    Evaluacion findById(long id);

    Evaluacion findByEmpresaId(long id);

    Evaluacion findByEvaluadorId(long id);

    List<Evaluacion> findByFecha(LocalDateTime fecha);

    Evaluacion findByTipo(String tipo);
    
    Evaluacion findByEstado(String estado);

    @Query("SELECT e FROM Evaluacion e WHERE e.empresa.id = :empresaId")
    List<Evaluacion> obtenerEvaluacionesPorEmpresa(@Param("empresaId") Long empresaId);

}