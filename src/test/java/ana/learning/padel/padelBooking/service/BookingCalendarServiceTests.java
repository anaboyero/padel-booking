package ana.learning.padel.padelBooking.service;

import ana.learning.padel.padelBooking.DTO.BookingCalendarDTO;
import ana.learning.padel.padelBooking.DTO.BookingDTO;
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
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
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
@Transactional
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
    private BookingService bookingService;
    @Autowired
    private ResidenceService residenceService;
    @Autowired
    private PlayerService playerService;
    @Autowired
    PlayerMapper playerMapper;
    @PersistenceContext
    private EntityManager entityManager;


    @Test
    public void shouldCreateNewCalendar() {
        ///  GIVEN A START DATE
        ///  WHEN creating a new calendar
        BookingCalendar calendar = bookingCalendarService.createBookingCalendar(TODAY);

        ///  THEN it should have persist a new calendar with 35 available persisted bookings linked to the calendar

        assertThat(calendar.getAvailableBookings().size()).isEqualTo(35);
        assertThat(calendar.getReservedBookings().size()).isEqualTo(0);
        assertThat(calendar.getAvailableBookings().get(0).getId()).isNotNull();
        assertThat(calendar.getAvailableBookings().get(0).getCalendar().getId()).isEqualTo(calendar.getId());
        assertThat(calendar.getAvailableBookings().get(1).getCalendar().getId()).isEqualTo(calendar.getId());
    }

    @Test
    @Transactional
    public void shouldSaveANewBookingCalendar() {

        /// GIVEN A non-persisted calendar with no date
        BookingCalendar calendar = new BookingCalendar();
        assertThat((calendar.getReservedBookings()).size()).isEqualTo(0);
        assertThat((calendar.getAvailableBookings()).size()).isEqualTo(0);
        assertThat((calendar.getId())).isNull();

        ///  WHEN persist the calendar with a date
        BookingCalendar savedCalendar = bookingCalendarService.createBookingCalendar(TODAY);

        ///  THEN AVAILABLE BOOKINGS AND CALENDAR ARE PERSISTED AND THEY ARE LINKED.
        assertThat(savedCalendar.getId()).isNotNull();
        assertThat(savedCalendar.getAvailableBookings().get(0).getId()).isNotNull();
        assertThat(savedCalendar.getAvailableBookings().get(0).getCalendar().getId()).isEqualTo(savedCalendar.getId());
        assertThat(savedCalendar.getAvailableBookings().get(1).getCalendar().getId()).isEqualTo(savedCalendar.getId());
        assertThat((savedCalendar.getAvailableBookings()).size()).isEqualTo(MAX_NUM_OF_SLOTS_PER_WEEK);
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

        BookingCalendar calendar = bookingCalendarService.createBookingCalendar(TODAY);

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
        assertThat(result.getCalendar()).isNotNull();
        assertThat(result.getCalendar().getReservedBookings().size()).isEqualTo(1);
        assertThat(result.getCalendar().getAvailableBookings().size()).isEqualTo(MAX_NUM_OF_SLOTS_PER_WEEK - 1);
    }

    @Test
    public void shouldCancelABooking() {
        ///  GIVEN a valid player with a booking reservation
        Residence residence = residenceService.saveResidence(createResidence());
        Player player = playerService.savePlayer(createPlayer(residence));
        Long playerId = player.getId();
        PlayerDTO playerDTO = playerMapper.toDTO(player);
        BookingCalendar calendar = bookingCalendarService.createBookingCalendar(TODAY);
        Long calendarId = calendar.getId();
        Booking availableBooking = calendar.getAvailableBookings().get(0);
        Long bookingId = availableBooking.getId();
        Booking reservation = bookingCalendarService.reserveBooking(bookingId, playerDTO).get();

        assertThat(reservation.getBookingOwner().getId()).isEqualTo(playerId);
        assertThat(reservation.getBookingOwner().getBookings().size()).isEqualTo(1);
        assertThat(reservation.getCalendar().getReservedBookings().size()).isEqualTo(1);
        assertThat(reservation.getCalendar().getAvailableBookings().size()).isEqualTo(MAX_NUM_OF_SLOTS_PER_WEEK - 1);

        ///  WHEN trying to cancel the booking

        Booking updatedBooking = bookingCalendarService.cancelBooking(bookingId).get();

        ///  THEN the booking is available and has not any player as an owner
        ///  the booking is not in reserved bookings of the calendar, but in the available list of bookings

        assertThat(updatedBooking.getBookingOwner()).isNull();
        assertThat(updatedBooking.getCalendar().getReservedBookings().size()).isEqualTo(0);
        assertThat(updatedBooking.getCalendar().getAvailableBookings().size()).isEqualTo(MAX_NUM_OF_SLOTS_PER_WEEK);
    }

    @Test
    public void shouldNotCancelAnAvailableBooking() {
        ///  GIVEN an available booking
        BookingCalendar calendar = bookingCalendarService.createBookingCalendar(TODAY);
        Long calendarId = calendar.getId();
        Booking availableBooking = calendar.getAvailableBookings().get(0);
        Long bookingId = availableBooking.getId();

        ///  WHEN trying to cancel the booking
        Optional<Booking> updatedBooking = bookingCalendarService.cancelBooking(bookingId);

        ///  THEN return the same booking without any change.
        assertThat(updatedBooking).isEmpty();
    }

    @Test
    public void shouldNotCancelAnNonExistingBooking() {

        ///  WHEN trying to cancel un non existing booking
        Optional<Booking> updatedBooking = bookingCalendarService.cancelBooking(100L);

        ///  THEN return and Optional empty.
        assertThat(updatedBooking).isEmpty();
    }

    @Test
    public void shouldDeleteBookingById_withoutReservation() {
        ///  GIVEN a persisted calendar with available bookings
        BookingCalendar calendar = bookingCalendarService.createBookingCalendar(TODAY);
        Long calendarId = calendar.getId();
        Booking availableBooking = calendar.getAvailableBookings().get(0);
        Long bookingId = availableBooking.getId();
        assertThat(calendar.getAvailableBookings().size()).isEqualTo(MAX_NUM_OF_SLOTS_PER_WEEK);

        ///  WHEN deleting an available booking by its id
        bookingService.deleteBookingById(bookingId);

        ///  THEN the booking is no longer in the database and it is no longer in the list of available bookings of the calendar
        Optional<Booking> deletedBooking = bookingRepository.findById(bookingId);
        assertThat(deletedBooking).isEmpty();
        calendar.getAvailableBookings().size();
        assertThat(calendar.getAvailableBookings().get(0).getId()).isNotEqualTo(calendarId);
    }

    @Test
    public void shouldDeleteBookingById_withReservation() {
        ///  GIVEN a persisted calendar with available bookings and a reserved booking
        BookingCalendar calendar = bookingCalendarService.createBookingCalendar(TODAY);
        Long calendarId = calendar.getId();
        Residence residence = residenceService.saveResidence(createResidence());
        Player player = playerService.savePlayer(createPlayer(residence));
        Booking reservedBooking = bookingCalendarService.reserveBooking(calendar.getAvailableBookings().get(0).getId(), playerMapper.toDTO(player)).get();
        Long reservedBookingId = reservedBooking.getId();
        assertThat(calendar.getAvailableBookings().size()).isEqualTo(MAX_NUM_OF_SLOTS_PER_WEEK -1);
        assertThat(calendar.getReservedBookings().size()).isEqualTo(1);

        ///  WHEN deleting an available booking by its id
        bookingService.deleteBookingById(reservedBookingId);

        ///  THEN the booking is no longer in the database and it is no longer in the list of reserved bookings of the calendar

        // Forzar la recarga desde la BD

        entityManager.flush();
        entityManager.clear();

        Optional<Booking> deletedBooking = bookingRepository.findById(reservedBookingId);
        log.info("\n ********* ");
        log.info(deletedBooking.toString());
        assertThat(deletedBooking).isEmpty();

        BookingCalendar updatedCalendar = bookingCalendarRepository.findById(calendarId).get();
        int result = updatedCalendar.getReservedBookings().size();
        assertThat(result).isEqualTo(0);
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
