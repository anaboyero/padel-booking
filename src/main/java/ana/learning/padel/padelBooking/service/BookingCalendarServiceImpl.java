package ana.learning.padel.padelBooking.service;

import ana.learning.padel.padelBooking.model.Booking;
import ana.learning.padel.padelBooking.model.BookingCalendar;
import ana.learning.padel.padelBooking.model.Player;
import ana.learning.padel.padelBooking.repository.BookingCalendarRepository;
import ana.learning.padel.padelBooking.repository.BookingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class BookingCalendarServiceImpl implements BookingCalendarService{
    @Autowired
    BookingCalendarRepository bookingCalendarRepository;
    @Autowired
    BookingService bookingService;
    private static final Logger log = LoggerFactory.getLogger(BookingCalendarServiceImpl.class);

    public BookingCalendarServiceImpl(BookingCalendarRepository bookingCalendarRepository, BookingService bookingService) {
        this.bookingCalendarRepository = bookingCalendarRepository;
        this.bookingService = bookingService;
    }

    private void persistAvailableBookings(BookingCalendar bookingCalendar){
        log.info("\n*** Entro en el bucle for para persistir los bookings");
        for (Booking booking : bookingCalendar.getAvailableBookings()) {
            bookingService.saveBooking(booking);
        }
    }

    @Override
    public List<BookingCalendar> getAllBookingCalendars(){
        return (List<BookingCalendar>) bookingCalendarRepository.findAll();
    }


    @Override
    public BookingCalendar saveBookingCalendar(BookingCalendar bookingCalendar){
        if (bookingCalendar.getId() == null) {
            log.info("\n*** Como es la primera vez que vamos a persistir el calendario, persisto antes los bookings disponibles y sin asignar(o sea, todos)");
            persistAvailableBookings(bookingCalendar);
        }
        log.info("\n*** Persisto el calendario");
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
