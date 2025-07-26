package ana.learning.padel.padelBooking.service;

import ana.learning.padel.padelBooking.model.Booking;
import ana.learning.padel.padelBooking.model.BookingCalendar;
import ana.learning.padel.padelBooking.model.Player;
import ana.learning.padel.padelBooking.repository.BookingCalendarRepository;
import ana.learning.padel.padelBooking.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class BookingCalendarServiceImpl implements BookingCalendarService{
    @Autowired
    BookingCalendarRepository bookingCalendarRepository;
    @Override
    public BookingCalendar saveBookingCalendar(BookingCalendar bookingCalendar){
        return bookingCalendarRepository.save(bookingCalendar);
    }

    @Override
    public boolean isBookingAvailable(Booking booking, BookingCalendar bookingCalendar){
        return bookingCalendar.isBookingAvailable(booking);
    }

    @Override
    public Optional<Booking> reserveBooking(Booking booking, BookingCalendar bookingCalendar){
        return bookingCalendar.reserveBooking(booking);
    }


}
