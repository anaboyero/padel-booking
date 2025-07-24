package ana.learning.padel.padelBooking.domain;

import ana.learning.padel.padelBooking.model.Booking;
import ana.learning.padel.padelBooking.model.BookingCalendar;
import ana.learning.padel.padelBooking.model.Player;
import ana.learning.padel.padelBooking.model.Residence;
import jakarta.persistence.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.StatusResultMatchersExtensionsKt.isEqualTo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@SpringBootTest
public class PadelBookingBookingCalendarTests {

    private static final String NAME_OF_PLAYER1 = "Ana";
    private static final String NAME_OF_PLAYER2 = "Javier";
    private static final Residence.Building RESIDENCE_BUILDING_EMPECINADO21 = Residence.Building.JUAN_MARTIN_EMPECINADO_21;
    private static final Residence.Floor RESIDENCE_5FLOOR = Residence.Floor.FIFTH_FLOOR;
    private static final Residence.Letter RESIDENCE_LETTER_A = Residence.Letter.A;
    private static final Booking.TimeSlot SLOT = Booking.TimeSlot.NINE_AM;
    private static final LocalDate TODAY = LocalDate.now();
    private static final int MAX_NUM_OF_SLOTS_PER_WEEK = 13*7;

    private static final Logger log = LoggerFactory.getLogger(PadelBookingBookingCalendarTests.class);

    @BeforeEach
    public void setUp(){

    }

    @Test
    @DisplayName("****** Given a date, should create a new Calendar")
    public void shouldCreateANewBookingCalendarGivenADate() {

        BookingCalendar bookingCalendar = new BookingCalendar();
        bookingCalendar.setStartDay(TODAY);
        bookingCalendar.generateAvailableBookings();
        assertThat((bookingCalendar.getAvailableBookings()).size()).isEqualTo(MAX_NUM_OF_SLOTS_PER_WEEK);
        assertThat((bookingCalendar.getReservedBookings()).size()).isEqualTo(0);
        log.info("*********** TEST! ***********");
        log.info(bookingCalendar.toString());

    }

        @Test
    @DisplayName("should register a new player")
    public void ShouldRegisterANewPlayer() {
        // As a player, I want to register in the calendar so that I can book a slot to play padel.
            // GIVEN that I am not registered, WHEN I register my data THEN I receive my confirmation and id.
            Player newPlayer = new Player();




        }

//    @Test
//    @DisplayName("****** should confirm and book an available slot")
//    public void shouldConfirmAndBookAnAvailableSlot() {
//
//        // As a player, I want to book an available slot in the calendar so that I can play padel.
//
//        Player player = new Player();
//        player.setName(NAME_OF_PLAYER1);
//        player.setResidence(RESIDENCE_OF_PLAYER1);
//
//
//        BookingCalendar bookingCalendar = new BookingCalendar();
//        bookingCalendar.setStartDay(TODAY);
//        bookingCalendar.generateAvailableBookings();
//
//        Player player1 = new Player(NAME_OF_PLAYER1, RESIDENCE_OF_PLAYER1);
//
//
//        Booking desiredBooking = new Booking();
//        desiredBooking.setBookingDate(TODAY);
//        desiredBooking.setTimeSlot(SLOT);
//        desiredBooking.setBookingOwner(player1);
//
//        Optional<Booking> confirmedBooking = player.bookingCalendarService.askForBooking(desiredBooking);
//
//        Booking booking = new Booking(SLOT, player1, player2);
//        bookingCalendar.confirmAndBook(booking);
//
//        assertThat(bookingCalendar.getAvailableBookings().size()).isEqualTo(MAX_NUM_OF_SLOTS_PER_WEEK - 1);
//        assertThat(bookingCalendar.getReservedBookings().size()).isEqualTo(1);
//
//    }





}
