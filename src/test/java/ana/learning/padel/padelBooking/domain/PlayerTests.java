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

@SpringBootTest
public class PlayerTests {

    private static final String NAME_OF_PLAYER1 = "Ana";
    private static final Residence.Building RESIDENCE_BUILDING_EMPECINADO21 = Residence.Building.JUAN_MARTIN_EMPECINADO_21;
    private static final Residence.Floor RESIDENCE_5FLOOR = Residence.Floor.FIFTH_FLOOR;
    private static final Residence.Letter RESIDENCE_LETTER_A = Residence.Letter.A;
    private static final int MAX_NUM_OF_SLOTS_PER_WEEK = 13*7;
    Residence residence;
    Player player;

    private static final Logger log = LoggerFactory.getLogger(PlayerTests.class);

    @BeforeEach
    public void setUp(){
        residence = new Residence();
        residence.setBuilding(RESIDENCE_BUILDING_EMPECINADO21);
        residence.setFloor(RESIDENCE_5FLOOR);
        residence.setLetter(RESIDENCE_LETTER_A);
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
}
