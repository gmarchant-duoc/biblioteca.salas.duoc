package biblioteca.salas.duoc.biblioteca.salas.duoc.assemblers;

import biblioteca.salas.duoc.biblioteca.salas.duoc.controller.ReservaControllerV2;
import biblioteca.salas.duoc.biblioteca.salas.duoc.model.Reserva;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class ReservaModelAssembler implements RepresentationModelAssembler<Reserva, EntityModel<Reserva>> {

    @Override
    public EntityModel<Reserva> toModel(Reserva reserva) {
        return EntityModel.of(reserva,
                linkTo(methodOn(ReservaControllerV2.class).getReservaById(reserva.getId())).withSelfRel(),
                linkTo(methodOn(ReservaControllerV2.class).getAllReservas()).withRel("reservas"));
    }
}