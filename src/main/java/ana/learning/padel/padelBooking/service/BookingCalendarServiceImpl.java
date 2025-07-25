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
        if (booking.getBookingDate().isBefore(bookingCalendar.getStartDay())) {
            return false;
        }
        return true;
    }

//    @Override
//    public Optional<Booking> reserveBooking(Booking booking, BookingCalendar bookingCalendar){
//        if (isBookingAvailable(booking, bookingCalendar)){
//            return Optional.of(bookingCalendar.reserve(booking));
//        }
//        return Optional.empty();
//    }


}
