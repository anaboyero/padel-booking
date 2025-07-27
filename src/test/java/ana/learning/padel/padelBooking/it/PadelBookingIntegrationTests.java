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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

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
    private static final Residence.Building RESIDENCE_BUILDING_EMPECINADO21 = Residence.Building.JUAN_MARTIN_EMPECINADO_21;
    private static final Residence.Floor RESIDENCE_5FLOOR = Residence.Floor.FIFTH_FLOOR;
    private static final Residence.Letter RESIDENCE_LETTER_A = Residence.Letter.A;
    private static final Booking.TimeSlot SLOT = Booking.TimeSlot.TWO_PM;
    private static final LocalDate TODAY = LocalDate.now();

    private static final Logger log = LoggerFactory.getLogger(PadelBookingIntegrationTests.class);

    Residence residence;
    Player player;

    @BeforeEach
    public void setUp(){
        residence = new Residence();
        residence.setBuilding(RESIDENCE_BUILDING_EMPECINADO21);
        residence.setFloor(RESIDENCE_5FLOOR);
        residence.setLetter(RESIDENCE_LETTER_A);
        residence = residenceService.saveResidence(residence);

        player = new Player();
        player.setName(NAME_OF_PLAYER1);
        player.setResidence(residence);
    }

    @Test
    public void ShouldRegisterANewPlayer() {
        player = playerService.savePlayer(player);
        assertThat(player.getId()).isNotNull();
    }

    @Test
    public void ShouldPersistABooking_GivenAPersistentPlayer() {
        player = playerService.savePlayer(player);
        assertThat(player.getId()).isNotNull();
        assertThat(residence.getId()).isNotNull();

        Booking booking = new Booking();
        booking.setBookingDate(TODAY);
        booking.setTimeSlot(SLOT);
        booking.setBookingOwner(player);

        booking = bookingService.saveBooking(booking);

        assertThat(booking.getBookingDate()).isEqualTo(TODAY);
        assertThat(booking.getTimeSlot()).isEqualTo(SLOT);
        assertThat(booking.getBookingOwner()).isEqualTo(player);
        assertThat(booking.getId()).isNotNull();
    }

    @Test
    public void shouldPersistCalendarGivenADate() {
        BookingCalendar bookingCalendar = new BookingCalendar();
        bookingCalendar = bookingCalendarService.saveBookingCalendar(bookingCalendar);
        assertThat(bookingCalendar.getId()).isNotNull();
    }
}
