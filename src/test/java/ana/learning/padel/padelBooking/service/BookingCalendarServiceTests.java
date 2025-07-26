package ana.learning.padel.padelBooking.service;

import ana.learning.padel.padelBooking.domain.PadelBookingBookingCalendarTests;
import ana.learning.padel.padelBooking.model.Booking;
import ana.learning.padel.padelBooking.model.BookingCalendar;
import ana.learning.padel.padelBooking.model.Player;
import ana.learning.padel.padelBooking.model.Residence;
import ana.learning.padel.padelBooking.repository.BookingCalendarRepository;
import ana.learning.padel.padelBooking.repository.PlayerRepository;
import ana.learning.padel.padelBooking.repository.ResidenceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;



//@SpringBootTest
@ExtendWith(MockitoExtension.class)
//@RunWith(MockitoJUnitRunner.class)
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
    Player player;
    Residence residenceToSave;
    Booking tentativeBooking;
    BookingCalendar bookingCalendar;

    private static final Logger log = LoggerFactory.getLogger(BookingCalendarServiceTests.class);

    @Mock
    private ResidenceRepository residenceRepository;

    @InjectMocks
    private ResidenceService residenceService = new ResidenceServiceImpl();

    @Mock
    private BookingCalendarRepository bookingCalendarRepository;

    @InjectMocks
    private BookingCalendarService bookingCalendarService = new BookingCalendarServiceImpl();

//    @Mock
//    private PlayerRepository playerRepository;
//
//    @InjectMocks
//    private PlayerService playerService = new PlayerServiceImpl();

    @BeforeEach
    public void setUp(){

        tentativeBooking = new Booking();
        tentativeBooking.setBookingDate(TODAY);
        tentativeBooking.setTimeSlot(SLOT);
        tentativeBooking.setBookingOwner(player);

//
//        Player player = new Player();
//        player.setName(NAME_OF_PLAYER1);
//        player.setResidence(mockedResidence);
//        player = playerService.savePlayer(player);
    }



    @Test
    public void shouldConfirmAnAvailableBooking(){
        bookingCalendar = new BookingCalendar();
        bookingCalendar.setStartDay(TODAY);
        Boolean isAvailable = bookingCalendarService.isBookingAvailable(tentativeBooking, bookingCalendar);
        assertThat(isAvailable).isTrue();
    }
}
