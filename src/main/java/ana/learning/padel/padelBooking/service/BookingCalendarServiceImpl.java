package ana.learning.padel.padelBooking.service;

import ana.learning.padel.padelBooking.model.BookingCalendar;
import ana.learning.padel.padelBooking.repository.BookingCalendarRepository;
import ana.learning.padel.padelBooking.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookingCalendarServiceImpl implements BookingCalendarService{
    @Autowired
    BookingCalendarRepository bookingCalendarRepository;
    @Override
    public BookingCalendar saveBookingCalendar(BookingCalendar bookingCalendar){
        return bookingCalendarRepository.save(bookingCalendar);
    }


}
