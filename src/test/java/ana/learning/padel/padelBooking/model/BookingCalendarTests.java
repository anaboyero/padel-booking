package ana.learning.padel.padelBooking.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import ana.learning.padel.padelBooking.exceptions.PastDateException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BookingCalendarTests {

    private static final String NAME_OF_PLAYER1 = "Ana";
    private static final LocalDate YESTERDAY = LocalDate.now().minusDays(1);
    private static final Residence.Building RESIDENCE_BUILDING_EMPECINADO21 = Residence.Building.JUAN_MARTIN_EMPECINADO_21;
    private static final Residence.Floor RESIDENCE_5FLOOR = Residence.Floor.FIFTH;
    private static final Residence.Letter RESIDENCE_LETTER_A = Residence.Letter.A;

    BookingCalendar bookingCalendar;
    Player player;
    Booking tentativeBooking;
    Residence residence;

    private static final Logger log = LoggerFactory.getLogger(BookingCalendarTests.class);

    @Test
    public void shouldNotCreateANewCalendarWithUnvalidDate(){

        ///  GIVEN A START DATE
        ///  Throws an exception WHEN trying to create a new calendar with a start day in the past

        assertThrows(PastDateException.class, () -> new BookingCalendar(YESTERDAY));
    }


//    @BeforeEach
//    public void setUp(){
//        bookingCalendar = new BookingCalendar();
//        bookingCalendar.setStartDay(TODAY);
//        residence = createAndPersistResidence();
//        player = createAndPersistPlayer(residence);
//        tentativeBooking = createAndPersistBooking();
//    }



//    @Test
//    public void shouldReserveAnAvailableBooking(){
//
//        /// GIVEN a valid player and an available booking in a calendar
//        BookingCalendar calendar = new BookingCalendar();
//        calendar.setStartDay(TODAY);
//        Booking availableBooking = new Booking();
//        availableBooking.setId(1L);
//        calendar.setAvailableBookings(List.of(availableBooking));
//
//        residence = createAndPersistResidence();
//        player = createAndPersistPlayer(residence);
//
//        assertThat(availableBooking.getBookingOwner()).isNull();
//        assertThat(calendar.getReservedBookings().size()).isEqualTo(0);
//        assertThat(player.getBookings().size()).isEqualTo(0);
//
//       /// WHEN the player tries to reserve the booking
//        Optional<Booking> result= availableBooking.reserveBooking(player);
//
//       /// THEN the booking is assigned to the player, the booking has the player as a owner and the calendar has the booking in reserved.
//        assertThat(result.get().getBookingOwner().getName()).isEqualTo(NAME_OF_PLAYER1);
//        assertThat(player.getBookings().get(0).getBookingOwner().getId()). isEqualTo(player.getId());
//        assertThat(calendar.getReservedBookings().size()).isEqualTo(1);
//    }
//
//    @Test
//    public void shouldNotReserveAnUnavailableBooking(){
//
//         /// GIVEN a valid player that owns a booking in a calendar
//        BookingCalendar calendar = new BookingCalendar();
//        calendar.setStartDay(TODAY);
//        Booking availableBooking = calendar.getAvailableBookings().get(0);
//        residence = createAndPersistResidence();
//        player = createAndPersistPlayer(residence);
//        List<Booking> playerBookings = player.getBookings();
//
//        Booking reservation= availableBooking.reserveBooking(player).get();
//
//        assertThat(reservation.getBookingOwner().getName()).isEqualTo(NAME_OF_PLAYER1);
//        assertThat(playerBookings.size()).isEqualTo(1);
//        assertThat(playerBookings.get(0).getBookingOwner().getId()). isEqualTo(player.getId());
//        assertThat(calendar.getReservedBookings().size()).isEqualTo(1);
//
//        /// WHEN another valid player tries to reserve the unavailable booking
//
//        Player secondPlayer = createAndPersistPlayer(residence);
//        secondPlayer.setId(10L);
//
//        Optional<Booking> result = availableBooking.reserveBooking(secondPlayer);
//
//        /// THEN the booking is not assigned to the player,
//        /// the booking mantains its owner and the calendar has the booking in reserved.
//
//        assertThat(result).isEmpty();
//        assertThat(reservation.getBookingOwner().getName()).isEqualTo(NAME_OF_PLAYER1);
//        assertThat(playerBookings.size()).isEqualTo(1);
//        assertThat(secondPlayer.getBookings().size()).isEqualTo(0);
//        assertThat(calendar.getReservedBookings().size()).isEqualTo(1);
//    }


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

//    @Test
//    private void shouldCreateValidAvailableBookings(){
//        ///  GIVEN A CALENDAR
//        /// WHEN SETTING A START DAY
//        /// THEN IT RETURNS A LIST OF 28 AVAILABLE BOOKINGS
//
//
//    }

//    private Booking createAndPersistBooking(){
//        Booking booking = new Booking();
//        booking.setBookingDate(TODAY);
//        booking.setTimeSlot(SLOT);
//        player.setId(300L);
//        return booking;
//    }

}
