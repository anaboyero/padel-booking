package ana.learning.padel.padelBooking.service;

import ana.learning.padel.padelBooking.exceptions.PastDateException;
import ana.learning.padel.padelBooking.model.Booking;
import ana.learning.padel.padelBooking.model.BookingCalendar;
import ana.learning.padel.padelBooking.model.Player;
import ana.learning.padel.padelBooking.model.Residence;
import ana.learning.padel.padelBooking.repository.BookingCalendarRepository;
import ana.learning.padel.padelBooking.repository.BookingRepository;
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
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
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

    @Mock
    private BookingCalendarRepository bookingCalendarRepository;
    @Mock
    private BookingRepository bookingRepository;

    // Lo mockeo para usarlo en el constructor de BookingCalendarService.

    @Mock
    private BookingService bookingService;

    @InjectMocks
    private BookingCalendarService bookingCalendarService = new BookingCalendarServiceImpl(bookingCalendarRepository, bookingService);

    @BeforeEach
    public void setUp(){

        Residence residence = new Residence();
        residence.setBuilding(RESIDENCE_BUILDING_EMPECINADO21);
        residence.setFloor(RESIDENCE_5FLOOR);
        residence.setLetter(RESIDENCE_LETTER_A);
        residence.setId(15L);

        player1 = new Player();
        player1.setName(NAME_OF_PLAYER1);
        player1.setResidence(residence);

        player2 = new Player();
        player2.setName(NAME_OF_PLAYER2);
        player2.setResidence(residence);

        bookingCalendar = new BookingCalendar();
        bookingCalendar.setStartDay(TODAY);

        tentativeBooking = new Booking();
        tentativeBooking.setBookingDate(TODAY);
        tentativeBooking.setTimeSlot(SLOT);
        tentativeBooking.setBookingOwner(player1);
    }

//    @Test
//    @DisplayName("****** Given a date, should create a new Calendar")
//    public void shouldCreateANewBookingCalendar_GivenADate() {
//
//        /// GIVEN A non-initialized calendar
//        BookingCalendar calendar = new BookingCalendar();
//        assertThat((calendar.getReservedBookings()).size()).isEqualTo(0);
//        assertThat((calendar.getAvailableBookings()).size()).isEqualTo(0);
//
//        ///  WHEN asigning the start date
//        calendar.setStartDay(TODAY);
//
//        ///  THEN IT CREATES THE BOOKINGS
//        assertThat((calendar.getReservedBookings()).size()).isEqualTo(0);
//        assertThat((calendar.getAvailableBookings()).size()).isEqualTo(MAX_NUM_OF_SLOTS_PER_WEEK);
//    }

    @Test
    public void shouldCreateANewCalendarWithAProperDate(){

        ///  GIVEN A START DATE
        LocalDate start = TODAY;
        BookingCalendar bookingCalendarToSave = new BookingCalendar();
        bookingCalendarToSave.setId(25L);
        Booking bookingToSave = new Booking();
        bookingToSave.setId(100L);
        bookingToSave.setBookingDate(TODAY);

        when(bookingCalendarRepository.save(any(BookingCalendar.class))).thenReturn(bookingCalendarToSave);
        when(bookingService.saveBooking(any(Booking.class))).thenReturn(bookingToSave);

        ///  WHEN creating a new calendar with a start day

        BookingCalendar calendar = bookingCalendarService.createBookingCalendar(start);

        ///  THEN RETURN A PERSISTED CALENDAR WITH PERSISTED AVAILABLE BOOKINGS

        assertThat(calendar.getAvailableBookings().size()).isEqualTo(MAX_NUM_OF_SLOTS_PER_WEEK);
        assertThat(calendar.getAvailableBookings().get(0).getId()).isNotNull();
    }

    @Test
    public void shouldNotCreateANewCalendarWithAnUnvalidDate(){

        ///  GIVEN A START DATE
        LocalDate yesterday = TODAY.minusDays(1);

        ///  Throws an exception WHEN trying to create a new calendar with a start day in the past

        // Ejecutamos el metodo problemático dentro de un lambda (la función anónima)
        // El assertThrows ejecuta el código del lambda, espera que salte la excepción
        // y la captura para verificar si es del tipo correcto.

        assertThrows(PastDateException.class, () -> bookingCalendarService.createBookingCalendar(yesterday));
    }




//    @Test
//    public void shouldPersistABookingCalendar(){
//        BookingCalendar pastBookingCalendar = new BookingCalendar();
//        pastBookingCalendar.setStartDay(TODAY.minusDays(30));
//        bookingCalendarToSave = new BookingCalendar();
//        bookingCalendarToSave.setStartDay(TODAY);
//        bookingCalendarToSave.setId(1L);
//
//        when(bookingCalendarRepository.save(any(BookingCalendar.class))).thenReturn(bookingCalendarToSave);
//
//        bookingCalendar = bookingCalendarService.saveBookingCalendar(pastBookingCalendar);
//
//        assertThat(bookingCalendar.getStartDay()).isEqualTo(TODAY);
//        assertThat(bookingCalendar.getId()).isEqualTo(1L);
//        verify(bookingCalendarRepository).save(any(BookingCalendar.class));
//    }
//
//    @Test
//    public void shouldConfirmAnAvailableBooking(){
//        assertThat(bookingCalendarService.isBookingAvailable(tentativeBooking, bookingCalendar)).isTrue();
//    }
//
//    @Test
//    public void shouldNotConfirmAnUnavailableBooking(){
//        Booking firstBooking = new Booking();
//        firstBooking.setBookingDate(TODAY);
//        firstBooking.setTimeSlot(SLOT);
//        firstBooking.setBookingOwner(player2);
//
//        bookingCalendarService.confirmBooking(firstBooking, bookingCalendar);
//        assertThat(bookingCalendarService.isBookingAvailable(tentativeBooking, bookingCalendar)).isFalse();
//    }
//
//    @Test
//    public void shouldReserveAnAvailableBooking(){
//        assertThat(bookingCalendarService.isBookingAvailable(tentativeBooking, bookingCalendar)).isTrue();
//    }
//
//    @Test
//    public void shouldNotReserveAnUnavailableBooking(){
//        Booking firstBooking = new Booking();
//        firstBooking.setBookingDate(TODAY);
//        firstBooking.setTimeSlot(SLOT);
//        firstBooking.setBookingOwner(player2);
//
//        Optional<Booking> availableAttempt = bookingCalendarService.confirmBooking(firstBooking, bookingCalendar);
//        Optional<Booking> unavailableAttempt = bookingCalendarService.confirmBooking(tentativeBooking, bookingCalendar);
//        assertThat(availableAttempt.isEmpty()).isFalse();
//        assertThat(unavailableAttempt.isEmpty()).isTrue();
//    }
//
//    @Test
//    public void shouldNotReserveANotValidBooking(){
//        Booking earlyBooking = new Booking();
//        earlyBooking.setBookingDate(TODAY.minusDays(30));
//        earlyBooking.setTimeSlot(SLOT);
//        player1.setResidence(new Residence());
//        earlyBooking.setBookingOwner(player1);

//        Booking noResidenceBooking = new Booking();
//        noResidenceBooking.setBookingDate(TODAY);
//        noResidenceBooking.setTimeSlot(SLOT);
//        player1.setResidence(null);
//        noResidenceBooking.setBookingOwner(player1);

//        Optional<Booking> unavailableAttempt1 = bookingCalendarService.confirmBooking(earlyBooking, bookingCalendar);
//        Optional<Booking> unavailableAttempt2 = bookingCalendarService.reserveBooking(noResidenceBooking, bookingCalendar);

//        assertThat(unavailableAttempt1.isEmpty()).isTrue();
//        assertThat(unavailableAttempt2.isEmpty()).isTrue();

//    }

//    @Test
//    public void shouldAddConfirmedBookingToPlayer(){
//        Optional<Booking> reservation = bookingCalendarService.reserveBooking(tentativeBooking, bookingCalendar);
//        assertThat(tentativeBooking.getBookingOwner().getBookings()).contains(tentativeBooking);
//    }

//    @Test
//    public void shouldPersistAConfirmedBooking(){
//        BookingCalendar bookingCalendarToSave = new BookingCalendar();
//        bookingCalendarToSave.setStartDay(TODAY);
//        bookingCalendarToSave.setId(1L);
//        bookingCalendarToSave.reserveBooking(tentativeBooking);
//
//        when(bookingCalendarRepository.save(any(BookingCalendar.class))).thenReturn(bookingCalendarToSave);
//
//        bookingCalendar = bookingCalendarService.saveBookingCalendar(bookingCalendar);
//
//        assertThat(bookingCalendar.getStartDay()).isEqualTo(TODAY);
//        assertThat(bookingCalendar.getId()).isEqualTo(1L);
//        verify(bookingCalendarRepository).save(any(BookingCalendar.class));
//    }


}
