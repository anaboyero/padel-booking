package ana.learning.padel.padelBooking.service;

import ana.learning.padel.padelBooking.DTO.BookingCalendarDTO;
import ana.learning.padel.padelBooking.DTO.PlayerDTO;
import ana.learning.padel.padelBooking.exceptions.ResourceNotFoundException;
import ana.learning.padel.padelBooking.model.Booking;
import ana.learning.padel.padelBooking.model.BookingCalendar;
import ana.learning.padel.padelBooking.model.Player;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BookingCalendarService {
    BookingCalendar saveBookingCalendar(BookingCalendar bookingCalendar);
    List<BookingCalendar> getAllBookingCalendars();
    void deleteAllBookingCalendars();
    BookingCalendar createBookingCalendar (LocalDate startDate);
    Optional<BookingCalendar> getBookingCalendarById(Long id);
    Optional<Booking> reserveBooking(Long id, PlayerDTO playerDTO);
    void deleteBookingCalendarById(Long id) throws ResourceNotFoundException;
    public Optional<Booking> cancelBooking(Long id);
}
