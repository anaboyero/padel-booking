package ana.learning.padel.padelBooking.service;

import ana.learning.padel.padelBooking.model.Booking;
import ana.learning.padel.padelBooking.model.BookingCalendar;
import ana.learning.padel.padelBooking.model.Player;
import ana.learning.padel.padelBooking.model.Residence;
import ana.learning.padel.padelBooking.repository.BookingCalendarRepository;
import ana.learning.padel.padelBooking.repository.BookingRepository;
import ana.learning.padel.padelBooking.repository.PlayerRepository;
import ana.learning.padel.padelBooking.repository.ResidenceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.LocalDate;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookingCalendarServiceTests {

    private static final String NAME_OF_PLAYER1 = "Ana";
    private static final String NAME_OF_PLAYER2 = "Javier";
    private static final Residence.Building RESIDENCE_BUILDING_EMPECINADO21 = Residence.Building.JUAN_MARTIN_EMPECINADO_21;
    private static final Residence.Floor RESIDENCE_5FLOOR = Residence.Floor.FIFTH_FLOOR;
    private static final Residence.Letter RESIDENCE_LETTER_A = Residence.Letter.A;
    private static final Residence.Building RESIDENCE_BUILDING_EMPECINADO25 = Residence.Building.JUAN_MARTIN_EMPECINADO_25;
    private static final Residence.Floor RESIDENCE_2FLOOR = Residence.Floor.SECOND_FLOOR;
    private static final Residence.Letter RESIDENCE_LETTER_B = Residence.Letter.B;
    private static final Booking.TimeSlot SLOT = Booking.TimeSlot.TWO_PM;
    private static final LocalDate TODAY = LocalDate.now();
    private static final int MAX_NUM_OF_SLOTS_PER_WEEK = 4*7;
    Player player1;
    Player player2;
    Booking tentativeBooking;
    BookingCalendar bookingCalendar;
    BookingCalendar bookingCalendarToSave;

    private static final Logger log = LoggerFactory.getLogger(BookingCalendarServiceTests.class);

    @Mock
    private ResidenceRepository residenceRepository;

    @InjectMocks
    private ResidenceService residenceService = new ResidenceServiceImpl();

    @Mock
    private BookingCalendarRepository bookingCalendarRepository;

    @InjectMocks
    private BookingCalendarService bookingCalendarService = new BookingCalendarServiceImpl();

    @Mock
    private BookingRepository bookingRepository;

    @InjectMocks
    private BookingService bookingService = new BookingServiceImpl();

    @Mock
    private PlayerRepository playerRepository;

    @InjectMocks
    private PlayerService playerService = new PlayerServiceImpl();

    @BeforeEach
    public void setUp(){

        player1 = new Player();
        player1.setName(NAME_OF_PLAYER1);
        player2 = new Player();
        player2.setName(NAME_OF_PLAYER2);

        bookingCalendar = new BookingCalendar();
        bookingCalendar.setStartDay(TODAY);
    }

    @Test
    public void shouldPersistABookingCalendar(){

        BookingCalendar pastBookingCalendar = new BookingCalendar();
        pastBookingCalendar.setStartDay(TODAY.minusDays(30));
        bookingCalendarToSave = new BookingCalendar();
        bookingCalendarToSave.setStartDay(TODAY);

        when(bookingCalendarRepository.save(any(BookingCalendar.class))).thenReturn(bookingCalendarToSave);

        bookingCalendar = bookingCalendarService.saveBookingCalendar(pastBookingCalendar);

        assertThat(bookingCalendar.getStartDay()).isEqualTo(TODAY);
        verify(bookingCalendarRepository).save(any(BookingCalendar.class));


//        when(residenceRepository.save(any(Residence.class))).thenReturn(residenceToSave);
//
//        Residence newResidence = residenceService.saveResidence(new Residence());
////
//        assertEquals(RESIDENCE_BUILDING_EMPECINADO25, newResidence.getBuilding());
//        assertEquals(RESIDENCE_5FLOOR, newResidence.getFloor());
//        assertEquals(RESIDENCE_LETTER_A, newResidence.getLetter());
//        verify(residenceRepository).save(any(Residence.class));






    }

    @Test
    public void shouldConfirmAnAvailableBooking(){

        tentativeBooking = new Booking();
        tentativeBooking.setBookingDate(TODAY);
        tentativeBooking.setTimeSlot(SLOT);
        tentativeBooking.setBookingOwner(player1);
        log.info("****Veamos el concenido de tentativeBooking" + tentativeBooking.toString());

        Boolean result = bookingCalendarService.isBookingAvailable(tentativeBooking, bookingCalendar);
        assertThat(result).isTrue();
    }

    @Test
    public void shouldNotConfirmAnUnavailableBooking(){

        Booking firstBooking = new Booking();
        firstBooking.setBookingDate(TODAY);
        firstBooking.setTimeSlot(SLOT);
        firstBooking.setBookingOwner(player1);

        tentativeBooking = new Booking();
        tentativeBooking.setBookingDate(TODAY);
        tentativeBooking.setTimeSlot(SLOT);
        tentativeBooking.setBookingOwner(player2);

        bookingCalendarService.reserveBooking(firstBooking, bookingCalendar);
        Boolean result = bookingCalendarService.isBookingAvailable(tentativeBooking, bookingCalendar);
        assertThat(result).isFalse();
    }

    @Test
    public void shouldReserveAnAvailableBooking(){

        tentativeBooking = new Booking();
        tentativeBooking.setBookingDate(TODAY);
        tentativeBooking.setTimeSlot(SLOT);
        tentativeBooking.setBookingOwner(player1);

        Boolean result = bookingCalendarService.isBookingAvailable(tentativeBooking, bookingCalendar);
        assertThat(result).isTrue();
    }

    @Test
    public void shouldNotReserveAnUnavailableBooking(){

        Booking firstBooking = new Booking();
        firstBooking.setBookingDate(TODAY);
        firstBooking.setTimeSlot(SLOT);
        firstBooking.setBookingOwner(player1);

        tentativeBooking = new Booking();
        tentativeBooking.setBookingDate(TODAY);
        tentativeBooking.setTimeSlot(SLOT);
        tentativeBooking.setBookingOwner(player2);

        Optional<Booking> availableAttempt = bookingCalendarService.reserveBooking(firstBooking, bookingCalendar);
        Optional<Booking> unavailableAttempt = bookingCalendarService.reserveBooking(tentativeBooking, bookingCalendar);
        assertThat(availableAttempt.isEmpty()).isFalse();
        assertThat(unavailableAttempt.isEmpty()).isTrue();

    }
}
