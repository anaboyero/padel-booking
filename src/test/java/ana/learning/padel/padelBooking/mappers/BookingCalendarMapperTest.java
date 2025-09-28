//package ana.learning.padel.padelBooking.mappers;
//
//import ana.learning.padel.padelBooking.model.Booking;
//import ana.learning.padel.padelBooking.model.BookingCalendar;
//import org.junit.jupiter.api.Test;
//
//import java.time.LocalDate;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class BookingCalendarMapperTest {
//
//    private static final LocalDate TODAY = LocalDate.now();
//    BookingCalendarMapper mapper;
//
//
//    @Test
//    public void convertToDTO () {
//        ///  GIVEN a BookingCalendar
//        Booking booking1 = new Booking();
//        booking1.setId(13L);
//        Booking booking2 = new Booking();
//        booking2.setId(25L);
//        List<Booking> bookings = List.of(booking1, booking2);
//
//        BookingCalendar calendar = new BookingCalendar();
//        calendar.setStartDay(TODAY);
//        calendar.setId(10L);
//        calendar.setAvailableBookings(bookings);
//
//
//        ///  when
//
//        BookingCalendarMapper.toDTO()
//
//
//    }
//
//}