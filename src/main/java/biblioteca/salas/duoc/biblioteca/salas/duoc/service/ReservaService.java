package biblioteca.salas.duoc.biblioteca.salas.duoc.service;

import biblioteca.salas.duoc.biblioteca.salas.duoc.model.Reserva;
import biblioteca.salas.duoc.biblioteca.salas.duoc.repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Date;

@Service
public class ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

    public List<Reserva> findAll() {
        return reservaRepository.findAll();
    }

    public Reserva findById(Integer id) {
        return reservaRepository.findById(id).orElseThrow(() -> new RuntimeException("Reserva no encontrada"));
    }

    public Reserva save(Reserva reserva) {
        return reservaRepository.save(reserva);
    }

    public void deleteById(Integer id) {
        reservaRepository.deleteById(id);
    }

    public List<Reserva> findByEstudianteId(Integer idEstudiante) {
        return reservaRepository.findByEstudianteId(idEstudiante);
    }

    public List<Reserva> findBySalaCodigo(Integer codigoSala) {
        return reservaRepository.findBySalaCodigo(codigoSala);
    }

    public List<Reserva> findByFechaSolicitada(Date fecha) {
        return reservaRepository.findByFechaSolicitada(fecha);
    }

    public List<Reserva> findByEstado(Integer estado) {
        return reservaRepository.findByEstado(estado);
    }

    public List<Reserva> findByFechaSolicitadaBetween(Date start, Date end) {
        return reservaRepository.findByFechaSolicitadaBetween(start, end);
    }

    public long countByEstudianteId(Integer idEstudiante) {
        return reservaRepository.countByEstudianteId(idEstudiante);
    }
}
