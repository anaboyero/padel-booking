package ana.learning.padel.padelBooking.controller;

import ana.learning.padel.padelBooking.DTO.BookingCalendarDTO;
import ana.learning.padel.padelBooking.DTO.BookingDTO;
import ana.learning.padel.padelBooking.DTO.CreateCalendarRequestDTO;
import ana.learning.padel.padelBooking.exceptions.PastDateException;
import ana.learning.padel.padelBooking.mappers.BookingCalendarMapper;
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
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/booking-calendars")
public class BookingCalendarController {

    private static final LocalDate TODAY = LocalDate.now();
    final BookingCalendarService bookingCalendarService;
    private final BookingService bookingService;
    private final PlayerService playerService;
    private final BookingMapper bookingMapper;
    private final BookingCalendarMapper bookingCalendarMapper;
    private final Logger log = LoggerFactory.getLogger(BookingCalendarController.class);

    public BookingCalendarController(BookingCalendarService bookingCalendarService, BookingService bookingService, PlayerService playerService, BookingMapper bookingMapper, BookingCalendarMapper bookingCalendarMapper) {
        this.bookingCalendarService = bookingCalendarService;
        this.bookingService = bookingService;
        this.playerService = playerService;
        this.bookingMapper = bookingMapper;
        this.bookingCalendarMapper = bookingCalendarMapper;
    }

    @GetMapping
    public List<BookingCalendar> getBookingCalendars() {
        return bookingCalendarService.getAllBookingCalendars();
    }


    @PostMapping
    public ResponseEntity<BookingCalendarDTO> createCalendar(@RequestBody CreateCalendarRequestDTO createCalendarRequestDTO) throws PastDateException {
        LocalDate startDate = createCalendarRequestDTO.getStartDay();

        BookingCalendar savedBookingCalendar = bookingCalendarService.createBookingCalendar(startDate);
        BookingCalendarDTO result = bookingCalendarMapper.customToDTO(savedBookingCalendar);


        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedBookingCalendar.getId())
                .toUri();

        return ResponseEntity.created(location).body(result);
    }


//    @PostMapping("/{calendarId}/bookings/{bookingId}")
//    public ResponseEntity<BookingDTO> reserveBooking(@PathVariable Long calendarId, @PathVariable Long bookingId, @RequestBody Player player){
//
//        Optional<Booking> temptativeBooking = bookingService.getBookingById(bookingId);
//        Optional<Player> temptativePlayer = playerService.getPlayerById(player.getId());
//        Optional<BookingCalendar> temptativeCalendar = bookingCalendarService.getBookingCalendarById(calendarId);
//
//        if ((temptativeBooking.isEmpty()) || (temptativePlayer.isEmpty()) || (temptativeCalendar.isEmpty()) ) {
//            return ResponseEntity.badRequest().body(null);
//        }
//
//        Optional<Booking> reservation = bookingCalendarService.reserveBooking(temptativeBooking.get(), temptativePlayer.get(), temptativeCalendar.get());
//
//        if (reservation.isEmpty()) {
//            return ResponseEntity.badRequest().body(null);
//        }
//        return ResponseEntity.ok().body(bookingMapper.toDTO(reservation.get()));
//    }

    @DeleteMapping
    public ResponseEntity<BookingCalendar> deleteAllBookingCalendars() {
        bookingCalendarService.deleteAllBookingCalendars();
        return ResponseEntity.noContent().build();
    }

}
