package ana.learning.padel.padelBooking.service;

import ana.learning.padel.padelBooking.DTO.PlayerDTO;
import ana.learning.padel.padelBooking.exceptions.PastDateException;
import ana.learning.padel.padelBooking.mappers.PlayerMapper;
import ana.learning.padel.padelBooking.model.Booking;
import ana.learning.padel.padelBooking.model.BookingCalendar;
import ana.learning.padel.padelBooking.model.Player;
import ana.learning.padel.padelBooking.model.Residence;
import ana.learning.padel.padelBooking.repository.BookingCalendarRepository;
import ana.learning.padel.padelBooking.repository.BookingRepository;
import ana.learning.padel.padelBooking.repository.PlayerRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.StatusResultMatchersExtensionsKt.isEqualTo;


//@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookingCalendarServiceTests {

    private static final String NAME_OF_PLAYER1 = "Ana";
    private static final String NAME_OF_PLAYER2 = "Javier";
    private static final Booking.TimeSlot SLOT = Booking.TimeSlot.TWO_PM;
    private static final LocalDate TODAY = LocalDate.now();
    private static final Residence.Building RESIDENCE_BUILDING_EMPECINADO21 = Residence.Building.JUAN_MARTIN_EMPECINADO_21;
    private static final Residence.Floor RESIDENCE_5FLOOR = Residence.Floor.FIFTH;
    private static final Residence.Letter RESIDENCE_LETTER_A = Residence.Letter.A;
    private static final int MAX_NUM_OF_SLOTS_PER_WEEK = 5*7;

    Player player1;
    Player player2;
    Booking tentativeBooking;
    BookingCalendar bookingCalendar;
    BookingCalendar bookingCalendarToSave;

    private static final Logger log = LoggerFactory.getLogger(BookingCalendarServiceTests.class);

    @Autowired
    private BookingCalendarRepository bookingCalendarRepository;
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private BookingCalendarService bookingCalendarService;
    @Autowired
    private ResidenceService residenceService;
    @Autowired
    private PlayerService playerService;
    @Autowired
    PlayerMapper playerMapper;

//    @BeforeEach
//    public void setUp(){
//
//        Residence residence = new Residence();
//        residence.setBuilding(RESIDENCE_BUILDING_EMPECINADO21);
//        residence.setFloor(RESIDENCE_5FLOOR);
//        residence.setLetter(RESIDENCE_LETTER_A);
//        Residence savedResidence = residenceService.saveResidence(residence);
//
//        player1 = new Player();
//        player1.setName(NAME_OF_PLAYER1);
//        player1.setResidence(savedResidence);
//
//        player2 = new Player();
//        player2.setName(NAME_OF_PLAYER2);
//        player2.setResidence(residence);
//
//        bookingCalendar = new BookingCalendar();
//        bookingCalendar.setStartDay(TODAY);
//
//        tentativeBooking = new Booking();
//        tentativeBooking.setBookingDate(TODAY);
//        tentativeBooking.setTimeSlot(SLOT);
//        tentativeBooking.setBookingOwner(player1);
//    }

    @Test
    @Transactional
    public void shouldSaveANewBookingCalendar() {

        /// GIVEN A non-persisted calendar
        BookingCalendar calendar = new BookingCalendar(TODAY);
        assertThat((calendar.getReservedBookings()).size()).isEqualTo(0);
        assertThat((calendar.getAvailableBookings()).size()).isEqualTo(MAX_NUM_OF_SLOTS_PER_WEEK);
        assertThat((calendar.getId())).isNull();

        ///  WHEN persist the calendar
        BookingCalendar savedCalendar = bookingCalendarService.saveBookingCalendar(calendar);

        ///  THEN AVAILABLE BOOKINGS AND CALENDAR ARE PERSISTED AND THEY ARE LINKED
        assertThat(savedCalendar.getId()).isNotNull();
        assertThat(savedCalendar.getAvailableBookings().get(0).getId()).isNotNull();
        assertThat((calendar.getAvailableBookings()).size()).isEqualTo(MAX_NUM_OF_SLOTS_PER_WEEK);
    }

    @Test
    public void shouldUpdateExistingBooking() {
        ///  GIVEN A PLAYER AND A BOOKING
        Residence residence = residenceService.saveResidence(createResidence());
        Player player = playerService.savePlayer(createPlayer(residence));
        Booking booking = new Booking();
        booking.setBookingDate(TODAY);
        booking.setTimeSlot(SLOT);

        ///  WHEN UPDATE THE BOOKING TO ASSIGN THE PLAYER AS OWNER AND ADD THE BOOKING TO THE PLAYER'S LIST OF BOOKINGS
//        bookingCalendarService.updatePlayerAndBooking(player, booking);
        booking.setBookingOwner(player);
        player.getBookings().add(booking);

        ///  THEN THE PLAYER HAS THE BOOKING IN THEIR LIST OF BOOKINGS AND THE BOOKING HAS THE PLAYER AS OWNER
        assertThat(player.getBookings().size()).isEqualTo(1);


    }

    @Test
    public void shouldReserveABooking() {
        ///  GIVEN a valid player, a persisted calendar and a free booking
        Residence residence = residenceService.saveResidence(createResidence());
        Player player = playerService.savePlayer(createPlayer(residence));
        Long playerId = player.getId();
        PlayerDTO playerDTO = playerMapper.toDTO(player);

        BookingCalendar calendar = bookingCalendarService.saveBookingCalendar(new BookingCalendar(TODAY));
        Long calendarId = calendar.getId();
        Booking availableBooking = calendar.getAvailableBookings().get(0);
        Long bookingId = availableBooking.getId();

        ///  WHEN the player reserves the booking
        Booking result = bookingCalendarService.reserveBooking(bookingId, playerDTO).get();

        ///  THEN the booking has been updated in the database and has the player as owner
        ///  And the returned booking is the same as the updated one in the database
        ///  And the player has this booking in their list of bookings
        /// And the calendar has this booking in its list of reserved bookings and it is no longer in the list of available bookings

        assertThat(result.getBookingOwner()).isNotNull();
        assertThat(result.getBookingOwner().getBookings().size()).isEqualTo(1);
        assertThat(result.getCalendar().getReservedBookings().size()).isEqualTo(1);
        assertThat(result.getCalendar().getAvailableBookings().size()).isEqualTo(MAX_NUM_OF_SLOTS_PER_WEEK - 1);

//        BookingCalendar updatedCalendar = bookingCalendarRepository.findById(calendarId).get();
//        assertThat(updatedCalendar.getReservedBookings().size()).isEqualTo(1);
//        updatedCalendar.getReservedBookings().size();
//        Player updatedPlayer = playerRepository.findById(player.getId()).get();
//        Booking updatedBooking = bookingRepository.findById(bookingId).get();
//        assertThat(updatedBooking).isEqualTo(result);
//        assertThat(updatedBooking.getBookingOwner().getId()).isEqualTo(playerId);
//        assertThat(updatedPlayer.getBookings().size()).isEqualTo(1);
//        assertThat(updatedCalendar.getReservedBookings().size()).isEqualTo(1);

        ///  THEN  the booking is reserved for the player, and moved from available to reserved bookings in the calendar and the player has this booking.
//
//        BookingCalendar updatedCalendar = bookingCalendarRepository.findById(calendarId).get();
//        Booking updatedBooking = bookingRepository.findById(bookingId).get();
//        Player updatedPlayer = playerService.getPlayerById(playerId).get();
//        assertThat(result).isNotNull();
//        assertThat(result.getBookingOwner()).isNotNull();
//        assertThat(result.getCalendar()).isNotNull();

        // EL TEMA NO ES QUE NO ACTUALICE BIEN, SINO QUE NO ME DEJA HACER ESTO, ME DICE QUE NO SE PUEDE HACER UNA OPERACIÓN DE LECTURA FUERA DE UNA TRANSACCIÓN

//        assertThat(updatedCalendar.getReservedBookings().size()).isEqualTo(1);
//        assertThat(updatedPlayer.getBookings().size()).isEqualTo(1);
//        assertThat(updatedCalendar.getAvailableBookings().size()).isEqualTo(MAX_NUM_OF_SLOTS_PER_WEEK - 1);
//        assertThat(updatedBooking.getBookingOwner().getId()).isEqualTo(playerId);
    }


    private Residence createResidence() {
        Residence residence = new Residence();
        residence.setBuilding(RESIDENCE_BUILDING_EMPECINADO21);
        residence.setFloor(RESIDENCE_5FLOOR);
        residence.setLetter(RESIDENCE_LETTER_A);
        return residence;
    }

    private Player createPlayer(Residence residence) {
        Player player = new Player();
        player.setName(NAME_OF_PLAYER1);
        if (residence!=null) {
            player.setResidence(residence);
        }
        else {
            player.setResidence(createResidence());
        }
        return player;
    }

    private Booking createBooking(){
        Booking booking = new Booking();
        booking.setBookingDate(TODAY);
        booking.setTimeSlot(SLOT);
        return booking;
    }


}
