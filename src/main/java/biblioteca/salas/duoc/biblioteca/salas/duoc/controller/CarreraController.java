package biblioteca.salas.duoc.biblioteca.salas.duoc.controller;

import biblioteca.salas.duoc.biblioteca.salas.duoc.model.Carrera;
import biblioteca.salas.duoc.biblioteca.salas.duoc.service.CarreraService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/carreras")
@Tag(name = "Carreras", description = "Operaciones relacionadas con las carreras")
public class CarreraController {

    @Autowired
    private CarreraService carreraService;

    @GetMapping
    @Operation(summary = "Obtener todas las carreras", description = "Obtiene una lista de todas las carreras")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Carrera.class)))
    })
    public List<Carrera> getAllCarreras() {
        return carreraService.findAll();
    }

    @GetMapping("/{codigo}")
    @Operation(summary = "Obtener una carrera por código", description = "Obtiene una carrera por su código")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Carrera.class))),
            @ApiResponse(responseCode = "404", description = "Carrera no encontrada")
    })
    public Carrera getCarreraByCodigo(@PathVariable String codigo) {
        return carreraService.findByCodigo(codigo);
    }

    @PostMapping
    @Operation(summary = "Crear una nueva carrera", description = "Crea una nueva carrera")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Carrera creada exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Carrera.class)))
    })
    public Carrera createCarrera(@RequestBody Carrera carrera) {
        return carreraService.save(carrera);
    }

    @PutMapping("/{codigo}")
    @Operation(summary = "Actualizar una carrera", description = "Actualiza una carrera existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Carrera actualizada exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Carrera.class))),
            @ApiResponse(responseCode = "404", description = "Carrera no encontrada")
    })
    public Carrera updateCarrera(@PathVariable String codigo, @RequestBody Carrera carrera) {
        carrera.setCodigo(codigo);
        return carreraService.save(carrera);
    }

    @DeleteMapping("/{codigo}")
    @Operation(summary = "Eliminar una carrera", description = "Elimina una carrera por su código")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Carrera eliminada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Carrera no encontrada")
    })
    public void deleteCarrera(@PathVariable String codigo) {
        carreraService.deleteByCodigo(codigo);
    }
}
