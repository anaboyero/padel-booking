package ana.learning.padel.padelBooking.service;

import ana.learning.padel.padelBooking.model.Booking;
import ana.learning.padel.padelBooking.model.BookingCalendar;
import ana.learning.padel.padelBooking.model.Player;
import ana.learning.padel.padelBooking.repository.BookingCalendarRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookingCalendarServiceImpl implements BookingCalendarService{
    @Autowired
    BookingCalendarRepository bookingCalendarRepository;
    @Autowired
    BookingService bookingService;
    @Autowired
    PlayerService playerService;
    private static final Logger log = LoggerFactory.getLogger(BookingCalendarServiceImpl.class);

    public BookingCalendarServiceImpl(BookingCalendarRepository bookingCalendarRepository, BookingService bookingService) {
        this.bookingCalendarRepository = bookingCalendarRepository;
        this.bookingService = bookingService;
    }

    private void persistAvailableBookings(BookingCalendar bookingCalendar){
        log.info("\n*** Entro en el bucle for para persistir los bookings");
        for (Booking booking : bookingCalendar.getAvailableBookings()) {
            bookingService.saveBooking(booking);
        }
    }

    @Override
    public List<BookingCalendar> getAllBookingCalendars(){
        return (List<BookingCalendar>) bookingCalendarRepository.findAll();
    }

    @Override
    public BookingCalendar saveBookingCalendar(BookingCalendar bookingCalendar){
        if (bookingCalendar.getId() == null) {
            //La primera vez que se persiste el calendario, se persisten antes los bookings disponibles (o sea, todos).
            persistAvailableBookings(bookingCalendar);
        }
        // Persisto el calendario.
        return bookingCalendarRepository.save(bookingCalendar);
    }

    @Override
    public boolean isBookingAvailable(Booking booking, BookingCalendar bookingCalendar){
        return bookingCalendar.isBookingAvailable(booking);
    }


    @Override
    public Optional<Booking> confirmBooking(Booking booking, BookingCalendar bookingCalendar){
        return bookingCalendar.reserveBooking(booking);
    }

    @Override
    public Optional<BookingCalendar> getBookingCalendarById(Long id){
        return bookingCalendarRepository.findById(id);
    }

    @Override
    public Optional<Booking> reserveBooking(Booking temptativeBooking, Player temptativePlayer, BookingCalendar bookingCalendar) {
        if (!playerService.isAProperPlayerToMakeAReservation(temptativePlayer)) {
            log.info("No se puede reservar porque el player no existe o no tiene una residencia completa");
            return Optional.empty();
        }
        if (!isBookingAvailable(temptativeBooking, bookingCalendar)) {
            log.info("No se puede reservar porque el booking no está disponible");
            return Optional.empty();
        }
        log.info("La reserva se puede llevar a cabo");

        temptativeBooking.setBookingOwner(temptativePlayer);
        temptativePlayer.addBooking(temptativeBooking);
        Booking savedBooking = bookingService.saveBooking(temptativeBooking);
        playerService.savePlayer(temptativePlayer);
        return bookingCalendar.reserveBooking(savedBooking);


//        Booking assignedBooking = bookingService.assignBookingToPlayer(temptativeBooking, temptativePlayer);
//        Booking assignedPlayer = playerService.assignPlayerToBooking(assignedBooking, temptativePlayer);

//        return this.confirmBooking(assignedBooking, bookingCalendar);
    }

    @Override
    public void deleteAllBookingCalendars() {
        bookingCalendarRepository.deleteAll();
    }
}
