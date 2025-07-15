package ana.learning.padel.padelBooking;

import ana.learning.padel.padelBooking.model.Booking;
import ana.learning.padel.padelBooking.model.BookingCalendar;
import ana.learning.padel.padelBooking.model.Player;
import ana.learning.padel.padelBooking.model.Residence;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class PadelBookingApplication {

	public static void main(String[] args) {
		SpringApplication.run(PadelBookingApplication.class, args);

		Residence residence1 = new Residence(Residence.Building.JUAN_MARTIN_EMPECINADO_21, Residence.Floor.GROUND_FLOOR, Residence.Letter.A);
		Residence residence2 = new Residence(Residence.Building.JUAN_MARTIN_EMPECINADO_23, Residence.Floor.FIRST_FLOOR, Residence.Letter.B);
		Residence residence3 = new Residence(Residence.Building.JUAN_MARTIN_EMPECINADO_25, Residence.Floor.SEVENTH_FLOOR, Residence.Letter.C);
		Residence residence4 = new Residence(Residence.Building.RAMIREZ_PRADO_7, Residence.Floor.FIFTH_FLOOR, Residence.Letter.D);
		Residence residence5 = new Residence(Residence.Building.RAMIREZ_PRADO_8, Residence.Floor.SECOND_FLOOR, Residence.Letter.E);

		Player player1 = new Player("Ana", residence1);
		Player player2 = new Player("Javier", residence4);

		LocalDate today = LocalDate.now();
		Booking reserva_lunes9AM = new Booking(today, Booking.TimeSlot.NINE_AM, player1);
		Booking reserva_viernes3PM = new Booking(today.plusDays(3), Booking.TimeSlot.THREE_PM, player2);

		List<Booking> reservations = new ArrayList<>();
		reservations.add(reserva_lunes9AM);
		reservations.add(reserva_viernes3PM);

		System.out.println("**** Booking 1: " + reserva_lunes9AM);
		System.out.println("**** Booking 2: " + reserva_viernes3PM);

//		BookingCalendar calendar = new BookingCalendar();
//		calendar.setReserved_bookings(reservations);
//		System.out.println("**** Calendar " + calendar);

	}

}
