package s2.proyecto.service;

import java.util.List;

import s2.proyecto.model.PromedioGeneral;

public interface ServicioPromedioGeneral {
    List<PromedioGeneral> obtenerTodosLosPromediosGenerales();
    PromedioGeneral obtenerPromedioGeneralPorId(Long id);
    void guardarPromedioGeneral(PromedioGeneral promedioGeneral);
    void save(PromedioGeneral promedioGeneral);
    double calcularNotaFinal(PromedioGeneral promedioGeneral);
    void asignarVisador(Long idPromedioGeneral, Long idVisador);
}
