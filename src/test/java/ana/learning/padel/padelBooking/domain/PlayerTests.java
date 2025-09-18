package ana.learning.padel.padelBooking.domain;

import ana.learning.padel.padelBooking.model.Booking;
import ana.learning.padel.padelBooking.model.Player;
import ana.learning.padel.padelBooking.model.Residence;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.StatusResultMatchersExtensionsKt.isEqualTo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootTest
public class PlayerTests {

    private static final String NAME_OF_PLAYER1 = "Ana";
    private static final Residence.Building RESIDENCE_BUILDING_EMPECINADO21 = Residence.Building.JUAN_MARTIN_EMPECINADO_21;
    private static final Residence.Floor RESIDENCE_5FLOOR = Residence.Floor.FIFTH;
    private static final Residence.Letter RESIDENCE_LETTER_A = Residence.Letter.A;
    private static final Booking.TimeSlot SLOT = Booking.TimeSlot.TWO_PM;
    private static final LocalDate TODAY = LocalDate.now();
    private static final LocalDate TOMORROW = LocalDate.now().plusDays(1);
    private static final LocalDate YESTERDAY = LocalDate.now().minusDays(1);
    private static final int MAX_NUM_OF_SLOTS_PER_WEEK = 13*7;
    Residence residence;
    Player player;
    Booking booking1;
    Booking booking2;

    private static final Logger log = LoggerFactory.getLogger(PlayerTests.class);

    @BeforeEach
    public void setUp(){


        residence = new Residence();
        residence.setBuilding(RESIDENCE_BUILDING_EMPECINADO21);
        residence.setFloor(RESIDENCE_5FLOOR);
        residence.setLetter(RESIDENCE_LETTER_A);
        residence.setId(1L); // esto lo estoy falseando yo, como si se hubiera persistido
    }

    private void setPlayerWithTwoOwnedBookings(){

        booking1 = new Booking();
        booking1.setBookingDate(TODAY);
        booking1.setTimeSlot(SLOT);
        booking1.setId(1L);;
        booking2 = new Booking();
        booking2.setBookingDate(TOMORROW);
        booking2.setTimeSlot(SLOT);
        booking2.setId(2L);;
        List<Booking> bookings = new java.util.ArrayList<>();
        player = new Player();
        player.setId(10L);
        player.setResidence(residence);
        bookings.add(booking1);
        bookings.add(booking2);
        player.setBookings(bookings);
    }

    @Test
    @DisplayName("Should create a new player from scratch")
    public void shouldCreateANewPlayerFromScratch () {
        player = new Player();
        player.setName(NAME_OF_PLAYER1);
        player.setResidence(residence);

        assertThat(player.getName()).isEqualTo(NAME_OF_PLAYER1);
        assertThat(player.getResidence()).isEqualTo(residence);
    }

    @Test
    public void shouldCancelOwnedBooking() {
        /// GIVEN a player with 2 owned bookings
        setPlayerWithTwoOwnedBookings();
        assertThat(player.getBookings().size()).isEqualTo(2);
        /// WHEN cancelling one of the owned bookings
        player.cancelBooking(booking1);
        /// THEN the player only owns one booking
        assertThat(player.getBookings().size()).isEqualTo(1);
    }

    @Test
    public void shouldNotCancelPastBookingFromPlayer() {
        /// GIVEN a player with an owned booking in the past
        setPlayerWithTwoOwnedBookings();
        booking1.setBookingDate(YESTERDAY);

        /// WHEN trying to cancel that booking
        player.cancelBooking(booking1);

        /// then the booking is not cancelled
        assertThat(player.getBookings().size()).isEqualTo(2);

    }



}
