package biblioteca.salas.duoc.biblioteca.salas.duoc.controller;

import biblioteca.salas.duoc.biblioteca.salas.duoc.assemblers.ReservaModelAssembler;
import biblioteca.salas.duoc.biblioteca.salas.duoc.model.Reserva;
import biblioteca.salas.duoc.biblioteca.salas.duoc.service.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

// Define esta clase como un controlador REST y establece la ruta base para los endpoints.
@RestController
@RequestMapping("/api/v2/reservas")
public class ReservaControllerV2 {

    // Inyecta el servicio de Reserva.
    @Autowired
    private ReservaService reservaService;

    // Inyecta el ensamblador de modelo de Reserva para HATEOAS.
    @Autowired
    private ReservaModelAssembler assembler;

    // Define un endpoint GET que produce respuestas en formato HAL-JSON y devuelve todas las reservas.
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Reserva>> getAllReservas() {
        // Obtiene todas las reservas del servicio y las convierte en EntityModel usando el ensamblador.
        List<EntityModel<Reserva>> reservas = reservaService.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        // Devuelve la colección de modelos de entidades, incluyendo un enlace a sí misma.
        return CollectionModel.of(reservas,
                linkTo(methodOn(ReservaControllerV2.class).getAllReservas()).withSelfRel());
    }

    // Define un endpoint GET que produce respuestas en formato HAL-JSON y devuelve una reserva específica por ID.
    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<Reserva> getReservaById(@PathVariable Integer id) {
        // Busca la reserva por ID usando el servicio.
        Reserva reserva = reservaService.findById(id);
        // Convierte la reserva en EntityModel usando el ensamblador y la devuelve.
        return assembler.toModel(reserva);
    }

    // Define un endpoint POST que produce respuestas en formato HAL-JSON y crea una nueva reserva.
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Reserva>> createReserva(@RequestBody Reserva reserva) {
        // Guarda la nueva reserva usando el servicio.
        Reserva newReserva = reservaService.save(reserva);
        // Devuelve una respuesta 201 Created con la URL de la nueva reserva en el encabezado Location.
        return ResponseEntity
                .created(linkTo(methodOn(ReservaControllerV2.class).getReservaById(newReserva.getId())).toUri())
                .body(assembler.toModel(newReserva));
    }

    // Define un endpoint PUT que produce respuestas en formato HAL-JSON y actualiza una reserva existente por ID.
    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Reserva>> updateReserva(@PathVariable Integer id, @RequestBody Reserva reserva) {
        // Establece el ID de la reserva a actualizar.
        reserva.setId(id);
        // Actualiza la reserva usando el servicio.
        Reserva updatedReserva = reservaService.save(reserva);
        // Devuelve una respuesta 200 OK con la reserva actualizada.
        return ResponseEntity.ok(assembler.toModel(updatedReserva));
    }

    // Define un endpoint DELETE que produce respuestas en formato HAL-JSON y elimina una reserva por ID.
    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<?> deleteReserva(@PathVariable Integer id) {
        // Elimina la reserva usando el servicio.
        reservaService.deleteById(id);
        // Devuelve una respuesta 204 No Content indicando que la eliminación fue exitosa.
        return ResponseEntity.noContent().build();
    }
}
