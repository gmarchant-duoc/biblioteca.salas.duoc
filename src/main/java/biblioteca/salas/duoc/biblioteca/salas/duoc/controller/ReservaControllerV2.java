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

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v2/reservas")
public class ReservaControllerV2 {

    @Autowired
    private ReservaService reservaService;

    @Autowired
    private ReservaModelAssembler assembler;

    // Método personalizado para obtener reservas por estudiante
    @GetMapping(value = "/estudiante/{idEstudiante}", produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Reserva>> getReservasByEstudiante(@PathVariable Integer idEstudiante) {
        List<EntityModel<Reserva>> reservas = reservaService.findByEstudianteId(idEstudiante).stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(reservas,
                linkTo(methodOn(ReservaControllerV2.class).getReservasByEstudiante(idEstudiante)).withSelfRel());
    }

    // Método personalizado para obtener reservas por sala
    @GetMapping(value = "/sala/{codigoSala}", produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Reserva>> getReservasBySala(@PathVariable Integer codigoSala) {
        List<EntityModel<Reserva>> reservas = reservaService.findBySalaCodigo(codigoSala).stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(reservas,
                linkTo(methodOn(ReservaControllerV2.class).getReservasBySala(codigoSala)).withSelfRel());
    }

    // Método personalizado para obtener reservas por fecha
    @GetMapping(value = "/fecha/{fecha}", produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Reserva>> getReservasByFecha(@PathVariable Date fecha) {
        List<EntityModel<Reserva>> reservas = reservaService.findByFechaSolicitada(fecha).stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(reservas,
                linkTo(methodOn(ReservaControllerV2.class).getReservasByFecha(fecha)).withSelfRel());
    }

    // Método personalizado para obtener reservas por estado
    @GetMapping(value = "/estado/{estado}", produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Reserva>> getReservasByEstado(@PathVariable Integer estado) {
        List<EntityModel<Reserva>> reservas = reservaService.findByEstado(estado).stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(reservas,
                linkTo(methodOn(ReservaControllerV2.class).getReservasByEstado(estado)).withSelfRel());
    }

    // Método personalizado para obtener reservas entre dos fechas
    @GetMapping(value = "/fecha/{start}/{end}", produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Reserva>> getReservasBetweenFechas(@PathVariable Date start, @PathVariable Date end) {
        List<EntityModel<Reserva>> reservas = reservaService.findByFechaSolicitadaBetween(start, end).stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(reservas,
                linkTo(methodOn(ReservaControllerV2.class).getReservasBetweenFechas(start, end)).withSelfRel());
    }

    // Método personalizado para obtener el total de reservas de un estudiante
    @GetMapping(value = "/estudiante/{idEstudiante}/total", produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<Long> getTotalReservasByEstudiante(@PathVariable Integer idEstudiante) {
        long totalReservas = reservaService.countByEstudianteId(idEstudiante);
        return EntityModel.of(totalReservas,
                linkTo(methodOn(ReservaControllerV2.class).getTotalReservasByEstudiante(idEstudiante)).withSelfRel());
    }

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Reserva>> getAllReservas() {
        List<EntityModel<Reserva>> reservas = reservaService.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(reservas,
                linkTo(methodOn(ReservaControllerV2.class).getAllReservas()).withSelfRel());
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<Reserva> getReservaById(@PathVariable Integer id) {
        Reserva reserva = reservaService.findById(id);
        return assembler.toModel(reserva);
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Reserva>> createReserva(@RequestBody Reserva reserva) {
        Reserva newReserva = reservaService.save(reserva);
        return ResponseEntity
                .created(linkTo(methodOn(ReservaControllerV2.class).getReservaById(newReserva.getId())).toUri())
                .body(assembler.toModel(newReserva));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Reserva>> updateReserva(@PathVariable Integer id, @RequestBody Reserva reserva) {
        reserva.setId(id);
        Reserva updatedReserva = reservaService.save(reserva);
        return ResponseEntity.ok(assembler.toModel(updatedReserva));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<?> deleteReserva(@PathVariable Integer id) {
        reservaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
