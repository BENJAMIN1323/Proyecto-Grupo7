package s2.proyecto.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import s2.proyecto.model.Empresa;
import s2.proyecto.model.Evaluacion;
import s2.proyecto.model.PromedioGeneral;
import s2.proyecto.model.Roles;
import s2.proyecto.model.Usuario;
import s2.proyecto.repository.RepositorioEvaluacion;
import s2.proyecto.repository.RepositorioUsuario;
import s2.proyecto.service.ServicioEmpresa;
import s2.proyecto.service.ServicioPromedioGeneral;
import s2.proyecto.service.ServicioUsuario;

@Controller
@RequestMapping("/promedio")
public class ControladorPromedioGeneral {
    private final ServicioPromedioGeneral promedioGeneralService;
    private final ServicioEmpresa empresaService;
    private final ServicioUsuario usuarioService;
    private final RepositorioUsuario usuarioRepository;

    @Autowired
    public ControladorPromedioGeneral(ServicioPromedioGeneral promedioGeneralService, ServicioEmpresa empresaService, ServicioUsuario usuarioService, RepositorioUsuario usuarioRepository) {
        this.promedioGeneralService = promedioGeneralService;
        this.empresaService = empresaService;
        this.usuarioService = usuarioService;
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping("/lista")
    public String listaPromediosGenerales(Model model) {
        List<PromedioGeneral> promediosGenerales = promedioGeneralService.obtenerTodosLosPromediosGenerales();
        model.addAttribute("promediosGenerales", promediosGenerales);
        return "evaluaciones/promedios";
    }

    @GetMapping("/ver/{id}")
    public String verPromedioGeneral(@PathVariable Long id, Model model) {
        PromedioGeneral promedioGeneral = promedioGeneralService.obtenerPromedioGeneralPorId(id);
        model.addAttribute("promedioGeneral", promedioGeneral);
        return "evaluaciones/verPromedio";
    }

    @GetMapping("/crear")
    public String mostrarFormularioCreacion(Model model) {
        List<Empresa> empresas = empresaService.findAll();
        List<Usuario> visadores = usuarioService.obtenerUsuariosPorRol(Roles.VISADOR);
        model.addAttribute("empresas", empresas);
        model.addAttribute("visadores", visadores);
        model.addAttribute("promedioGeneral", new PromedioGeneral());
        return "promedio-general/crear";
    }

    @PostMapping("/guardarObservaciones")
    public String guardarObservaciones(
            @RequestParam("promedioId") Long promedioId,
            @RequestParam("observaciones") String observaciones,
            Model model) {
        PromedioGeneral promedioGeneral = promedioGeneralService.obtenerPromedioGeneralPorId(promedioId);
        promedioGeneral.setObservaciones(observaciones);
        promedioGeneralService.guardarPromedioGeneral(promedioGeneral);

        return "redirect:/promedio/ver/" + promedioId;
    }

    @PostMapping("/guardarYVisar")
    public String guardarYVisar(
            @RequestParam("promedioId") Long promedioId,
            Model model) {
        PromedioGeneral promedioGeneral = promedioGeneralService.obtenerPromedioGeneralPorId(promedioId);
        promedioGeneral.visar();
        promedioGeneralService.guardarPromedioGeneral(promedioGeneral);

        return "redirect:/promedio/ver/" + promedioId;
    }

    @PostMapping("/guardarYPendiente")
    public String guardarYPendiente(
            @RequestParam("promedioId") Long promedioId,
            Model model) {
        PromedioGeneral promedioGeneral = promedioGeneralService.obtenerPromedioGeneralPorId(promedioId);
        promedioGeneral.pendiente();
        promedioGeneralService.guardarPromedioGeneral(promedioGeneral);

        return "redirect:/promedio/ver/" + promedioId;
    }

    @PostMapping("/crear")
    public String crearPromedioGeneral(@ModelAttribute PromedioGeneral promedioGeneral) {
        promedioGeneralService.guardarPromedioGeneral(promedioGeneral);
        return "redirect:/promedio/lista";
    }

    @GetMapping("/asignarVisador/{promedioId}")
    public String mostrarFormularioAsignarVisador(Model model, @PathVariable Long promedioId) {
        List<Usuario> visadores = usuarioRepository.findByRol(Roles.VISADOR);

        model.addAttribute("visadores", visadores);

        model.addAttribute("promedioId", promedioId);

        return "/evaluaciones/asignarVisador";
    }

    @PostMapping("/asignarVisador")
    public String asignarVisador(@RequestParam Long promedioId, @RequestParam Long idVisador) {
        try {
            promedioGeneralService.asignarVisador(promedioId, idVisador);
            return "redirect:/promedio/lista";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/promedio/asignarVisador/" + promedioId + "?error";
        }
    }
}