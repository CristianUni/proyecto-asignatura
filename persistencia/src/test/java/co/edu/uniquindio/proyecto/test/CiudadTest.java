package co.edu.uniquindio.proyecto.test;

import co.edu.uniquindio.proyecto.entidades.Ciudad;
import co.edu.uniquindio.proyecto.entidades.Usuario;
import co.edu.uniquindio.proyecto.repositorios.CiudadRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CiudadTest {


    @Autowired
    private CiudadRepo ciudadRepo;

    @Test
    @Sql("classpath:datos.sql")
    public void crearCiudadTest(){
        Ciudad ciudad = new Ciudad("Montenegro");
        Ciudad ciudadGuardada = ciudadRepo.save(ciudad);

        Assertions.assertNotNull(ciudadGuardada);
    }

    @Test
    @Sql("classpath:datos.sql")
    public void eliminarCiudadTest(){

        Ciudad guardada = ciudadRepo.findById(1).orElse(null);

        Assertions.assertNotNull(guardada);

        ciudadRepo.delete(guardada);

        Ciudad guardada2 = ciudadRepo.findById(1).orElse(null);

        Assertions.assertNull(guardada2);
    }

    @Test
    @Sql("classpath:datos.sql")
    public void actualizarCiudadTest(){

        Ciudad guardada = ciudadRepo.findById(1).orElse(null);
        guardada.setNombre("Tebaida");

        ciudadRepo.save(guardada);

        Ciudad ciudadBuscada = ciudadRepo.findById(1).orElse(null);

        Assertions.assertEquals("Tebaida", ciudadBuscada.getNombre());
    }

    @Test
    @Sql("classpath:datos.sql")
    public void listarCiudadesTest(){

        List<Ciudad> ciudades = ciudadRepo.findAll();

        ciudades.forEach(u -> System.out.println(u));
    }


}
