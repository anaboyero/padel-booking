package ana.learning.padel.padelBooking.it;

import ana.learning.padel.padelBooking.model.Booking;
import ana.learning.padel.padelBooking.model.BookingCalendar;
import ana.learning.padel.padelBooking.model.Player;
import ana.learning.padel.padelBooking.model.Residence;
import ana.learning.padel.padelBooking.repository.PlayerRepository;
import ana.learning.padel.padelBooking.repository.ResidenceRepository;
import ana.learning.padel.padelBooking.service.BookingCalendarService;
import ana.learning.padel.padelBooking.service.BookingService;
import ana.learning.padel.padelBooking.service.PlayerService;
import ana.learning.padel.padelBooking.service.ResidenceService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class PadelBookingIntegrationTests {

    @Autowired
    PlayerService playerService;

    @Autowired
    ResidenceService residenceService;

    @Autowired
    BookingService bookingService;

    @Autowired
    BookingCalendarService bookingCalendarService;

    private static final String NAME_OF_PLAYER1 = "Ana";
    private static final String NAME_OF_PLAYER2 = "Javier";
    private static final Residence.Building RESIDENCE_BUILDING_PLAYER1 = Residence.Building.JUAN_MARTIN_EMPECINADO_21;
    private static final Residence.Floor RESIDENCE_FLOOR_PLAYER1 = Residence.Floor.FIFTH_FLOOR;
    private static final Residence.Letter RESIDENCE_LETTER_A = Residence.Letter.A;
    private static final Booking.TimeSlot SLOT = Booking.TimeSlot.NINE_AM;
    private static final LocalDate TODAY = LocalDate.now();
    private static final int MAX_NUM_OF_SLOTS_PER_WEEK = 13*7;

    @Test
    @DisplayName("Should create and persist a new player from scratch")
    public void shouldCreateAndPersistANewPlayerFromScratch () {

        Residence residence = new Residence();
        residence.setBuilding(Residence.Building.JUAN_MARTIN_EMPECINADO_21);
        residence.setFloor(Residence.Floor.FIFTH_FLOOR);
        residence.setLetter(Residence.Letter.A);
        residence = residenceService.saveResidence(residence);
        System.out.println("****** Should persisted a new player with id: " + residence);

        Player player = new Player();
        player.setName(NAME_OF_PLAYER1);
        player.setResidence(residence);
        player = playerService.savePlayer(player);
        System.out.println("****** Should persisted a new player with id: " + player);

        assertThat(player.getId()).isNotNull();
    }

    @Test
    @DisplayName("Should create and persist a new Booking given a persistent player")
    public void ShouldCreateAndPersistANewBooking_GivenAPersistentPlayer() {
        Residence residence = new Residence();
        residence.setBuilding(Residence.Building.JUAN_MARTIN_EMPECINADO_21);
        residence.setFloor(Residence.Floor.FIFTH_FLOOR);
        residence.setLetter(Residence.Letter.A);
        residence = residenceService.saveResidence(residence);
        System.out.println("****** Should persisted a new residence with id: " + residence);

        Player player = new Player();
        player.setName(NAME_OF_PLAYER1);
        player.setResidence(residence);
        player = playerService.savePlayer(player);
        System.out.println("****** Should persisted a new player with id: " + player);
        assertThat(player.getId()).isNotNull();

        Booking booking = new Booking();
        booking.setBookingDate(TODAY);
        booking.setTimeSlot(SLOT);
        booking.setBookingOwner(player);
        System.out.println("****** Should have a booking without id: " + booking);
        booking = bookingService.saveBooking(booking);
        System.out.println("****** Should have persisted a new booking with id: " + booking);
        assertThat(booking.getId()).isNotNull();

        assertThat(booking.getBookingDate()).isEqualTo(TODAY);
        assertThat(booking.getTimeSlot()).isEqualTo(SLOT);
        assertThat(booking.getBookingOwner()).isEqualTo(player);
        assertThat(booking.getId()).isNotNull();
        System.out.println("****** Should create a new Booking" + booking);
    }

//    @Test
//    @DisplayName("Given a date, should persist a new Calendar")
//    public void GivenADate_ShouldPersistANewCalendar() {
//        BookingCalendar bookingCalendar = new BookingCalendar();
//        bookingCalendar.setLastUpdate(TODAY);
//        bookingCalendar = bookingCalendarService.saveBookingCalendar(bookingCalendar);
//
//        assertThat((bookingCalendar.getAvailableBookings()).size()).isEqualTo(MAX_NUM_OF_SLOTS_PER_WEEK);
//        assertThat((bookingCalendar.getReservedBookings()).size()).isEqualTo(0);
//        assertThat(bookingCalendar.getId()).isNotNull();
//        System.out.println("****** Should persist a new bookingCalendar" + bookingCalendar);
//    }


}
