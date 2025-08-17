package ana.learning.padel.padelBooking.domain;

import ana.learning.padel.padelBooking.model.Booking;
import ana.learning.padel.padelBooking.model.BookingCalendar;
import ana.learning.padel.padelBooking.model.Player;
import ana.learning.padel.padelBooking.model.Residence;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BookingCalendarTests {

    private static final String NAME_OF_PLAYER1 = "Ana";
    private static final String NAME_OF_PLAYER2 = "Javier";
    private static final Booking.TimeSlot SLOT = Booking.TimeSlot.TWO_PM;
    private static final LocalDate TODAY = LocalDate.now();
    private static final int MAX_NUM_OF_SLOTS_PER_WEEK = 4*7;
    private static final Residence.Building RESIDENCE_BUILDING_EMPECINADO21 = Residence.Building.JUAN_MARTIN_EMPECINADO_21;
    private static final Residence.Floor RESIDENCE_5FLOOR = Residence.Floor.FIFTH;
    private static final Residence.Letter RESIDENCE_LETTER_A = Residence.Letter.A;


    BookingCalendar bookingCalendar;
    Player player;
    Booking tentativeBooking;

    private static final Logger log = LoggerFactory.getLogger(BookingCalendarTests.class);

    @BeforeEach
    public void setUp(){
        bookingCalendar = new BookingCalendar();
        bookingCalendar.setStartDay(TODAY);
        Residence residence = new Residence();
        residence.setBuilding(RESIDENCE_BUILDING_EMPECINADO21);
        residence.setFloor(RESIDENCE_5FLOOR);
        residence.setLetter(RESIDENCE_LETTER_A);
        residence.setId(12L);

        player = new Player();
        player.setName(NAME_OF_PLAYER1);
        player.setResidence(residence);

        tentativeBooking = new Booking();
        tentativeBooking.setBookingDate(TODAY);
        tentativeBooking.setTimeSlot(SLOT);
        tentativeBooking.setBookingOwner(player);
    }

    @Test
    @DisplayName("****** Given a date, should create a new Calendar")
    public void shouldCreateANewBookingCalendarGivenADate() {
        bookingCalendar.setStartDay(TODAY);
        assertThat((bookingCalendar.getReservedBookings()).size()).isEqualTo(0);
        assertThat((bookingCalendar.getAvailableBookings()).size()).isEqualTo(MAX_NUM_OF_SLOTS_PER_WEEK);
    }

    @Test
    public void shouldConfirmAnAvailableBooking(){
        Boolean result = bookingCalendar.isBookingAvailable(tentativeBooking);

        assertThat(result).isTrue();
    }

    @Test
    public void shouldNotConfirmAPastBooking(){
        tentativeBooking.setBookingDate(TODAY.minusDays(1));
        tentativeBooking.setTimeSlot(SLOT);

        assertThat(bookingCalendar.isBookingAvailable(tentativeBooking)).isFalse();
    }


    @Test
    public void shouldReserveAnAvailableBooking(){
        Optional<Booking> reservedBooking = bookingCalendar.reserveBooking(tentativeBooking);
        log.info("\n***Hasta aquí va bien");
        assertThat(reservedBooking.get()).isEqualTo(tentativeBooking);
        assertThat(reservedBooking.get().getBookingDate()).isEqualTo(TODAY);
        assertThat(reservedBooking.get().getTimeSlot()).isEqualTo(SLOT);
        assertThat(reservedBooking.get().getBookingOwner()).isEqualTo(player);
        log.info("\n***Hasta aquí va bien");
        assertThat((bookingCalendar.getReservedBookings()).size()).isEqualTo(1);
        log.info("\n***Hasta aquí va bien");
        assertThat((bookingCalendar.getAvailableBookings()).size()).isEqualTo(MAX_NUM_OF_SLOTS_PER_WEEK - 1);
        log.info("\n***Hasta aquí va bien");

    }

    @Test
    public void shouldNotReserveAnUnavailableBooking(){
        Booking firstBooking = new Booking();
        firstBooking.setBookingDate(TODAY);
        firstBooking.setTimeSlot(SLOT);
        firstBooking.setBookingOwner(player);

        Player player2 = new Player();
        player2.setName(NAME_OF_PLAYER2);
        tentativeBooking.setBookingOwner(player2);

        Optional<Booking> reservedBooking = bookingCalendar.reserveBooking(firstBooking);
        Optional<Booking> attemptedBooking = bookingCalendar.reserveBooking(tentativeBooking);

        assertThat(reservedBooking.get()).isEqualTo(tentativeBooking);
        assertThat(reservedBooking.get().getBookingOwner()).isEqualTo(player);
        assertThat(reservedBooking.get().getBookingOwner()).isNotEqualTo(player2);
        assertTrue(attemptedBooking.isEmpty());
        assertThat((bookingCalendar.getReservedBookings()).size()).isEqualTo(1);
        assertThat((bookingCalendar.getAvailableBookings()).size()).isEqualTo(MAX_NUM_OF_SLOTS_PER_WEEK - 1);
    }

    @Test
    public void shouldNotRegisterABookingWithoutAPlayer(){
        tentativeBooking.setBookingOwner(null);
        assertThat(bookingCalendar.reserveBooking(tentativeBooking)).isEmpty();
    }

    @Test
    public void shouldNotRegisterABookingWithAPlayerWithoutResidence(){
        Player playerWithNoResidence = new Player();
        tentativeBooking.setBookingOwner(playerWithNoResidence);

        assertThat(bookingCalendar.reserveBooking(tentativeBooking)).isEmpty();
    }

}
