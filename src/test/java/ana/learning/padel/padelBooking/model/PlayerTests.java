package ana.learning.padel.padelBooking.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.StatusResultMatchersExtensionsKt.isEqualTo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
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
    public void createAndPersistResidence(){
        residence = new Residence();
        residence.setBuilding(RESIDENCE_BUILDING_EMPECINADO21);
        residence.setFloor(RESIDENCE_5FLOOR);
        residence.setLetter(RESIDENCE_LETTER_A);
        residence.setId(1L);
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
        player = setPlayerWithTwoOwnedBookings(TODAY);
        assertThat(player.getBookings().size()).isEqualTo(2);
        /// WHEN cancelling one of the owned bookings
        player.cancelBooking(player.getBookings().get(0));
        /// THEN the player only owns one booking
        assertThat(player.getBookings().size()).isEqualTo(1);
    }

    @Test
    public void shouldNotCancelPastBookingFromPlayer() {
        /// GIVEN a player with an owned booking in the past
        player = setPlayerWithTwoOwnedBookings(YESTERDAY);

        /// WHEN trying to cancel that booking
        player.cancelBooking(player.getBookings().get(0));

        /// then the booking is not cancelled
        assertThat(player.getBookings().size()).isEqualTo(2);

    }

//    @Disabled("Estos metodos me los estoy replanteando para tratarlo desde booking y no desde player")
//    @Test
//    public void shouldAddBooking(){
//        /// GIVEN a player with no bookings
//        player = new Player();
//        player.setName(NAME_OF_PLAYER1);
//        player.setResidence(residence);
//        assertThat(player.getBookings().size()).isEqualTo(0);
//
//        /// WHEN adding a booking
//        booking1 = new Booking();
//        booking1.setBookingDate(TODAY);
//        booking1.setTimeSlot(SLOT);
//        player.addBooking(booking1);
//
//        /// THEN the player has one booking
//        assertThat(player.getBookings().size()).isEqualTo(1);
//    }

    private List<Booking> createConsecutiveBookings (int numBookings, LocalDate start) {
        LocalDate startDate;
        if (start==null) {
            startDate = TODAY;
        }
        else {
            startDate = start;
        }
        List<Booking> bookings = new ArrayList<>();
        if (numBookings < 1) {
            return bookings;
        }
        for (int i = 0; i<numBookings; i++) {
            Booking booking = new Booking();
            booking.setBookingDate(startDate.plusDays(i));
            booking.setTimeSlot(SLOT);
            booking.setId((long)(1 + i));
            bookings.add(booking);
        }
        return bookings;
    }

    private Player createAndPersist (Residence residence) {
        player = new Player();
        player.setName(NAME_OF_PLAYER1);
        player.setId(1L);
        if (residence!=null) {
            player.setResidence(residence);
        }
        return player;
    }

    private Player setPlayerWithTwoOwnedBookings(LocalDate start){
        List<Booking> bookings = createConsecutiveBookings(2, start);
        player = createAndPersist(residence);
        player.setBookings(bookings);
        return player;
    }

}
