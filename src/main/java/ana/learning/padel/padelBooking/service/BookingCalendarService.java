package ana.learning.padel.padelBooking.service;

import ana.learning.padel.padelBooking.model.Booking;
import ana.learning.padel.padelBooking.model.BookingCalendar;
import ana.learning.padel.padelBooking.model.Player;

import java.time.LocalDate;
import java.util.Optional;

public interface BookingCalendarService {
    public BookingCalendar saveBookingCalendar(BookingCalendar bookingCalendar);
    public boolean isBookingAvailable(Booking booking, BookingCalendar bookingCalendar);
    public Optional<Booking> reserveBooking(Booking booking, BookingCalendar bookingCalendar);
}
