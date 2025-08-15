package ana.learning.padel.padelBooking.controller;

import ana.learning.padel.padelBooking.DTO.CreateCalendarRequest;
import ana.learning.padel.padelBooking.model.BookingCalendar;
import ana.learning.padel.padelBooking.repository.BookingCalendarRepository;
import ana.learning.padel.padelBooking.service.BookingCalendarService;
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
//    final BookingCalendarRepository bookingCalendarRepository;
    private final Logger log = LoggerFactory.getLogger(BookingCalendarController.class);


    public BookingCalendarController(BookingCalendarService bookingCalendarService) {
        this.bookingCalendarService = bookingCalendarService;
//        this.bookingCalendarRepository = bookingCalendarRepository;
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


}
