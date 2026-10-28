package recursoshumanos.gestionempleos.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import recursoshumanos.gestionempleos.modelo.Empleado;

public interface EmpleadoRepositorio extends JpaRepository<Empleado, Integer> {

}
