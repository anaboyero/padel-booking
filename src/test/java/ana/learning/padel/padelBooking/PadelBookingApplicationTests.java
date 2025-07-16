package ana.learning.padel.padelBooking;

import ana.learning.padel.padelBooking.model.Booking;
import ana.learning.padel.padelBooking.model.Player;
import ana.learning.padel.padelBooking.model.Residence;
import jakarta.persistence.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PadelBookingApplicationTests {
	private static final String NAME_OF_PLAYER1 = "Ana";
	private static final String NAME_OF_PLAYER2 = "Javier";
	private static final Residence RESIDENCE_OF_PLAYER1 = new Residence(Residence.Building.JUAN_MARTIN_EMPECINADO_21, Residence.Floor.FIFTH_FLOOR, Residence.Letter.A);
	private static final Residence RESIDENCE_OF_PLAYER2 = new Residence(Residence.Building.RAMIREZ_PRADO_7, Residence.Floor.SECOND_FLOOR, Residence.Letter.E);



	@Test
	void contextLoads() {
	}

	@Test
	@DisplayName("Should create a new player from scratch")
	public void shouldCreateANewPlayerFromScratch () {
		Player player = new Player(NAME_OF_PLAYER1, RESIDENCE_OF_PLAYER1);
		assertThat(player.getName()).isEqualTo(NAME_OF_PLAYER1);
		assertThat(player.getResidence()).isEqualTo(RESIDENCE_OF_PLAYER1);
	}

//	@Test
//	@DisplayName("Should create a new Booking")
//	public void shouldCreateANewBooking() {
//		Player player = new Player(NAME_OF_PLAYER1, RESIDENCE_OF_PLAYER1);
//		player.makeABooking();
//
//	}

}
