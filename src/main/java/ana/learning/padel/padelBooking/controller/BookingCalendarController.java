package ana.learning.padel.padelBooking.controller;

import ana.learning.padel.padelBooking.DTO.BookingDTO;
import ana.learning.padel.padelBooking.DTO.CreateCalendarRequest;
import ana.learning.padel.padelBooking.mappers.BookingMapper;
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
    private final BookingMapper bookingMapper;
//    final BookingCalendarRepository bookingCalendarRepository;
    private final Logger log = LoggerFactory.getLogger(BookingCalendarController.class);

    public BookingCalendarController(BookingCalendarService bookingCalendarService, BookingService bookingService, PlayerService playerService, BookingMapper bookingMapper) {
        this.bookingCalendarService = bookingCalendarService;
//        this.bookingCalendarRepository = bookingCalendarRepository;
        this.bookingService = bookingService;
        this.playerService = playerService;
        this.bookingMapper = bookingMapper;
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
    public BookingDTO reserveBooking(@PathVariable Long calendarId, @PathVariable Long bookingId, @RequestBody Player player){
        log.info("\n  *** \n ENTRAMOS EN EL NUEVO POST DE RESERVAR UN BOOKING CONCRETO EN CALENDAR CONCRETO\n\n");

        log.info("\n  *** Escenario: todo available y todo correcto");

        Booking availableBooking = bookingService.getBookingById(bookingId).get();
        BookingCalendar calendar = bookingCalendarService.getBookingCalendarById(calendarId).get();
        Player savedPlayer = playerService.getPlayerById(player.getId()).get();

        log.info("\n  *** Hago la reserva tentativa con el jugador");
        Booking tentativeBookingWithPlayer = bookingService.assignBookingToPlayer(availableBooking, savedPlayer);
        log.info("\n  *** Y después actualizo el calendario");
        Booking reservedBooking = bookingCalendarService.reserveBooking(tentativeBookingWithPlayer, calendar).get();
        return bookingMapper.toDTO(reservedBooking);
    }



}
