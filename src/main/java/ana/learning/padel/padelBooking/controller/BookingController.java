package ana.learning.padel.padelBooking.controller;

import ana.learning.padel.padelBooking.DTO.BookingCalendarDTO;
import ana.learning.padel.padelBooking.DTO.BookingDTO;
import ana.learning.padel.padelBooking.DTO.PlayerDTO;
import ana.learning.padel.padelBooking.exceptions.IncompletePlayerException;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/bookings")
public class BookingController {

    private static final Logger log = LoggerFactory.getLogger(BookingController.class);
    private final BookingMapper bookingMapper;
    BookingService bookingService;
    PlayerService playerService;
    BookingCalendarService bookingCalendarService;

    public BookingController(BookingService bookingService, BookingMapper bookingMapper, PlayerService playerService, BookingCalendarService bookingCalendarService){
        this.bookingService = bookingService;
        this.bookingMapper = bookingMapper;
        this.playerService = playerService;
        this.bookingCalendarService = bookingCalendarService;
    }

    @GetMapping
    public ResponseEntity<List<BookingDTO>> getAllBookings() {
        log.info("Entrando en el controlador de Booking para obtener todas las reservas");
        List<Booking> bookings = bookingService.getAllBookings();
        log.info("Preparada para pasar todas las reservas a DTO y devolverlas");
        List<BookingDTO> bookingDTOS = bookings.stream()
                .map(bookingMapper::toDTO)
                .toList();
        return ResponseEntity.ok(bookingDTOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingDTO> getBookingById(@PathVariable Long id) {
        Optional<Booking> result = bookingService.getBookingById(id);
        if (result.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        BookingDTO bookingDTO = bookingMapper.toDTO(result.get());
        return ResponseEntity.ok(bookingDTO);
    }

    @PatchMapping("/{idBooking}/{idPlayer}")
    public ResponseEntity<BookingDTO> reserveBooking(@PathVariable Long idBooking, @PathVariable Long idPlayer) {

        Optional<Booking> result = bookingCalendarService.reserveBooking(idBooking, idPlayer);
        if(result.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        BookingDTO dto = bookingMapper.toDTO(result.get());
        return ResponseEntity.ok(dto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<BookingDTO> cancelBooking(@PathVariable Long id) {
        Optional<Booking> result = bookingService.cancelBooking(id);
        if (result.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().body(bookingMapper.toDTO(result.get()));
    }


}
