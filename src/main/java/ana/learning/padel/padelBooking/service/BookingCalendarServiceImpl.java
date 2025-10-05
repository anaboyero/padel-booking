package ana.learning.padel.padelBooking.service;

import ana.learning.padel.padelBooking.exceptions.PastDateException;
import ana.learning.padel.padelBooking.model.Booking;
import ana.learning.padel.padelBooking.model.BookingCalendar;
import ana.learning.padel.padelBooking.model.Player;
import ana.learning.padel.padelBooking.repository.BookingCalendarRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    public BookingCalendarServiceImpl(BookingCalendarRepository bookingCalendarRepository, BookingService bookingService) {
        this.bookingCalendarRepository = bookingCalendarRepository;
        this.bookingService = bookingService;
    }



//    @Override
//    public BookingCalendar createBookingCalendar(LocalDate startDay) {
//        if (startDay.isBefore(LocalDate.now())) {
//            throw new PastDateException("No se puede crear un calendario con fecha en el pasado");
//        }
//        BookingCalendar calendar = new BookingCalendar();
//        calendar.setStartDay(startDay);
//        BookingCalendar savedCalendar = bookingCalendarRepository.save(calendar);
//        List<Booking> availableBookings  = new ArrayList<>();
//        for (int i = 0; i<7; i++) {
//            LocalDate day = startDay.plusDays(i);
//            for (Booking.TimeSlot timeSlot : Booking.TimeSlot.values()) {
//                Booking booking = new Booking();
//                booking.setBookingDate(day);
//                booking.setTimeSlot(timeSlot);
//                booking.setBookingOwner(null);
////                booking.setCalendar(calendar);
//                Booking savedBooking = bookingService.saveBooking(booking);
//                log.info(savedBooking.toString());
//                availableBookings.add(savedBooking);
//            }
//        }
//        savedCalendar.setAvailableBookings(availableBookings);
//        savedCalendar = bookingCalendarRepository.save(savedCalendar);
//        log.info("\n Available bookings creadas y persistidas: ");
//        log.info(availableBookings.toString());
//        return savedCalendar;
//    }

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
        log.info("\n**** Entramos en el saveBookingCalendar del service con este calendario:" + bookingCalendar.toString());

        if (bookingCalendar.getId()!=null) {
            log.info("\n *** El calendario ya tiene id, simplemente lo actualizamos en la base de  datos");
            return bookingCalendarRepository.save(bookingCalendar);
        }
        // Si es la primera vez que se persiste el calendario, el id es null y las bookings no están persistidas.
        log.info("\n *** El calendario no tiene id, es la primera vez que va a ser persistido");

        BookingCalendar savedCalendar = bookingCalendarRepository.save(bookingCalendar);
        if (savedCalendar == null) {
            log.error("ERROR: savedCalendar es null después del save");
        } else {
            log.info("savedCalendar guardado con ID: " + savedCalendar.getId());
        }
        List<Booking> persistedBookings = new ArrayList<>();
        for (Booking booking : savedCalendar.getAvailableBookings()) {
            booking.setCalendar(savedCalendar);
            persistedBookings.add(booking);
        }
        savedCalendar.setAvailableBookings(persistedBookings);
        savedCalendar = bookingCalendarRepository.save(savedCalendar);
        return savedCalendar;
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
    public Optional<BookingCalendar> getBookingCalendarById(Long id){
        return bookingCalendarRepository.findById(id);
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
