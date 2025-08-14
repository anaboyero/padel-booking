package ana.learning.padel.padelBooking.service;

import ana.learning.padel.padelBooking.model.Booking;
import ana.learning.padel.padelBooking.model.BookingCalendar;
import ana.learning.padel.padelBooking.model.Player;
import ana.learning.padel.padelBooking.model.Residence;
import ana.learning.padel.padelBooking.repository.BookingCalendarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookingCalendarServiceTests {

    private static final String NAME_OF_PLAYER1 = "Ana";
    private static final String NAME_OF_PLAYER2 = "Javier";
    private static final Booking.TimeSlot SLOT = Booking.TimeSlot.TWO_PM;
    private static final LocalDate TODAY = LocalDate.now();
    Player player1;
    Player player2;
    Booking tentativeBooking;
    BookingCalendar bookingCalendar;
    BookingCalendar bookingCalendarToSave;

    private static final Logger log = LoggerFactory.getLogger(BookingCalendarServiceTests.class);

    @Mock
    private BookingCalendarRepository bookingCalendarRepository;

    // Lo mockeo para usarlo en el constructor de BookingCalendarService.
    // Autowired no sirve, por que???
    @Mock
    private BookingService bookingService;

    @InjectMocks
    private BookingCalendarService bookingCalendarService = new BookingCalendarServiceImpl(bookingCalendarRepository, bookingService);

    @BeforeEach
    public void setUp(){
        player1 = new Player();
        player1.setName(NAME_OF_PLAYER1);
        player1.setResidence(new Residence());

        player2 = new Player();
        player2.setName(NAME_OF_PLAYER2);
        player2.setResidence(new Residence());

        bookingCalendar = new BookingCalendar();
        bookingCalendar.setStartDay(TODAY);

        tentativeBooking = new Booking();
        tentativeBooking.setBookingDate(TODAY);
        tentativeBooking.setTimeSlot(SLOT);
        tentativeBooking.setBookingOwner(player1);
    }

    @Test
    public void shouldCreateANewCalendarWithAProperDate(){
        BookingCalendar bookingCalendarToSave = new BookingCalendar();
        bookingCalendarToSave.setStartDay(TODAY);
        bookingCalendarToSave.setId(1L);

        when(bookingCalendarRepository.save(any(BookingCalendar.class))).thenReturn(bookingCalendarToSave);

        bookingCalendar = bookingCalendarService.saveBookingCalendar(bookingCalendar);

        assertThat(bookingCalendar.getStartDay()).isEqualTo(TODAY);
        assertThat(bookingCalendar.getId()).isEqualTo(1L);
        verify(bookingCalendarRepository).save(any(BookingCalendar.class));
    }

    @Test
    public void shouldPersistABookingCalendar(){
        BookingCalendar pastBookingCalendar = new BookingCalendar();
        pastBookingCalendar.setStartDay(TODAY.minusDays(30));
        bookingCalendarToSave = new BookingCalendar();
        bookingCalendarToSave.setStartDay(TODAY);
        bookingCalendarToSave.setId(1L);

        when(bookingCalendarRepository.save(any(BookingCalendar.class))).thenReturn(bookingCalendarToSave);

        bookingCalendar = bookingCalendarService.saveBookingCalendar(pastBookingCalendar);

        assertThat(bookingCalendar.getStartDay()).isEqualTo(TODAY);
        assertThat(bookingCalendar.getId()).isEqualTo(1L);
        verify(bookingCalendarRepository).save(any(BookingCalendar.class));
    }

    @Test
    public void shouldConfirmAnAvailableBooking(){
        assertThat(bookingCalendarService.isBookingAvailable(tentativeBooking, bookingCalendar)).isTrue();
    }

    @Test
    public void shouldNotConfirmAnUnavailableBooking(){
        Booking firstBooking = new Booking();
        firstBooking.setBookingDate(TODAY);
        firstBooking.setTimeSlot(SLOT);
        firstBooking.setBookingOwner(player2);

        bookingCalendarService.reserveBooking(firstBooking, bookingCalendar);
        assertThat(bookingCalendarService.isBookingAvailable(tentativeBooking, bookingCalendar)).isFalse();
    }

    @Test
    public void shouldReserveAnAvailableBooking(){
        assertThat(bookingCalendarService.isBookingAvailable(tentativeBooking, bookingCalendar)).isTrue();
    }

    @Test
    public void shouldNotReserveAnUnavailableBooking(){
        Booking firstBooking = new Booking();
        firstBooking.setBookingDate(TODAY);
        firstBooking.setTimeSlot(SLOT);
        firstBooking.setBookingOwner(player2);

        Optional<Booking> availableAttempt = bookingCalendarService.reserveBooking(firstBooking, bookingCalendar);
        Optional<Booking> unavailableAttempt = bookingCalendarService.reserveBooking(tentativeBooking, bookingCalendar);
        assertThat(availableAttempt.isEmpty()).isFalse();
        assertThat(unavailableAttempt.isEmpty()).isTrue();
    }

    @Test
    public void shouldNotReserveANotValidBooking(){
        Booking earlyBooking = new Booking();
        earlyBooking.setBookingDate(TODAY.minusDays(30));
        earlyBooking.setTimeSlot(SLOT);
//        player1.setResidence(new Residence());
        earlyBooking.setBookingOwner(player1);

        Booking noResidenceBooking = new Booking();
        noResidenceBooking.setBookingDate(TODAY);
        noResidenceBooking.setTimeSlot(SLOT);
        player1.setResidence(null);
        noResidenceBooking.setBookingOwner(player1);

        Optional<Booking> unavailableAttempt1 = bookingCalendarService.reserveBooking(earlyBooking, bookingCalendar);
        Optional<Booking> unavailableAttempt2 = bookingCalendarService.reserveBooking(noResidenceBooking, bookingCalendar);

        assertThat(unavailableAttempt1.isEmpty()).isTrue();
        assertThat(unavailableAttempt2.isEmpty()).isTrue();

    }

//    @Test
//    public void shouldAddConfirmedBookingToPlayer(){
//        Optional<Booking> reservation = bookingCalendarService.reserveBooking(tentativeBooking, bookingCalendar);
//        assertThat(tentativeBooking.getBookingOwner().getBookings()).contains(tentativeBooking);
//    }

    @Test
    public void shouldPersistAConfirmedBooking(){
        BookingCalendar bookingCalendarToSave = new BookingCalendar();
        bookingCalendarToSave.setStartDay(TODAY);
        bookingCalendarToSave.setId(1L);
        bookingCalendarToSave.reserveBooking(tentativeBooking);

        when(bookingCalendarRepository.save(any(BookingCalendar.class))).thenReturn(bookingCalendarToSave);

        bookingCalendar = bookingCalendarService.saveBookingCalendar(bookingCalendar);

        assertThat(bookingCalendar.getStartDay()).isEqualTo(TODAY);
        assertThat(bookingCalendar.getId()).isEqualTo(1L);
        verify(bookingCalendarRepository).save(any(BookingCalendar.class));
    }


}
