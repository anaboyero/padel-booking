package ana.learning.padel.padelBooking;

import ana.learning.padel.padelBooking.model.Booking;
import ana.learning.padel.padelBooking.model.BookingCalendar;
import ana.learning.padel.padelBooking.model.Player;
import ana.learning.padel.padelBooking.model.Residence;
import jakarta.persistence.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PadelBookingApplicationTests {
	private static final String NAME_OF_PLAYER1 = "Ana";
	private static final String NAME_OF_PLAYER2 = "Javier";
	private static final Residence RESIDENCE_OF_PLAYER1 = new Residence(Residence.Building.JUAN_MARTIN_EMPECINADO_21, Residence.Floor.FIFTH_FLOOR, Residence.Letter.A);
	private static final Residence RESIDENCE_OF_PLAYER2 = new Residence(Residence.Building.RAMIREZ_PRADO_7, Residence.Floor.SECOND_FLOOR, Residence.Letter.E);
	private static final Booking.TimeSlot SLOT = Booking.TimeSlot.NINE_AM;
	private static final LocalDate TODAY = LocalDate.now();


	@Test
	void contextLoads() {
	}

	@Test
	@DisplayName("Should create a new player from scratch")
	public void shouldCreateANewPlayerFromScratch () {
		Player player1 = new Player(NAME_OF_PLAYER1, RESIDENCE_OF_PLAYER1);
		assertThat(player1.getName()).isEqualTo(NAME_OF_PLAYER1);
		assertThat(player1.getResidence()).isEqualTo(RESIDENCE_OF_PLAYER1);
	}

	@Test
	@DisplayName("Should create a new Booking")
	public void shouldCreateANewBooking() {
		Player player1 = new Player(NAME_OF_PLAYER1, RESIDENCE_OF_PLAYER1);
		Booking booking = new Booking(TODAY, SLOT, player1);
		assertThat(booking.getDay()).isEqualTo(TODAY);
		assertThat(booking.getTimeSlot()).isEqualTo(SLOT);
		assertThat(booking.getBooking_owner()).isEqualTo(player1);
	}

//	@Test
//	@DisplayName("Should create a new Calendar")
//	public void shouldCreateANewBookingCalendar() {
//		BookingCalendar bookingCalendar = new BookingCalendar();
//		assertThat(bookingCalendar.getAvailableBookings()).isNotNull();
//		assertThat(bookingCalendar.getReservedBookings()).isNotNull();
//	}


}
