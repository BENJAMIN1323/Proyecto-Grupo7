package s2.proyecto.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import s2.proyecto.model.Empresa;
import s2.proyecto.model.Evaluacion;
import s2.proyecto.model.Usuario;
import s2.proyecto.repository.RepositorioEvaluacion;
import s2.proyecto.repository.RepositorioUsuario;

import java.util.List;

@Service
public class ServicioEvaluacionImpl implements ServicioEvaluacion {
    private final RepositorioEvaluacion evaluacionRepository;
    private final RepositorioUsuario usuarioRepository;

    @Autowired
    public ServicioEvaluacionImpl(RepositorioEvaluacion evaluacionRepository, RepositorioUsuario usuarioRepository) {
        this.evaluacionRepository = evaluacionRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public List<Evaluacion> obtenerTodasLasEvaluaciones() {
        return evaluacionRepository.findAll();
    }

    @Override
    public Evaluacion obtenerEvaluacionPorId(Long id) {
        return evaluacionRepository.findById(id).orElse(null);
    }

    @Override
    public void asignarEvaluacion(Long idEvaluacion, Long idEvaluador) {
        Evaluacion evaluacion = evaluacionRepository.findById(idEvaluacion).orElse(null);
        Usuario evaluador = usuarioRepository.findById(idEvaluador).orElse(null);

        if (evaluacion != null && evaluador != null) {
            evaluacion.setEvaluador(evaluador);
            evaluacionRepository.save(evaluacion);
        }
    }

    @Override
    public List<Evaluacion> obtenerEvaluacionesPorEmpresa(Long empresaId) {

        return null;
    }

    @Override
    public void aprobarEvaluacion(Long evaluacionId) {
        Evaluacion evaluacion = evaluacionRepository.findById(evaluacionId).orElse(null);
        if (evaluacion != null) {
            evaluacion.aprobar();
            evaluacionRepository.save(evaluacion);
        }
    }

    @Override
    public void desaprobarEvaluacion(Long evaluacionId) {
        Evaluacion evaluacion = evaluacionRepository.findById(evaluacionId).orElse(null);
        if (evaluacion != null) {
            evaluacion.desaprobar();
            evaluacionRepository.save(evaluacion);
        }
    }

    @Override
    public void guardarEvaluacion(Evaluacion evaluacion) {
        evaluacionRepository.save(evaluacion);
    }


    @Override
    public void asignarEvaluador(Long idEvaluacion, Long idEvaluador) {
        Evaluacion evaluacion = evaluacionRepository.findById(idEvaluacion).orElseThrow(() -> new RuntimeException("Evaluacion Not Found ID: " + idEvaluacion));
        Usuario evaluador = usuarioRepository.findById(idEvaluador).orElseThrow(() -> new RuntimeException("Usuario evaluador Not Found ID: " + idEvaluador));

        evaluacion.setEvaluador(evaluador);

        evaluacionRepository.save(evaluacion);
    }


    @Override
    public List<Evaluacion> obtenerEvaluacionesSinAsignar() {

        return null;
    }

    @Override
    public List<Evaluacion> obtenerEvaluacionesAsignadas() {

        return null;
    }

    @Override
    public List<Evaluacion> obtenerEvaluacionesAsignadasPorEvaluador(Usuario evaluador) {
        throw new UnsupportedOperationException("Unimplemented method 'obtenerEvaluacionesAsignadasPorEvaluador'");
    }

    @Override
    public List<Evaluacion> obtenerEvaluacionesSinAsignarPorEvaluador(Usuario evaluador) {
        throw new UnsupportedOperationException("Unimplemented method 'obtenerEvaluacionesSinAsignarPorEvaluador'");
    }

    @Override
    public void desasignarEvaluacionesPorEvaluador(Usuario evaluador) {
        throw new UnsupportedOperationException("Unimplemented method 'desasignarEvaluacionesPorEvaluador'");
    }

    

}
