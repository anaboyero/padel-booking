package ana.learning.padel.padelBooking.domain;

import ana.learning.padel.padelBooking.model.Booking;
import ana.learning.padel.padelBooking.model.BookingCalendar;
import ana.learning.padel.padelBooking.model.Player;
import ana.learning.padel.padelBooking.model.Residence;
import ana.learning.padel.padelBooking.service.BookingCalendarService;
import ana.learning.padel.padelBooking.service.PlayerService;
import ana.learning.padel.padelBooking.service.ResidenceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


//@SpringBootTest
public class PadelBookingBookingCalendarTests {

    private static final String NAME_OF_PLAYER1 = "Ana";
    private static final String NAME_OF_PLAYER2 = "Javier";
    private static final Residence.Building RESIDENCE_BUILDING_EMPECINADO21 = Residence.Building.JUAN_MARTIN_EMPECINADO_21;
    private static final Residence.Floor RESIDENCE_5FLOOR = Residence.Floor.FIFTH_FLOOR;
    private static final Residence.Letter RESIDENCE_LETTER_A = Residence.Letter.A;
    private static final Booking.TimeSlot SLOT = Booking.TimeSlot.TWO_PM;
    private static final LocalDate TODAY = LocalDate.now();
    private static final int MAX_NUM_OF_SLOTS_PER_WEEK = 4*7;
    BookingCalendar bookingCalendar;
    Player player;

    private static final Logger log = LoggerFactory.getLogger(PadelBookingBookingCalendarTests.class);

    @BeforeEach
    public void setUp(){

        bookingCalendar = new BookingCalendar();
        bookingCalendar.setStartDay(TODAY);

        player = new Player();
        player.setName(NAME_OF_PLAYER1);
    }

    @Test
    @DisplayName("****** Given a date, should create a new Calendar")
    public void shouldCreateANewBookingCalendarGivenADate() {

        bookingCalendar.setStartDay(TODAY);
        assertThat((bookingCalendar.getAvailableBookings()).size()).isEqualTo(MAX_NUM_OF_SLOTS_PER_WEEK);
        assertThat((bookingCalendar.getReservedBookings()).size()).isEqualTo(0);
    }

    @Test
    public void shouldConfirmAnAvailableBooking(){

        Booking tentativeBooking = new Booking();
        tentativeBooking.setBookingDate(TODAY);
        tentativeBooking.setTimeSlot(SLOT);

        Boolean isAvailable = bookingCalendar.isBookingAvailable(tentativeBooking);

        assertThat(isAvailable).isTrue();
    }

    @Test
    public void shouldNotConfirmAPastBooking(){

        Booking tentativeBooking = new Booking();
        tentativeBooking.setBookingDate(TODAY.minusDays(1));
        tentativeBooking.setTimeSlot(SLOT);
        tentativeBooking.setBookingOwner(player);

        Boolean isAvailable = bookingCalendar.isBookingAvailable(tentativeBooking);

        assertThat(isAvailable).isFalse();
    }


    @Test
    public void shouldReserveAnAvailableBooking(){

        Booking tentativeBooking = new Booking();
        tentativeBooking.setBookingDate(TODAY);
        tentativeBooking.setTimeSlot(SLOT);
        tentativeBooking.setBookingOwner(player);
        log.info("\n ***** El calendario \n" + bookingCalendar.toString());
        log.info("\n ***** Fechas disponibles del calendario \n" + bookingCalendar.getAvailableBookings().toString());

        Optional<Booking> reservedBooking = bookingCalendar.reserveBooking(tentativeBooking);
        assertThat(reservedBooking.get()).isEqualTo(tentativeBooking);
        assertThat(reservedBooking.get().getBookingDate()).isEqualTo(TODAY);
        assertThat(reservedBooking.get().getTimeSlot()).isEqualTo(SLOT);
        assertThat(reservedBooking.get().getBookingOwner()).isEqualTo(player);
    }

    @Test
    public void shouldNotConfirmAReservedBooking(){

        Booking firstBooking = new Booking();
        firstBooking.setBookingDate(TODAY);
        firstBooking.setTimeSlot(SLOT);
        firstBooking.setBookingOwner(player);

        Player player2 = new Player();
        player2.setName(NAME_OF_PLAYER2);
        Booking tentativeBooking = new Booking();
        tentativeBooking.setBookingDate(TODAY);
        tentativeBooking.setTimeSlot(SLOT);
        tentativeBooking.setBookingOwner(player2);

        Optional<Booking> reservedBooking = bookingCalendar.reserveBooking(firstBooking);
        Optional<Booking> attemptedBooking = bookingCalendar.reserveBooking(tentativeBooking);

        assertThat(reservedBooking.get()).isEqualTo(tentativeBooking);
        assertTrue(attemptedBooking.isEmpty());
    }

}
