package s2.proyecto.service;

import java.util.List;

import s2.proyecto.model.Evaluacion;
import s2.proyecto.model.Usuario;

public interface ServicioEvaluacion {
    List<Evaluacion> obtenerTodasLasEvaluaciones();
    Evaluacion obtenerEvaluacionPorId(Long id);
    List<Evaluacion> obtenerEvaluacionesPorEmpresa(Long empresaId);
    void aprobarEvaluacion(Long evaluacionId);
    void desaprobarEvaluacion(Long evaluacionId);
    void guardarEvaluacion(Evaluacion evaluacion);
    void asignarEvaluador(Long idEvaluacion, Long idEvaluador);
    List<Evaluacion> obtenerEvaluacionesSinAsignar();
    List<Evaluacion> obtenerEvaluacionesAsignadas();
    List<Evaluacion> obtenerEvaluacionesAsignadasPorEvaluador(Usuario evaluador);
    List<Evaluacion> obtenerEvaluacionesSinAsignarPorEvaluador(Usuario evaluador);
    void asignarEvaluacion(Long idEvaluacion, Long idEvaluador);
    void desasignarEvaluacionesPorEvaluador(Usuario evaluador);
    
}
