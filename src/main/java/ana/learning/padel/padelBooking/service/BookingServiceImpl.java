package ana.learning.padel.padelBooking.service;

import ana.learning.padel.padelBooking.exceptions.ResourceNotFoundException;
import ana.learning.padel.padelBooking.model.Booking;
import ana.learning.padel.padelBooking.model.BookingCalendar;
import ana.learning.padel.padelBooking.model.Player;
import ana.learning.padel.padelBooking.repository.BookingRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    BookingRepository bookingRepository;
    Logger log = LoggerFactory.getLogger(BookingServiceImpl.class);

    @Override
    public Booking saveBooking(Booking booking) {
        return bookingRepository.save(booking);
    }

    public List<Booking> getAllBookings(){
        System.out.println("\n*** Entrando en repositorio de Booking");
        return (List<Booking>)bookingRepository.findAll();
    }

    public Optional<Booking> getBookingById(Long id){
        return bookingRepository.findById(id);
    }

    @Transactional
    public void deleteBookingById(Long id) throws ResourceNotFoundException {
        ///  Deshago las relaciones desde el lado del dueño de la reserva (booking) hacia el calendario y el jugador
        ///  Y después elimino el booking.
        Booking booking = bookingRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No existe booking con id: " + id));
        booking.getCalendar().getAvailableBookings().remove(booking);
        booking.getCalendar().getReservedBookings().remove(booking);
        booking.setCalendar(null);
        if (booking.getBookingOwner() != null) {
            booking.getBookingOwner().getBookings().remove(booking);
            booking.setBookingOwner(null);
        }
        bookingRepository.deleteById(id);
        log.info("Booking con id " + id + " eliminado correctamente");
        System.out.println("\n ******** ");
        System.out.println("Vamos a ver si lo ha eliminado de verdad o qué");
        System.out.println(bookingRepository.findById(id));

    }

    @Override
    @Transactional
    public Optional<Booking> cancelBooking(Long id) throws ResourceNotFoundException {
        // Si existe el booking y tiene dueño, deshago las relaciones y lo dejo como disponible
        // Si no, devuelvo Optional.empty()
        Optional<Booking> booking = bookingRepository.findById(id);

        if (booking.isPresent() && booking.get().getBookingOwner() != null) {
            booking.get().setCalendar(booking.get().getCalendar());
            booking.get().getBookingOwner().getBookings().remove(booking.get());
            booking.get().setBookingOwner(null);
            booking.get().getCalendar().getAvailableBookings().add(booking.get());
            booking.get().getCalendar().getReservedBookings().remove(booking.get());
            Booking saved = bookingRepository.save(booking.get());
            return Optional.of(saved);
        }
        return Optional.empty();
    }



}
