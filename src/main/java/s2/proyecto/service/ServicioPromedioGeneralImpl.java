package s2.proyecto.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import s2.proyecto.model.PromedioGeneral;
import s2.proyecto.model.Usuario;
import s2.proyecto.repository.RepositorioEvaluacion;
import s2.proyecto.repository.RepositorioPromedioGeneral;
import s2.proyecto.repository.RepositorioUsuario;

@Service
public class ServicioPromedioGeneralImpl implements ServicioPromedioGeneral {
    private final RepositorioPromedioGeneral promedioGeneralRepository;
    private final RepositorioUsuario usuarioRepository;

    @Autowired
    public ServicioPromedioGeneralImpl(RepositorioPromedioGeneral promedioGeneralRepository,
            RepositorioUsuario usuarioRepository) {
        this.promedioGeneralRepository = promedioGeneralRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public List<PromedioGeneral> obtenerTodosLosPromediosGenerales() {
        return promedioGeneralRepository.findAll();
    }

    @Override
    public PromedioGeneral obtenerPromedioGeneralPorId(Long id) {
        return promedioGeneralRepository.findById(id).orElse(null);
    }

    @Override
    public void guardarPromedioGeneral(PromedioGeneral promedioGeneral) {
        promedioGeneral.setPromedioFinal(calcularNotaFinal(promedioGeneral));
        promedioGeneralRepository.save(promedioGeneral);
    }

    @Override
    public void save(PromedioGeneral promedioGeneral) {
        promedioGeneral.setPromedioFinal(calcularNotaFinal(promedioGeneral));
        promedioGeneralRepository.save(promedioGeneral);
    }

    @Override
    public double calcularNotaFinal(PromedioGeneral promedioGeneral) {
        int notaCompatibilidad = promedioGeneral.getNotaCompatibilidad();
        int notaUsabilidad = promedioGeneral.getNotaUsabilidad();
        int notaFiabilidad = promedioGeneral.getNotaFiabilidad();
        int notaSeguridad = promedioGeneral.getNotaSeguridad();

        return (notaCompatibilidad + notaUsabilidad + notaFiabilidad + notaSeguridad) / 4.0;
    }

    @Override
    public void asignarVisador(Long idPromedioGeneral, Long idVisador) {
        PromedioGeneral promedioGeneral = promedioGeneralRepository.findById(idPromedioGeneral).orElse(null);
        Usuario visador = usuarioRepository.findById(idVisador).orElse(null);

        if (promedioGeneral != null) {
            promedioGeneral.setVisador(visador);
            promedioGeneralRepository.save(promedioGeneral);
        }
    }

}