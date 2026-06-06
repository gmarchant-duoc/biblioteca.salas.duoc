package biblioteca.salas.duoc.biblioteca.salas.duoc.repository;

import biblioteca.salas.duoc.biblioteca.salas.duoc.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface ReservaRepository extends JpaRepository<Reserva, Integer> {

    List<Reserva> findByEstudianteId(Integer idEstudiante);

    List<Reserva> findBySalaCodigo(Integer codigoSala);

    List<Reserva> findByFechaSolicitada(Date fecha);

    List<Reserva> findByEstado(Integer estado);

    List<Reserva> findByFechaSolicitadaBetween(Date start, Date end);

    long countByEstudianteId(Integer idEstudiante);
}