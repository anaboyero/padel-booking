package ana.learning.padel.padelBooking.service;

import ana.learning.padel.padelBooking.DTO.BookingCalendarDTO;
import ana.learning.padel.padelBooking.exceptions.PastDateException;
import ana.learning.padel.padelBooking.mappers.BookingCalendarMapperHelper;
import ana.learning.padel.padelBooking.model.Booking;
import ana.learning.padel.padelBooking.model.BookingCalendar;
import ana.learning.padel.padelBooking.model.Player;
import ana.learning.padel.padelBooking.repository.BookingCalendarRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
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
    @Autowired
    BookingCalendarMapperHelper bookingCalendarMapperHelper;

    public BookingCalendarServiceImpl(BookingCalendarRepository bookingCalendarRepository, BookingService bookingService,BookingCalendarMapperHelper bookingCalendarMapperHelper) {
        this.bookingCalendarRepository = bookingCalendarRepository;
        this.bookingService = bookingService;
    }

    @Override
    public List<BookingCalendar> getAllBookingCalendars(){
        return (List<BookingCalendar>) bookingCalendarRepository.findAll();
    }

    //Relación: Booking tiene una FK (calendar_id) → Calendar
    // Por lo tanto, Booking depende de que Calendar ya exista en la base de datos (es decir, ya tenga un id asignado).
    // Entonces, el "orden lógico" es:
    // Persistir la entidad referenciada: Calendar
    // Asignar ese Calendar al Booking
    // Persistir Booking
    @Override
    public BookingCalendar saveBookingCalendar(BookingCalendar bookingCalendar){

        if (bookingCalendar.getId()!=null) {
            log.info("\n *** El calendario ya tiene id, simplemente lo actualizamos en la base de  datos");
            return bookingCalendarRepository.save(bookingCalendar);
        }
        log.info("\n *** El calendario no tiene id, es la primera vez que va a ser persistido");
        BookingCalendar savedCalendar = bookingCalendarRepository.save(bookingCalendar);
        for (Booking booking : savedCalendar.getAvailableBookings()) {
            booking.setCalendar(savedCalendar);
        }
        return bookingCalendarRepository.save(savedCalendar);
    }

//    @Override
//    public boolean isBookingAvailable(Booking booking, BookingCalendar bookingCalendar){
//        return bookingCalendar.isBookingAvailable(booking);
//    }
//
//
//    @Override
//    public Optional<Booking> confirmBooking(Booking booking, BookingCalendar bookingCalendar){
//        return bookingCalendar.reserveBooking(booking);
//    }

    @Override
    public Optional<BookingCalendarDTO> getBookingCalendarById(Long id){
        Optional<BookingCalendar> calendar = bookingCalendarRepository.findById(id);
        if (calendar.isEmpty()) {
            return Optional.empty();
        }
        BookingCalendarDTO dto = bookingCalendarMapperHelper.customToDTO(calendar.get());
        return Optional.of(dto);
    }

    @Override
    public BookingCalendar createBookingCalendar (LocalDate startDate) throws PastDateException {
        if (startDate.isBefore(LocalDate.now())) {
            throw new PastDateException("No se puede crear un calendario con fecha de inicio en el pasado");
        }
        BookingCalendar bookingCalendar = new BookingCalendar(startDate);
        return saveBookingCalendar(bookingCalendar);
    }

    @Transactional
    @Override
    public Optional<Booking> reserveBooking(BookingCalendar calendar, Booking booking, Player player) {
        // Caso basico: happy path .
        // añado owner al booking.
        // añado boking a reservedCalendar y lo quito de available.
        // añado booking al player.
        //  salvo booking en bbdd.
        // actualizo calendar en bbdd.
        // actualizo player
        booking.setBookingOwner(player);
        calendar.getReservedBookings().add(booking);
        calendar.getAvailableBookings().remove(booking);
        player.getBookings().add(booking);
        Booking savedBooking = bookingService.saveBooking(booking);
        return Optional.of(savedBooking);
    }


//    @Override
//    public Optional<Booking> reserveBooking(Booking booking, Player player, BookingCalendar calendar) {
//        return Optional.empty();
//    }


//    @Override
//    public Optional<Booking> reserveBooking( Booking booking, Player player, BookingCalendar calendar) {
//
//        if (!playerService.isAProperPlayerToMakeAReservation(player)) {
//            log.info("No se puede reservar porque el player no existe o no tiene una residencia completa");
//            return Optional.empty();
//        }
//        if (!isBookingAvailable(booking, calendar)) {
//            log.info("No se puede reservar porque el booking no está disponible");
//            return Optional.empty();
//        }
//        log.info("La reserva se puede llevar a cabo");
//
////        booking.setBookingOwner(player);
//        player.addBooking(booking);
//        Booking savedBooking = bookingService.saveBooking(booking);
//        playerService.savePlayer(player);
//        return calendar.reserveBooking(savedBooking);
//
//
////        Booking assignedBooking = bookingService.assignBookingToPlayer(temptativeBooking, temptativePlayer);
////        Booking assignedPlayer = playerService.assignPlayerToBooking(assignedBooking, temptativePlayer);
//
////        return this.confirmBooking(assignedBooking, bookingCalendar);
//    }

    @Override
    public void deleteAllBookingCalendars() {
        bookingCalendarRepository.deleteAll();
    }
}
