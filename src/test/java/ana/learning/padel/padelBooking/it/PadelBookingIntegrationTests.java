package ana.learning.padel.padelBooking.it;

import ana.learning.padel.padelBooking.model.Booking;
import ana.learning.padel.padelBooking.model.BookingCalendar;
import ana.learning.padel.padelBooking.model.Player;
import ana.learning.padel.padelBooking.model.Residence;
import ana.learning.padel.padelBooking.repository.PlayerRepository;
import ana.learning.padel.padelBooking.repository.ResidenceRepository;
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

    private static final String NAME_OF_PLAYER1 = "Ana";
    private static final String NAME_OF_PLAYER2 = "Javier";
    private static final Residence.Building RESIDENCE_BUILDING_PLAYER1 = Residence.Building.JUAN_MARTIN_EMPECINADO_21;
    private static final Residence.Floor RESIDENCE_FLOOR_PLAYER1 = Residence.Floor.FIFTH_FLOOR;
//    private static final Residence RESIDENCE_OF_PLAYER1 = new Residence(Residence.Building.JUAN_MARTIN_EMPECINADO_21, Residence.Floor.FIFTH_FLOOR, Residence.Letter.A);
//    private static final Residence RESIDENCE_OF_PLAYER2 = new Residence(Residence.Building.RAMIREZ_PRADO_7, Residence.Floor.SECOND_FLOOR, Residence.Letter.E);
    private static final Booking.TimeSlot SLOT = Booking.TimeSlot.NINE_AM;
    private static final LocalDate TODAY = LocalDate.now();
    private static final int MAX_NUM_OF_SLOTS_PER_WEEK = 13*7;


    @Test
    @DisplayName("Should create and persist a new player from scratch")
    public void shouldCreateAndPersistANewPlayerFromScratch () {

        Residence residence1 = new Residence();
        residence1.setBuilding(Residence.Building.JUAN_MARTIN_EMPECINADO_21);
        residence1.setFloor(Residence.Floor.FIFTH_FLOOR);
        Residence savedResidence = residenceService.saveResidence(residence1);

        Player player1 = new Player();
        player1.setName(NAME_OF_PLAYER1);
        player1.setResidence(savedResidence);
        Player savedPlayer = playerService.savePlayer(player1);

        assertThat(savedPlayer.getId()).isNotNull();
        System.out.println("****** Should persisted a new player with id: " + savedPlayer);
    }

    @Test
    @DisplayName("Should create and persist a new Booking given a persistent player")
    public void ShouldCreateAndPersistANewBooking_GivenAPersistentPlayer() {
        Residence residence1 = new Residence();
        residence1.setBuilding(Residence.Building.JUAN_MARTIN_EMPECINADO_21);
        residence1.setFloor(Residence.Floor.FIFTH_FLOOR);
        Residence savedResidence = residenceService.saveResidence(residence1);

        Player player1 = new Player();
        player1.setName(NAME_OF_PLAYER1);
        player1.setResidence(savedResidence);
        Player savedPlayer = playerService.savePlayer(player1);

        assertThat(savedPlayer.getId()).isNotNull();
        System.out.println("****** Should persisted a new player with id: " + savedPlayer);

        Booking booking = new Booking();
        booking.setBookingDate(TODAY);
        booking.setTimeSlot(SLOT);
        booking.setBookingOwner(savedPlayer);
        System.out.println("****** el booking con owner y todo (sin persistir) es: " + booking);

        Booking savedBooking = bookingService.saveBooking(booking);
        System.out.println("****** Should have persisted a new booking with id: " + savedBooking);

//        assertThat(savedBooking.getBookingDate()).isEqualTo(TODAY);
//        assertThat(savedBooking.getTimeSlot()).isEqualTo(SLOT);
//        assertThat(savedBooking.getBookingOwner()).isEqualTo(savedPlayer);
//        assertThat(savedBooking.getId()).isNotNull();
//        System.out.println("****** Should create a new Booking" + booking);
    }
//
//    @Test
//    @DisplayName("Given a date, should create a new Calendar")
//    public void shouldCreateANewBookingCalendarGivenADate() {
//        BookingCalendar bookingCalendar = new BookingCalendar(TODAY);
//        assertThat((bookingCalendar.getAvailableBookings()).size()).isEqualTo(MAX_NUM_OF_SLOTS_PER_WEEK);
//        assertThat((bookingCalendar.getReservedBookings()).size()).isEqualTo(0);
//        assertThat(bookingCalendar.getId()).isNotNull();
//        System.out.println("****** Should create a new bookingCalendar" + bookingCalendar);
//    }

}
