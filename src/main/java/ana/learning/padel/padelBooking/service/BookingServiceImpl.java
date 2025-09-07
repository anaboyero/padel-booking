package ana.learning.padel.padelBooking.service;

import ana.learning.padel.padelBooking.model.Booking;
import ana.learning.padel.padelBooking.model.Player;
import ana.learning.padel.padelBooking.repository.BookingRepository;
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
        System.out.println((List<Booking>)bookingRepository.findAll());
        return (List<Booking>)bookingRepository.findAll();
    }

    public Optional<Booking> getBookingById(Long id){
        return bookingRepository.findById(id);
    }

    @Override
    public Booking assignBookingToPlayer(Booking booking, Player player) {
        log.info("FALTA: Avisar de que la reserva deje de estar available");
        log.info("ANUNCIO: booking reservada para jugador. Falta confirmar en calendario");
        booking.setBookingOwner(player);
        log.info("ANUNCIO: Reserva hecha");
        return saveBooking(booking);
    }

}
