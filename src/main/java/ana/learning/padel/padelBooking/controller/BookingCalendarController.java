package ana.learning.padel.padelBooking.controller;

import ana.learning.padel.padelBooking.DTO.CreateCalendarRequest;
import ana.learning.padel.padelBooking.model.Booking;
import ana.learning.padel.padelBooking.model.BookingCalendar;
import ana.learning.padel.padelBooking.model.Player;
import ana.learning.padel.padelBooking.service.BookingCalendarService;
import ana.learning.padel.padelBooking.service.BookingService;
import ana.learning.padel.padelBooking.service.PlayerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/booking-calendars")
public class BookingCalendarController {

    private static final LocalDate TODAY = LocalDate.now();
    final BookingCalendarService bookingCalendarService;
    private final BookingService bookingService;
    private final PlayerService playerService;
//    final BookingCalendarRepository bookingCalendarRepository;
    private final Logger log = LoggerFactory.getLogger(BookingCalendarController.class);

    public BookingCalendarController(BookingCalendarService bookingCalendarService, BookingService bookingService, PlayerService playerService) {
        this.bookingCalendarService = bookingCalendarService;
//        this.bookingCalendarRepository = bookingCalendarRepository;
        this.bookingService = bookingService;
        this.playerService = playerService;
    }

    @GetMapping
    public List<BookingCalendar> getBookingCalendars() {
        return bookingCalendarService.getAllBookingCalendars();
        //return new ArrayList<>();
    }


    @PostMapping
    public ResponseEntity<BookingCalendar> createCalendar(@RequestBody CreateCalendarRequest createCalendarRequest) {
        log.info("\n*** ENTRA EN EL CONTROLADOR (POST). Entra en createCalendar");

        BookingCalendar bookingCalendar = new BookingCalendar();
        bookingCalendar.setStartDay(createCalendarRequest.getStartDay());

        log.info("\n*** Antes de persistir: " + bookingCalendar);

        BookingCalendar savedBookingCalendar = bookingCalendarService.saveBookingCalendar(bookingCalendar);

        log.info("\n*** Después de persistir: " + bookingCalendar);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedBookingCalendar.getId())
                .toUri();

        log.info("\n*** Este es el URI de location: " + location);

        log.info("\n*** Este es el calendariosalvado del body: " + savedBookingCalendar);


        return ResponseEntity.created(location).body(savedBookingCalendar);
    }


    @PostMapping("/{calendarId}/bookings/{bookingId}")
    public Booking reserveBooking(@PathVariable Long calendarId, @PathVariable Long bookingId, @RequestBody Player player){
        // primera iteracion: asumo booking esta disponible
<<<<<<< HEAD
        log.info("\n  *** ESTOS SON LOS DATOS CON LOS QUE SE HA LLAMADO AL CONTROLADOR");
        log.info(calendarId.toString());
        log.info(player.toString());
        log.info(bookingId.toString());
        Booking booking = bookingService.getBookingById(bookingId).get();
        BookingCalendar calendar = bookingCalendarService.getBookingCalendarById(calendarId).get();
        Booking savedBooking = bookingService.reserveBooking(booking, player);
        return bookingCalendarService.reserveBooking(savedBooking, calendar).get();
=======
        log.info("\n  *** Esto necesitará validaciones y manejo de errores, pero de momento asumo que todo es correcto");
        Booking availableBooking = bookingService.getBookingById(bookingId).get();
        BookingCalendar calendar = bookingCalendarService.getBookingCalendarById(calendarId).get();
        Player savedPlayer = playerService.getPlayerById(player.getId()).get();
>>>>>>> 6cd1a169dd17c1dbfc28498d56b9b75c428e583c

        log.info("\n  *** ESTOS SON LOS DATOS CON LOS QUE TRABAJO");
        log.info("\n  *** calendarId: " + calendarId.toString());
        log.info("\n  *** savedPlayer " + savedPlayer.toString());
        log.info("\n  *** bookingId " + bookingId.toString());

        log.info("\n  *** Asumiendo que player y reserva son válidos, hago la reserva tentativa con el jugador");
        Booking tentativeBookingWithPlayer = bookingService.assignBookingToPlayer(availableBooking, savedPlayer);
        log.info("\n  *** y después intento actualizar el calendario");
        Booking savedBookingInCalendar = bookingCalendarService.reserveBooking(tentativeBookingWithPlayer, calendar).get();

        return savedBookingInCalendar;
    }

<<<<<<< HEAD
    }
=======
// EL TEST ESTA EN GREEN. TOCA REFACTOR.
>>>>>>> 6cd1a169dd17c1dbfc28498d56b9b75c428e583c

}
