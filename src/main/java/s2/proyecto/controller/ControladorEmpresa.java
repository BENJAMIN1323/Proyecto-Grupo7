package s2.proyecto.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import s2.proyecto.model.Empresa;
import s2.proyecto.service.ServicioEmpresa;

@Controller
@RequestMapping(value = "/empresa", produces = "application/json")
public class ControladorEmpresa {
    @Autowired
    private ServicioEmpresa empresaService;

    @GetMapping(value= "/lista")
    public String listaEmpresas(Model model) {
        List<Empresa> empresas = empresaService.findAll();
        model.addAttribute("empresas", empresas);
        return "listaEmpresas";
    }

    @GetMapping(value= "/formulario")
    public String mostrarFormulario(Model model) {
        model.addAttribute("empresa", new Empresa());
        return "formularioEmpresa";
    }

    @GetMapping(value= "/{id}")
    public String findById(Model model, Long id) {
        Empresa empresa = empresaService.findById(id);
        model.addAttribute("empresa", empresa);
        return "formularioEmpresa";
    }

    @GetMapping(value= "/nombre/{nombre}")
    public String findByNombre(Model model, String nombre) {
        Empresa empresa = empresaService.findByNombre(nombre);
        model.addAttribute("empresa", empresa);
        return "formularioEmpresa";
    }

    @PostMapping(value= "/guardarEmpresa")
    public String guardarEmpresa(Empresa empresa) {
        empresaService.guardarEmpresa(empresa);
        return "redirect:/empresa/lista";
    }

    @GetMapping(value= "/eliminar/{id}")
    public String eliminarEmpresa(@PathVariable Long id) {
        empresaService.deleteById(id);
        return "redirect:/empresa/lista";
    }

    @GetMapping(value= "/editar/{id}")
    public String editarEmpresa(Model model, @PathVariable Long id) {
        Empresa empresa = empresaService.findById(id);
        model.addAttribute("empresa", empresa);
        return "formularioEmpresa";
    }

    @GetMapping(value= "/evaluar/{id}")
    public String evaluarEmpresa(Model model, Long id) {
        Empresa empresa = empresaService.findById(id);
        model.addAttribute("empresa", empresa);
        return "evaluarEmpresa";
    }

}
