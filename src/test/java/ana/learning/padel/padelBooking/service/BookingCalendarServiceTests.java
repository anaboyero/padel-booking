package ana.learning.padel.padelBooking.service;

import ana.learning.padel.padelBooking.exceptions.PastDateException;
import ana.learning.padel.padelBooking.model.Booking;
import ana.learning.padel.padelBooking.model.BookingCalendar;
import ana.learning.padel.padelBooking.model.Player;
import ana.learning.padel.padelBooking.model.Residence;
import ana.learning.padel.padelBooking.repository.BookingCalendarRepository;
import ana.learning.padel.padelBooking.repository.BookingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


//@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookingCalendarServiceTests {

    private static final String NAME_OF_PLAYER1 = "Ana";
    private static final String NAME_OF_PLAYER2 = "Javier";
    private static final Booking.TimeSlot SLOT = Booking.TimeSlot.TWO_PM;
    private static final LocalDate TODAY = LocalDate.now();
    private static final Residence.Building RESIDENCE_BUILDING_EMPECINADO21 = Residence.Building.JUAN_MARTIN_EMPECINADO_21;
    private static final Residence.Floor RESIDENCE_5FLOOR = Residence.Floor.FIFTH;
    private static final Residence.Letter RESIDENCE_LETTER_A = Residence.Letter.A;
    private static final int MAX_NUM_OF_SLOTS_PER_WEEK = 5*7;

    Player player1;
    Player player2;
    Booking tentativeBooking;
    BookingCalendar bookingCalendar;
    BookingCalendar bookingCalendarToSave;

    private static final Logger log = LoggerFactory.getLogger(BookingCalendarServiceTests.class);

    @Autowired
    private BookingCalendarRepository bookingCalendarRepository;
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private BookingCalendarService bookingCalendarService;
    @Autowired
    private ResidenceService residenceService;

//    @BeforeEach
//    public void setUp(){
//
//        Residence residence = new Residence();
//        residence.setBuilding(RESIDENCE_BUILDING_EMPECINADO21);
//        residence.setFloor(RESIDENCE_5FLOOR);
//        residence.setLetter(RESIDENCE_LETTER_A);
//        Residence savedResidence = residenceService.saveResidence(residence);
//
//        player1 = new Player();
//        player1.setName(NAME_OF_PLAYER1);
//        player1.setResidence(savedResidence);
//
//        player2 = new Player();
//        player2.setName(NAME_OF_PLAYER2);
//        player2.setResidence(residence);
//
//        bookingCalendar = new BookingCalendar();
//        bookingCalendar.setStartDay(TODAY);
//
//        tentativeBooking = new Booking();
//        tentativeBooking.setBookingDate(TODAY);
//        tentativeBooking.setTimeSlot(SLOT);
//        tentativeBooking.setBookingOwner(player1);
//    }

    @Test
    public void shouldSaveANewBookingCalendar() {

        /// GIVEN A non-persisted calendar
        BookingCalendar calendar = new BookingCalendar(TODAY);
        assertThat((calendar.getReservedBookings()).size()).isEqualTo(0);
        assertThat((calendar.getAvailableBookings()).size()).isEqualTo(MAX_NUM_OF_SLOTS_PER_WEEK);
        assertThat((calendar.getId())).isNull();

        ///  WHEN persist the calendar
        BookingCalendar savedCalendar = bookingCalendarService.saveBookingCalendar(calendar);

        ///  THEN AVAILABLE BOOKINGS AND CALENDAR ARE PERSISTED AND THEY ARE LINKED
        assertThat(savedCalendar.getId()).isNotNull();
        assertThat(savedCalendar.getAvailableBookings().get(0).getId()).isNotNull();
        assertThat((calendar.getAvailableBookings()).size()).isEqualTo(MAX_NUM_OF_SLOTS_PER_WEEK);
    }



}
