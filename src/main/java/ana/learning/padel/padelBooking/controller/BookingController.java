package ana.learning.padel.padelBooking.controller;

import ana.learning.padel.padelBooking.model.Booking;
import ana.learning.padel.padelBooking.service.BookingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/v1/bookings")
public class BookingController {

    BookingService bookingService;
    private static final Logger log = LoggerFactory.getLogger(BookingController.class);

    public BookingController(BookingService bookingService){
        this.bookingService = bookingService;
    }

    @GetMapping
    public List<Booking> getAllBookings() {

        log.info("\n ***Entrando en BookingController: getAllBookings()");
        return bookingService.getAllBookings();
    }


}
