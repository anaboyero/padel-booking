package ana.learning.padel.padelBooking.domain;

import ana.learning.padel.padelBooking.model.Booking;
import ana.learning.padel.padelBooking.model.Player;
import ana.learning.padel.padelBooking.model.Residence;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.StatusResultMatchersExtensionsKt.isEqualTo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookingTests {

    private static final String NAME_OF_PLAYER1 = "Ana";
    private static final Residence.Building RESIDENCE_BUILDING_EMPECINADO21 = Residence.Building.JUAN_MARTIN_EMPECINADO_21;
    private static final Residence.Floor RESIDENCE_5FLOOR = Residence.Floor.FIFTH;
    private static final Residence.Letter RESIDENCE_LETTER_A = Residence.Letter.A;
    private static final Booking.TimeSlot SLOT = Booking.TimeSlot.TWO_PM;
    private static final LocalDate TODAY = LocalDate.now();
    Player player;
    Booking booking;
    Residence residence;

    private static final Logger log = LoggerFactory.getLogger(BookingTests.class);

    @BeforeEach
    public void setUp(){
        player = new Player();
        player.setName(NAME_OF_PLAYER1);
    }

    @Test
    public void shouldCreateANewBooking() {
        booking = new Booking();
        booking.setBookingDate(TODAY);
        booking.setTimeSlot(SLOT);
        booking.setBookingOwner(player);
        booking.setId(1L);

        assertThat(booking.getBookingDate()).isEqualTo(TODAY);
        assertThat(booking.getTimeSlot()).isEqualTo(SLOT);
        assertThat(booking.getBookingOwner()).isEqualTo(player);
        assertThat(booking.getId()).isEqualTo(1L);
    }

    @Test
    public void givenValidPlayer_shouldReserveAvailableBooking() {
        /// GIVEN an available booking and a player with residence and no bookings

        residence = createAndPersistResidence();
        booking = createAndPersistBooking();
        player = createAndPersistPlayer(residence);
        assertThat(player.getBookings().size()).isEqualTo(0);
        assertThat(booking.getBookingOwner()).isNull();

        /// WHEN the player tries to reserve the booking

        booking.reserveBooking(player);

        /// THEN the booking is assigned to the player, and the player has this booking

        assertThat(player.getBookings().size()).isEqualTo(1);
        assertThat(booking.getBookingOwner()).isNotNull();
        assertThat(booking.getBookingOwner().getName()).isEqualTo(NAME_OF_PLAYER1);

    }

    @Test
    public void givenValidPlayer_shouldNotReserveUnavailableBooking() {
        /// GIVEN a reserved booking, given a valid player

        booking = createAndPersistBooking();
        booking.setBookingOwner(new Player());
        residence = createAndPersistResidence();
        player = createAndPersistPlayer(residence);

        assertThat(player.getBookings().size()).isEqualTo(0);
        assertThat(booking.getBookingOwner()).isNotNull();

        /// WHEN the player tries to reserve the unavailable booking

        booking.reserveBooking(player);

        /// THEN the booking is not assigned to the player, and the player has not this booking

        assertThat(player.getBookings().size()).isEqualTo(0);
        assertThat(booking.getBookingOwner().getName()).isNotEqualTo(NAME_OF_PLAYER1);
    }

    @Test
    public void givenNotValidPlayer_shouldNotReserveAvailableBooking() {
        /// GIVEN an available booking, given a player with no residence

        booking = createAndPersistBooking();
        player = createAndPersistPlayer(null);

        assertThat(player.getBookings().size()).isEqualTo(0);
        assertThat(booking.getBookingOwner()).isNull();

        /// WHEN trying to reserve an available booking with unvalid player

        booking.reserveBooking(player);

        /// THEN the booking is not assigned to the player, and the player has not this booking

        assertThat(player.getBookings().size()).isEqualTo(0);
        assertThat(booking.getBookingOwner()).isNull();
    }

    private Residence createAndPersistResidence() {
        Residence residence = new Residence();
        residence.setBuilding(RESIDENCE_BUILDING_EMPECINADO21);
        residence.setFloor(RESIDENCE_5FLOOR);
        residence.setLetter(RESIDENCE_LETTER_A);
        residence.setId(1L);
        return residence;
    }

    private Player createAndPersistPlayer(Residence residence) {
        player = new Player();
        player.setName(NAME_OF_PLAYER1);
        player.setId(2L);
        if (residence!=null) {
            player.setResidence(residence);
        }
        return player;
    }

    private Booking createAndPersistBooking(){
        booking = new Booking();
        booking.setBookingDate(TODAY);
        booking.setTimeSlot(SLOT);
        player.setId(3L);
        return booking;
    }

}
