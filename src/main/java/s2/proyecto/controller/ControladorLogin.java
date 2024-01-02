package s2.proyecto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import s2.proyecto.exception.CredencialesErroneasException;
import s2.proyecto.exception.NotFoundException;
import s2.proyecto.model.Roles;
import s2.proyecto.model.Autenticacion;
import s2.proyecto.service.ServicioUsuario;

@Controller
public class ControladorLogin {

    @Autowired
    private ServicioUsuario usuarioService;

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam Long id, @RequestParam String password, Model model) {
        try {
            usuarioService.autenticarUsuario(id, password);

            Roles rol = Autenticacion.getUsuarioAutenticado().getRol();

            if (rol == Roles.ADMINISTRADOR) {
                return "redirect:/home";
            } else if (rol == Roles.EVALUADOR) {
                return "redirect:/evaluaciones/lista";
            } else if (rol == Roles.VISADOR) {
                return "redirect:/promedio/lista";
            } else {
                model.addAttribute("error", "Rol Not Found");
                return "login";
            }
        } catch (NotFoundException e) {
            model.addAttribute("error", "Usuario Not Found");
        } catch (CredencialesErroneasException e) {
            model.addAttribute("error", "Credenciales erroneas");
        }

        return "login";
    }
}