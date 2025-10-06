package ana.learning.padel.padelBooking.model;

import org.junit.jupiter.api.BeforeEach;
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
    public void setUpPlayer(){
        player = new Player();
        player.setName(NAME_OF_PLAYER1);
    }

    @Test
    public void shouldCreateANewBooking() {

        BookingCalendar calendar = new BookingCalendar();
        booking = new Booking();
        booking.setBookingDate(TODAY);
        booking.setTimeSlot(SLOT);
        booking.setBookingOwner(player);
        booking.setCalendar(calendar);
        booking.setId(1L);

        assertThat(booking.getBookingDate()).isEqualTo(TODAY);
        assertThat(booking.getTimeSlot()).isEqualTo(SLOT);
        assertThat(booking.getBookingOwner()).isEqualTo(player);
        assertThat(booking.getCalendar()).isEqualTo(calendar);
        assertThat(booking.getId()).isEqualTo(1L);
    }

//    @Test
//    public void givenValidPlayer_shouldReserveAvailableBooking() {
//        /// GIVEN an available booking and a player with residence and no bookings
//        BookingCalendar calendar = new BookingCalendar();
//        calendar.setStartDay(TODAY);
//        Booking availableBooking = calendar.getAvailableBookings().get(0);
//        residence = createAndPersistResidence();
//        player = createAndPersistPlayer(residence);
//
//        assertThat(player.getBookings().size()).isEqualTo(0);
//        assertThat(availableBooking.getBookingOwner()).isNull();
//
//        /// WHEN the player tries to reserve the booking
//
//        availableBooking.reserveBooking(player);
//
//        /// THEN the booking is assigned to the player, and the player has this booking
//
//        assertThat(player.getBookings().size()).isEqualTo(1);
//        assertThat(availableBooking.getBookingOwner()).isNotNull();
//        assertThat(availableBooking.getBookingOwner().getName()).isEqualTo(NAME_OF_PLAYER1);
//
//    }

    @Test
    public void givenValidPlayer_shouldNotReserveUnavailableBooking() {
        /// GIVEN a reserved booking, given a valid player

        booking = createBooking();
        residence = createResidence();
        player = createPlayer(residence);
        booking.setBookingOwner(new Player());

        assertThat(player.getBookings().size()).isEqualTo(0);
        assertThat(booking.getBookingOwner()).isNotNull();

        /// WHEN the player tries to reserve the unavailable booking

        booking.reserveBooking(player);

        /// THEN the booking is not assigned to the player, and the player has not this booking

        assertThat(player.getBookings().size()).isEqualTo(0);
        assertThat(booking.getBookingOwner().getName()).isNotEqualTo(NAME_OF_PLAYER1);
    }

    @Test
    public void givenValidPlayer_shouldReserveAvailableBooking() {
        /// GIVEN a reserved booking, given a valid player

        Residence residence = new Residence();
        residence.setBuilding(RESIDENCE_BUILDING_EMPECINADO21);
        residence.setLetter(RESIDENCE_LETTER_A);
        residence.setFloor(RESIDENCE_5FLOOR);

        Player validPlayer = new Player();
        validPlayer.setResidence(residence);

        BookingCalendar calendar = new BookingCalendar(TODAY);
        Booking availableBooking = calendar.getAvailableBookings().get(0);

        assertThat(validPlayer.getBookings().size()).isEqualTo(0);
        assertThat(availableBooking.getBookingOwner()).isNull();

        /// WHEN the player tries to reserve the unavailable booking

        availableBooking.reserveBooking(validPlayer);

        /// THEN the booking is assigned to the player, and the player has this booking

        assertThat(validPlayer.getBookings().size()).isEqualTo(1);
        assertThat(availableBooking.getBookingOwner()).isNotNull();
    }

    ///  ESTO YA NO SUCEDE PORQUE LOS PLAYERS SE CREAN CON RESIDENCIA
//    @Test
//    public void givenNotValidPlayer_shouldNotReserveAvailableBooking() {
//        /// GIVEN an available booking, given a player with no residence
//
//        booking = createAndPersistBooking();
//        player = createAndPersistPlayer(null);
//
//        assertThat(player.getBookings().size()).isEqualTo(0);
//        assertThat(booking.getBookingOwner()).isNull();
//
//        /// WHEN trying to reserve an available booking with unvalid player
//
//        booking.reserveBooking(player);
//
//        /// THEN the booking is not assigned to the player, and the player has not this booking
//
//        assertThat(player.getBookings().size()).isEqualTo(0);
//        assertThat(booking.getBookingOwner()).isNull();
//    }

    private Residence createResidence() {
        Residence residence = new Residence();
        residence.setBuilding(RESIDENCE_BUILDING_EMPECINADO21);
        residence.setFloor(RESIDENCE_5FLOOR);
        residence.setLetter(RESIDENCE_LETTER_A);
        residence.setId(1L);
        return residence;
    }

    private Player createPlayer(Residence residence) {
        player = new Player();
        player.setName(NAME_OF_PLAYER1);
        player.setId(2L);
        if (residence!=null) {
            player.setResidence(residence);
        }
        else {
            player.setResidence(createResidence());
        }
        return player;
    }

    private Booking createBooking(){
        booking = new Booking();
        booking.setBookingDate(TODAY);
        booking.setTimeSlot(SLOT);
        player.setId(3L);
        return booking;
    }

}
