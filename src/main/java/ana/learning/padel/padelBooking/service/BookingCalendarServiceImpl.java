package ana.learning.padel.padelBooking.service;

import ana.learning.padel.padelBooking.DTO.BookingCalendarDTO;
import ana.learning.padel.padelBooking.DTO.PlayerDTO;
import ana.learning.padel.padelBooking.exceptions.PastDateException;
import ana.learning.padel.padelBooking.mappers.BookingCalendarMapperHelper;
import ana.learning.padel.padelBooking.model.Booking;
import ana.learning.padel.padelBooking.model.BookingCalendar;
import ana.learning.padel.padelBooking.model.Player;
import ana.learning.padel.padelBooking.repository.BookingCalendarRepository;
import ana.learning.padel.padelBooking.repository.BookingRepository;
import ana.learning.padel.padelBooking.repository.PlayerRepository;
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
    BookingRepository bookingRepository;
    @Autowired
    PlayerRepository playerRepository;
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

//    @Override
//    public BookingCalendar createBookingCalendar(LocalDate startDay) {
//
//
//    }

    @Override
    public BookingCalendar saveBookingCalendar(BookingCalendar bookingCalendar){
        log.info("\n **** ENTRO EN SAVE BOOKING CALENDAR");

        if (bookingCalendar.getId()!=null) {
            log.info("\n *** El calendario ya tiene id, simplemente lo actualizamos en la base de  datos");
            return bookingCalendarRepository.save(bookingCalendar);
        }
        log.info("\n *** El calendario no tiene id, es la primera vez que va a ser persistido");
        BookingCalendar savedCalendar = bookingCalendarRepository.save(bookingCalendar);
        for (Booking booking : savedCalendar.getAvailableBookings()) {
            booking.setCalendar(savedCalendar);
            bookingRepository.save(booking);
        }
        log.info("\n\n\n **** VEAMOS SI LAS BOOKING DEL CALENDAR YA ENLAZAN AL CALENDAR\n");
        log.info("\n **** ID CALENDAR DE LA PRIMERA BOOKING AVAILABLE");
        log.info(savedCalendar.getAvailableBookings().get(0).getCalendar().getId().toString());

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
        BookingCalendar bookingCalendar = new BookingCalendar();
        bookingCalendar.setStartDay(startDate);
        BookingCalendar savedCalendar = bookingCalendarRepository.save(bookingCalendar);

        List<Booking> availableBookings = new ArrayList<>();

        for (int i = 0; i<7; i++) {
            LocalDate day = startDate.plusDays(i);
            for (Booking.TimeSlot timeSlot : Booking.TimeSlot.values()) {
                Booking booking = new Booking();
                booking.setBookingDate(day);
                booking.setTimeSlot(timeSlot);
                booking.setBookingOwner(null);
                booking.setCalendar(savedCalendar);
                Booking savedBooking = bookingRepository.save(booking); // Persist each booking immediately
                savedCalendar.getAvailableBookings().add(savedBooking);
            }
        }
        return bookingCalendarRepository.save(savedCalendar);
    }


    @Transactional
    @Override
    public Optional<Booking> reserveBooking(Long bookingId, PlayerDTO playerDTO) {

        // Recuperamos las entidades desde los repositorios dentro de la misma transacción
        Optional<Booking> bookingOpt = bookingRepository.findById(bookingId);
        Optional<Player> playerOpt = playerRepository.findById(playerDTO.getId());

        if (bookingOpt.isEmpty() || playerOpt.isEmpty()) {
            log.info("No existe el booking o el player");
            return Optional.empty();
        }

        Booking booking = bookingOpt.get();
        Player player = playerOpt.get();
        BookingCalendar calendar = booking.getCalendar();

        // Comprobamos que la reserva esté disponible
        if (booking.getBookingOwner() != null || calendar == null) {
            log.info("La reserva ya tiene dueño o no está asociada a ningún calendario");
            return Optional.empty();
        }

        // --- Actualizamos relaciones bidireccionales ---
        booking.setBookingOwner(player);
        player.getBookings().add(booking);

        booking.setCalendar(calendar);
        calendar.getReservedBookings().add(booking);
        calendar.getAvailableBookings().remove(booking);

        // --- Forzamos carga de las colecciones lazy iterando sobre ellas ---
        calendar.getReservedBookings().size();
        calendar.getAvailableBookings().size();
        player.getBookings().size();

        // --- Guardamos solo el lado dueño de las relaciones ---
        Booking savedBooking = bookingRepository.save(booking);
        return Optional.of(savedBooking);
    }


    @Override
    public void deleteAllBookingCalendars() {
        bookingCalendarRepository.deleteAll();
    }
}
