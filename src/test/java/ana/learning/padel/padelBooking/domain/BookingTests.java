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

    private static final Logger log = LoggerFactory.getLogger(BookingTests.class);

    @BeforeEach
    public void setUp(){
        player = new Player();
        player.setName(NAME_OF_PLAYER1);
    }

    @Test
    @DisplayName("Should create a new Booking")
    public void shouldCreateANewBooking() {
        booking = new Booking();
        booking.setBookingDate(TODAY);
        booking.setTimeSlot(SLOT);
        booking.setBookingOwner(player);

        assertThat(booking.getBookingDate()).isEqualTo(TODAY);
        assertThat(booking.getTimeSlot()).isEqualTo(SLOT);
        assertThat(booking.getBookingOwner()).isEqualTo(player);
    }
}
