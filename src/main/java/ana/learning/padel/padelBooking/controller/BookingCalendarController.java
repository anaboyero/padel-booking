package ana.learning.padel.padelBooking.controller;

import ana.learning.padel.padelBooking.DTO.CreateCalendarRequest;
import ana.learning.padel.padelBooking.model.Booking;
import ana.learning.padel.padelBooking.model.BookingCalendar;
import ana.learning.padel.padelBooking.model.Player;
import ana.learning.padel.padelBooking.repository.BookingCalendarRepository;
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
import java.util.ArrayList;
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


//    @PostMapping("/{calendarId}/bookings/{bookingId}")
//    public Booking reserveBooking(@PathVariable Long calendarId, @PathVariable Long bookingId, @RequestBody Player player){
//        // primera iteracion: asumo booking esta disponible
//        log.info("\n  *** ESTOS SON LOS DATOS CON LOS QUE SE HA LLAMADO AL CONTROLADOR");
//        log.info(calendarId.toString());
//        log.info(player.toString());
//        log.info(bookingId.toString());
//        Booking booking = bookingService.getBookingById(bookingId).get();
//        BookingCalendar calendar = bookingCalendarService.getBookingCalendarById(calendarId).get();
//        Player savedPlayer = playerService.getPlayerById(player.getId()).get();
//        Booking savedBooking = bookingService.reserveBooking(booking, savedPlayer);
//        return bookingCalendarService.reserveBooking(savedBooking, calendar).get();
//    }

    // El problema es un bucle infinito de referencias circulares.
    // Se conoce como: // Stack overflow o recursión infinita en serialización JSON
    // Estoy devolviendo un player con mucha informacion, cuando seria suficiente devolver solo su id y nombre.

    // Por otra parte, estoy devolviendo una entidad JAVA en lugar de un DTO, que es lo que deberia devolver para no saturar.

}
