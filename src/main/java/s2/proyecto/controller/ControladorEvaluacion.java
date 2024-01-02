package s2.proyecto.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import s2.proyecto.model.Empresa;
import s2.proyecto.model.VerificacionEvaluacion;
import s2.proyecto.model.Evaluacion;
import s2.proyecto.model.Anexo;
import s2.proyecto.model.PromedioGeneral;
import s2.proyecto.model.Roles;
import s2.proyecto.model.Usuario;
import s2.proyecto.repository.RepositorioUsuario;
import s2.proyecto.service.ServicioEmpresa;
import s2.proyecto.service.ServicioEvaluacion;
import s2.proyecto.service.ServicioUsuario;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/evaluaciones")
public class ControladorEvaluacion {
    private final ServicioEvaluacion evaluacionService;
    private final ServicioEmpresa empresaService;
    private final ServicioUsuario usuarioService;        
    private final RepositorioUsuario usuarioRepository;

    public ControladorEvaluacion(ServicioEvaluacion evaluacionService, ServicioEmpresa empresaService, ServicioUsuario usuarioService, RepositorioUsuario usuarioRepository) {
        this.evaluacionService = evaluacionService;
        this.empresaService = empresaService;
        this.usuarioService = usuarioService;
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping("/lista")
    public String listaEvaluaciones(Model model) {
        List<Evaluacion> evaluaciones = evaluacionService.obtenerTodasLasEvaluaciones();
        model.addAttribute("evaluaciones", evaluaciones);
        return "evaluaciones/lista";
    }

    @GetMapping("/ver/{id}")
    public String verEvaluacion(@PathVariable Long id, Model model) {
        Evaluacion evaluacion = evaluacionService.obtenerEvaluacionPorId(id);
        model.addAttribute("evaluacion", evaluacion);
        return "evaluaciones/ver";
    }

    @GetMapping("/empresa/{empresaId}")
    public String evaluacionesPorEmpresa(@PathVariable Long empresaId, Model model) {
        List<Evaluacion> evaluaciones = evaluacionService.obtenerEvaluacionesPorEmpresa(empresaId);
        model.addAttribute("evaluacion", evaluaciones);
        return "evaluaciones/lista";
    }

    @PostMapping("/aprobar/{id}")
    public String aprobarEvaluacion(@PathVariable Long id) {
        evaluacionService.aprobarEvaluacion(id);
        return "redirect:/evaluaciones/lista";
    }

    @PostMapping("/desaprobar/{id}")
public String desaprobarEvaluacion(@PathVariable Long id) {
    Evaluacion evaluacion = evaluacionService.obtenerEvaluacionPorId(id);
    
    if (evaluacion != null) {
        evaluacion.desaprobar();
        evaluacionService.guardarEvaluacion(evaluacion);
    } else {
        System.out.println("No se hallo la evaluación del id: " + id);
    }

    return "redirect:/evaluaciones/lista";
}


    @GetMapping("/crear")
    public String mostrarFormularioCreacion(Model model) {
        List<Empresa> empresas = empresaService.findAll();
        List<Usuario> evaluadores = usuarioService.obtenerUsuariosPorRol(Roles.EVALUADOR);
        model.addAttribute("empresas", empresas);
        model.addAttribute("evaluadores", evaluadores);
        model.addAttribute("evaluacion", new Evaluacion());
        return "evaluacion/crear";
    }

    @PostMapping("/crear")
    public String crearEvaluacion(@ModelAttribute Evaluacion evaluacion) {
        evaluacionService.guardarEvaluacion(evaluacion);
        return "redirect:/evaluaciones/lista";
    }

    @GetMapping("/formularioCompatibilidad")
    public String mostrarFormularioCompatibilidad(Model model) {
        model.addAttribute("evaluacion", new Evaluacion());
        return "evaluaciones/formularioCompatibilidad";
    }

    @PostMapping("/guardarCompatibilidad")
    public String guardarCompatibilidad(Evaluacion evaluacion) {
        evaluacion.setTipo(Anexo.COMPATIBILIDAD);
        evaluacionService.guardarEvaluacion(evaluacion);
        return "redirect:/evaluaciones";
    }

    @GetMapping("/formularioUsabilidad")
    public String mostrarFormularioUsabilidad(Model model) {
        model.addAttribute("evaluacion", new Evaluacion());
        return "evaluaciones/formularioUsabilidad";
    }

    @PostMapping("/guardarUsabilidad")
    public String guardarUsabilidad(Evaluacion evaluacion) {
        evaluacion.setTipo(Anexo.USABILIDAD);
        evaluacionService.guardarEvaluacion(evaluacion);
        return "redirect:/evaluaciones";
    }

    @GetMapping("/formularioFiabilidad")
    public String mostrarFormularioFiabilidad(Model model) {
        model.addAttribute("evaluacion", new Evaluacion());
        return "evaluaciones/formularioFiabilidad";
    }

    @PostMapping("/guardarFiabilidad")
    public String guardarFiabilidad(Evaluacion evaluacion) {
        evaluacion.setTipo(Anexo.FIABILIDAD);
        evaluacionService.guardarEvaluacion(evaluacion);
        return "redirect:/evaluaciones";
    }

    @GetMapping("/formularioSeguridad")
    public String mostrarFormularioSeguridad(Model model) {
        model.addAttribute("evaluacion", new Evaluacion());
        return "evaluaciones/formularioSeguridad";
    }

    @PostMapping("/guardarSeguridad")
    public String guardarSeguridad(Evaluacion evaluacion) {
        evaluacion.setTipo(Anexo.SEGURIDAD);
        evaluacionService.guardarEvaluacion(evaluacion);
        return "redirect:/evaluaciones";
    }

    @PostMapping("/guardarEvaluacion")
    public String guardarEvaluacion(@ModelAttribute Evaluacion evaluacion) {
        Evaluacion evaluacionExistente = evaluacionService.obtenerEvaluacionPorId(evaluacion.getId());

        evaluacionExistente.setFecha(LocalDateTime.now());

        evaluacionExistente.setPregunta1(evaluacion.getPregunta1());
        evaluacionExistente.setPregunta2(evaluacion.getPregunta2());
        evaluacionExistente.setPregunta3(evaluacion.getPregunta3());
        evaluacionExistente.setPregunta4(evaluacion.getPregunta4());
        evaluacionExistente.setPregunta5(evaluacion.getPregunta5());

        int sumaPreguntas = evaluacion.getPregunta1() + evaluacion.getPregunta2() + evaluacion.getPregunta3() + evaluacion.getPregunta4() + evaluacion.getPregunta5();
        evaluacionExistente.setNota(sumaPreguntas);

        if (evaluacionExistente.getTipo() == Anexo.COMPATIBILIDAD) {
            evaluacionExistente.getEmpresa().getPromedioGeneral().setNotaCompatibilidad(sumaPreguntas);
        } else if (evaluacionExistente.getTipo() == Anexo.FIABILIDAD) {
            evaluacionExistente.getEmpresa().getPromedioGeneral().setNotaFiabilidad(sumaPreguntas);
        } else if (evaluacionExistente.getTipo() == Anexo.USABILIDAD) {
            evaluacionExistente.getEmpresa().getPromedioGeneral().setNotaUsabilidad(sumaPreguntas);
        } else if (evaluacionExistente.getTipo() == Anexo.SEGURIDAD) {
            evaluacionExistente.getEmpresa().getPromedioGeneral().setNotaSeguridad(sumaPreguntas);
        }

        double promedioGeneral = evaluacionExistente.getEmpresa().getPromedioGeneral().calculatePromedioGeneral();
        evaluacionExistente.getEmpresa().getPromedioGeneral().setPromedioFinal(promedioGeneral);

        evaluacionExistente.setEstado(VerificacionEvaluacion.APROBADA);

        evaluacionService.guardarEvaluacion(evaluacionExistente);

        return "redirect:/evaluaciones/lista";
    }

    @GetMapping("/asignarEvaluador/{evaluacionId}")
    public String mostrarFormularioAsignarEvaluador(Model model, @PathVariable Long evaluacionId) {
        List<Usuario> evaluadores = usuarioRepository.findByRol(Roles.EVALUADOR);

        model.addAttribute("evaluadores", evaluadores);

        model.addAttribute("evaluacionId", evaluacionId);

        return "/evaluaciones/asignarEvaluador";
    }

    @PostMapping("/asignarEvaluador")
    public String asignarEvaluador(@RequestParam Long idEvaluacion, @RequestParam Long idEvaluador) {
    try {
        evaluacionService.asignarEvaluador(idEvaluacion, idEvaluador);
        return "redirect:/evaluaciones/lista";
    } catch (Exception e) {
        e.printStackTrace();
        return "redirect:/evaluaciones/asignarEvaluador/" + idEvaluacion + "?error";
    }
}

}
