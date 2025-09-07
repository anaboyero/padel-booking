package ana.learning.padel.padelBooking.service;

import ana.learning.padel.padelBooking.model.Booking;
import ana.learning.padel.padelBooking.model.BookingCalendar;
import ana.learning.padel.padelBooking.model.Player;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BookingCalendarService {
    BookingCalendar saveBookingCalendar(BookingCalendar bookingCalendar);
    boolean isBookingAvailable(Booking booking, BookingCalendar bookingCalendar);
    List<BookingCalendar> getAllBookingCalendars();
    Optional<Booking> confirmBooking(Booking booking, BookingCalendar bookingCalendar);
    Optional<BookingCalendar> getBookingCalendarById(Long id);
    Optional<Booking> reserveBooking(Booking temptativeBooking, Player temptativePlayer, BookingCalendar bookingCalendar);
}
