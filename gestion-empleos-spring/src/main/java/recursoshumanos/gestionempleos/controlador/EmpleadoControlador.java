package recursoshumanos.gestionempleos.controlador;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import recursoshumanos.gestionempleos.excepcion.RecursoNoEncontradoExcepcion;
import recursoshumanos.gestionempleos.modelo.Empleado;
import recursoshumanos.gestionempleos.servicio.EmpleadoServicio;
import recursoshumanos.gestionempleos.servicio.IEmpleadoServicio;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
//http://localhost:8080/recursoshumanos-app/
@RequestMapping("recursoshumanos-app")
@CrossOrigin(value = "http://localhost:3000")
public class EmpleadoControlador {

    private static final Logger logger =
            LoggerFactory.getLogger(EmpleadoControlador.class);

    @Autowired
    private IEmpleadoServicio empleadoServicio;
    //http://localhost:8080/recursoshumanos-app/empleados
    @GetMapping("/empleados")
    public List<Empleado> obtenerEmpleados() {
        var empleados = empleadoServicio.listarEmpleados();
        empleados.forEach((empleado -> logger.info(empleado.toString())));
        return empleados;
    }

    @PostMapping("/empleados")
    public Empleado agregarEmpleado(@RequestBody Empleado empleado) {
        logger.info("Empleado agregado: " + empleado);
        return empleadoServicio.guardarEmpleado(empleado);
    }

    @GetMapping("/empleados/{id}")
    public ResponseEntity<Empleado> obtenerEmpleadoPorID(@PathVariable Integer id) {
        Empleado empleado = empleadoServicio.buscarEmpleadoPorId(id);
        if (empleado == null)
            throw new RecursoNoEncontradoExcepcion("No se encontro el empleado" + id);
        return ResponseEntity.ok(empleado);
    }

    @PutMapping("/empleados/{id}")
    public ResponseEntity<Empleado>
        actualizarEmpleado(@PathVariable Integer id, @RequestBody Empleado empleadoRecibido) {
        Empleado empleado = empleadoServicio.buscarEmpleadoPorId(id);
        if (empleado == null)
            throw new RecursoNoEncontradoExcepcion("No se encontro el empleado" + id);
        empleado.setNombre(empleadoRecibido.getNombre());
        empleado.setDepartamento(empleadoRecibido.getDepartamento());
        empleado.setSueldo(empleadoRecibido.getSueldo());
        empleadoServicio.guardarEmpleado(empleado);
        return ResponseEntity.ok(empleado);
    }

    @DeleteMapping("/empleados/{id}")
    public ResponseEntity<Map<String, Boolean>>
    eliminarEmpleado(@PathVariable Integer id) {
        Empleado empleado = empleadoServicio.buscarEmpleadoPorId(id);
        if(empleado == null)
            throw new RecursoNoEncontradoExcepcion("No se encontro el empleado" + id);
        empleadoServicio.borrarEmpleado(empleado);
        Map<String, Boolean> respuesta = new HashMap<>();
        respuesta.put("eliminado", true);
        return ResponseEntity.ok(respuesta);
    }
}
