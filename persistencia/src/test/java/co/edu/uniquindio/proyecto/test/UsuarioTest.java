package co.edu.uniquindio.proyecto.test;

import co.edu.uniquindio.proyecto.entidades.Usuario;
import co.edu.uniquindio.proyecto.repositorios.CiudadRepo;
import co.edu.uniquindio.proyecto.repositorios.UsuarioRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UsuarioTest {

    @Autowired
    private UsuarioRepo usuarioRepo;

    @Autowired
    private CiudadRepo ciudadRepo;

    @Test
    @Sql("classpath:datos.sql")
    public void registrarTest(){
        Usuario usuarioNuevo = new Usuario("126","Lee Lopez","elchino@email.com","micontlasena");

        Usuario usuarioGuardado = usuarioRepo.save(usuarioNuevo);

        Assertions.assertNotNull(usuarioGuardado);
    }

    @Test
    @Sql("classpath:datos.sql")
    public void eliminarTest(){

        Usuario guardado = usuarioRepo.findById("123").orElse(null);

        Assertions.assertNotNull(guardado);

        usuarioRepo.delete(guardado);

        Usuario guardado2 = usuarioRepo.findById("123").orElse(null);

        Assertions.assertNull(guardado2);
    }

    @Test
    @Sql("classpath:datos.sql")
    public void actualizarTest(){

        Usuario guardado = usuarioRepo.findById("124").orElse(null);
        guardado.setEmail("maria1@email.com");

        usuarioRepo.save(guardado);

        Usuario usuarioBuscado = usuarioRepo.findById("124").orElse(null);

        Assertions.assertEquals("maria1@email.com", usuarioBuscado.getEmail());
    }

    @Test
    @Sql("classpath:datos.sql")
    public void listarTest(){

        List<Usuario> usuarios = usuarioRepo.findAll();

        usuarios.forEach(u -> System.out.println(u));
    }

}
