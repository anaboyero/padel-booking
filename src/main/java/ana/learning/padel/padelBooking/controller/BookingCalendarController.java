package ana.learning.padel.padelBooking.controller;

import ana.learning.padel.padelBooking.DTO.CreateCalendarRequest;
import ana.learning.padel.padelBooking.model.Booking;
import ana.learning.padel.padelBooking.model.BookingCalendar;
import ana.learning.padel.padelBooking.model.Player;
import ana.learning.padel.padelBooking.repository.BookingCalendarRepository;
import ana.learning.padel.padelBooking.service.BookingCalendarService;
import ana.learning.padel.padelBooking.service.BookingService;
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
//    final BookingCalendarRepository bookingCalendarRepository;
    private final Logger log = LoggerFactory.getLogger(BookingCalendarController.class);

    public BookingCalendarController(BookingCalendarService bookingCalendarService, BookingService bookingService) {
        this.bookingCalendarService = bookingCalendarService;
//        this.bookingCalendarRepository = bookingCalendarRepository;
        this.bookingService = bookingService;
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
        log.info("\n  *** ESTOS SON LOS DATOS CON LOS QUE SE HA LLAMADO AL CONTROLADOR");
        log.info(calendarId.toString());
        log.info(player.toString());
        log.info(bookingId.toString());
        Booking booking = bookingService.getBookingById(bookingId).get();
        BookingCalendar calendar = bookingCalendarService.getBookingCalendarById(calendarId).get();
        Booking savedBooking = bookingService.reserveBooking(booking, player);
        return bookingCalendarService.reserveBooking(savedBooking, calendar).get();


        ///  EL PROBLEMA AQUI ESTA SIENDO QUE EL PLAYER CON EL QUE SE LLAMA AL CONTROLADOR TIENE RESIDENCIA NULL
        ///  Y NO DEBERIA SER ASI PORQUE SE LE ASIGNO ANTES UNA.
        ///  CREO QUE SE ESTA PERDIENDO AL USAR EL PLAYER MAPPER

        /// POR TERMINAL, AL HACER EL TEST
        /// *** ESTOS SON LOS DATOS CON LOS QUE SE HA LLAMADO AL CONTROLADOR
        /// 2025-08-31T20:44:50.416+02:00  INFO 22037 --- [padelBooking] [main] a.l.p.p.c.BookingCalendarController      : 118
        /// 2025-08-31T20:44:50.416+02:00  INFO 22037 --- [padelBooking] [main] a.l.p.p.c.BookingCalendarController      : Player{id=239, name='Ana', residence=null, bookings=null}
        /// 2025-08-31T20:44:50.416+02:00  INFO 22037 --- [padelBooking] [main] a.l.p.p.c.BookingCalendarController      : 1950
        /// 2025-08-31T20:44:50.422+02:00  INFO 22037 --- [padelBooking] [main] a.l.p.p.service.BookingServiceImpl       : FALTA: Avisar de que la reserva deje de estar available
        /// 2025-08-31T20:44:50.423+02:00  INFO 22037 --- [padelBooking] [main] a.l.p.p.service.BookingServiceImpl       : ANUNCIO: Reserva hecha
        /// 2025-08-31T20:44:50.426+02:00  INFO 22037 --- [padelBooking] [main] a.l.p.p.model.BookingCalendar            :
        /// *** Sorry, we couldn't process your booking. This may be due to a lack of availability or invalid booking details

    }

}
