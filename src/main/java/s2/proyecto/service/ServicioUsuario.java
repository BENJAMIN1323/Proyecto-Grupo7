package s2.proyecto.service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import s2.proyecto.exception.CredencialesErroneasException;
import s2.proyecto.model.Roles;
import s2.proyecto.model.Usuario;
import s2.proyecto.model.Autenticacion;
import s2.proyecto.repository.RepositorioUsuario;

@Service
public class ServicioUsuario {
    @Autowired
    private RepositorioUsuario usuarioRepository;

    public Usuario findByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    public List<Usuario> obtenerEvaluadores() {
        return usuarioRepository.findByRol(Roles.EVALUADOR);
    }


    public Usuario findById(long id) {
        return usuarioRepository.findById(id);
    }

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    public Usuario create(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public void guardarUsuario(Usuario usuario) {
        usuarioRepository.save(usuario);
    }

    public void deleteById(long id) {
        usuarioRepository.deleteById(id);
    }

    public void guardarContrasena(Long usuarioId, String nuevaContrasena) {
        Optional<Usuario> optionalUsuario = usuarioRepository.findById(usuarioId);

        if (optionalUsuario.isPresent()) {
            Usuario usuario = optionalUsuario.get();
            
            usuario.setPassword(nuevaContrasena);
            usuarioRepository.save(usuario);
        } else {
            throw new RuntimeException("No se encontró el usuario con ID " + usuarioId);            
        }
    }

    public void enviarCredenciales(Usuario usuario) {
        String asunto = "Credenciales de acceso";
        String cuerpo = " tus credenciales son:\n"
                + "Usuario: " + usuario.getId() + "\n"
                + "Contraseña: " + usuario.getPassword();

        enviarCorreoElectronico(usuario.getEmail(), asunto, cuerpo);
    }

    private void enviarCorreoElectronico(String destinatario, String asunto, String cuerpo) {
        String correoRemitente = "ingsoftware@gmail.com";
        String contraseñaRemitente = "aogu msma kome lttg";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(correoRemitente, contraseñaRemitente);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(correoRemitente));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
            message.setSubject(asunto);
            message.setText(cuerpo);

            Transport.send(message);

            System.out.println("Correo enviado con exito hacia: " + destinatario);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean validarCredenciales(String id, String password) {
        Usuario usuario = usuarioRepository.findById(Long.parseLong(id));

        return usuario != null && usuario.validarCredenciales(id, password);
    }


    public List<Usuario> obtenerUsuariosPorRol(Roles evaluador) {
        return null;
    }

    @Transactional
    public void autenticarUsuario(Long id, String password) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow();

        if (!usuario.validarCredenciales(id.toString(), password)) {
            throw new CredencialesErroneasException("Credenciales erroneas");
        }
        Autenticacion.setUsuarioAutenticado(usuario);
    }
}
