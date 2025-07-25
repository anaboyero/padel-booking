//package ana.learning.padel.padelBooking.service;
//
//import ana.learning.padel.padelBooking.domain.PadelBookingBookingCalendarTests;
//import ana.learning.padel.padelBooking.model.Booking;
//import ana.learning.padel.padelBooking.model.Player;
//import ana.learning.padel.padelBooking.model.Residence;
//import ana.learning.padel.padelBooking.repository.PlayerRepository;
//import ana.learning.padel.padelBooking.repository.ResidenceRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.MockitoJUnitRunner;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.time.LocalDate;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//
//
//@SpringBootTest
//@RunWith(MockitoJUnitRunner.class)
//public class BookingCalendarServiceTests {
//
//    private static final String NAME_OF_PLAYER1 = "Ana";
//    private static final String NAME_OF_PLAYER2 = "Javier";
//    private static final Residence.Building RESIDENCE_BUILDING_EMPECINADO21 = Residence.Building.JUAN_MARTIN_EMPECINADO_21;
//    private static final Residence.Floor RESIDENCE_5FLOOR = Residence.Floor.FIFTH_FLOOR;
//    private static final Residence.Letter RESIDENCE_LETTER_A = Residence.Letter.A;
//    private static final Residence.Building RESIDENCE_BUILDING_EMPECINADO25 = Residence.Building.JUAN_MARTIN_EMPECINADO_25;
//    private static final Residence.Floor RESIDENCE_2FLOOR = Residence.Floor.SECOND_FLOOR;
//    private static final Residence.Letter RESIDENCE_LETTER_B = Residence.Letter.B;
//    private static final Booking.TimeSlot SLOT = Booking.TimeSlot.NINE_AM;
//    private static final LocalDate TODAY = LocalDate.now();
//    private static final int MAX_NUM_OF_SLOTS_PER_WEEK = 13*7;
//    Player player;
//    Residence mockedResidence;
//    Residence residence;
//
//    private static final Logger log = LoggerFactory.getLogger(PadelBookingBookingCalendarTests.class);
//
//    @Mock
//    private ResidenceRepository residenceRepository;
//
//    @InjectMocks
//    private ResidenceService residenceService = new ResidenceServiceImpl();
//
//    @Mock
//    private PlayerRepository playerRepository;
//
//    @InjectMocks
//    private PlayerService playerService = new PlayerServiceImpl();
//
//    @BeforeEach
//    public void setUp(){
//
//        Residence residence = new Residence();
//        residence.setBuilding(RESIDENCE_BUILDING_EMPECINADO25);
//        residence.setFloor(RESIDENCE_5FLOOR);
//        residence.setLetter(RESIDENCE_LETTER_A);
//
////
////        Player player = new Player();
////        player.setName(NAME_OF_PLAYER1);
////        player.setResidence(mockedResidence);
////        player = playerService.savePlayer(player);
//    }
//
//    @Test
//    public void shouldMockAResidenceRepository(){
//
//        Residence newResidence = new Residence();
//        log.info(newResidence.toString());
//
//        when(residenceRepository.save(any(Residence.class))).thenReturn(mockedResidence);
//
//        newResidence = residenceService.saveResidence(newResidence);
//        log.info(newResidence.toString());
////        assertEquals(RESIDENCE_BUILDING_EMPECINADO25, newResidence.getBuilding());
////        assertEquals(RESIDENCE_5FLOOR, newResidence.getFloor());
////        assertEquals(RESIDENCE_LETTER_A, newResidence.getLetter());
////        verify(residenceRepository).save(residence);
//    }
//}
