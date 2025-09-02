package ana.learning.padel.padelBooking.service;

import ana.learning.padel.padelBooking.model.Booking;
import ana.learning.padel.padelBooking.model.Player;

import java.util.List;
import java.util.Optional;

public interface BookingService {
    Booking saveBooking(Booking booking);
    List<Booking> getAllBookings();
    Optional<Booking> getBookingById(Long id);
    Booking assignBookingToPlayer(Booking booking, Player player);
//    Boolean isAvailable();
}
