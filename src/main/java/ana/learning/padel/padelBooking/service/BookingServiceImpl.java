package ana.learning.padel.padelBooking.service;

import ana.learning.padel.padelBooking.model.Booking;
import ana.learning.padel.padelBooking.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    BookingRepository bookingRepository;

    @Override
    public Booking saveBooking(Booking booking) {
        // Logic to save the booking using bookingRepository
        return null; // Placeholder return statement
    }
}
