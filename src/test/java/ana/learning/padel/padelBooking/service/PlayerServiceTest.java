package ana.learning.padel.padelBooking.service;

import ana.learning.padel.padelBooking.model.Booking;
import ana.learning.padel.padelBooking.model.Player;
import ana.learning.padel.padelBooking.model.Residence;
import ana.learning.padel.padelBooking.repository.BookingRepository;
import ana.learning.padel.padelBooking.repository.PlayerRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PlayerServiceTest {

    private static final Residence.Building RESIDENCE_BUILDING_EMPECINADO_21 = Residence.Building.JUAN_MARTIN_EMPECINADO_21;
    private static final Residence.Building RESIDENCE_BUILDING_EMPECINADO_23 = Residence.Building.JUAN_MARTIN_EMPECINADO_23;
    private static final Residence.Floor RESIDENCE_5FLOOR = Residence.Floor.FIFTH;
    private static final Residence.Letter RESIDENCE_LETTER_A = Residence.Letter.A;
    private static final Booking.TimeSlot SLOT = Booking.TimeSlot.TWO_PM;
    private static final LocalDate TODAY = LocalDate.now();
    private static final LocalDate TOMORROW = LocalDate.now().plusDays(1);
    private static final LocalDate YESTERDAY = LocalDate.now().minusDays(1);

    private static final Logger log = LoggerFactory.getLogger(PlayerServiceTest.class);

    @Mock
    private PlayerRepository playerRepository;

    @InjectMocks
    private PlayerService playerService = new PlayerServiceImpl();

    @Mock
    private BookingService bookingService;

    private Player player0;
    private Player player1;

    public PlayerServiceTest() {

    }

    @BeforeEach
    public void setUp() {
        player1 = new Player();
        player1.setName("Javier");
        player1.setId(1L);
    }

    @Test
    public void shouldSaveANewPlayer() {

        player0 = new Player();
        player0.setName("Ana");
        player0.setId(null);

        when(playerRepository.save(any(Player.class))).thenReturn(player1);
        Player testPlayer = playerService.savePlayer(player0);
        assertThat(testPlayer.getId()).isEqualTo(1L);
        assertThat(testPlayer.getName()).isEqualTo("Javier");
    }

    @Disabled("Estos metodos me los estoy replanteando")
    @Test
    public void shouldAddBookingToPlayer(){

        ///  GIVEN a player with no bookings
        player1 = new Player();
        player1.setName("Ana");
        player1.setId(10L);

        Booking booking = new Booking();
        booking.setBookingDate(TOMORROW);
        booking.setTimeSlot(SLOT);
        booking.setId(20L);

        when(playerRepository.findById(10L)).thenReturn(Optional.of(player1));
        when(bookingService.getBookingById(20L)).thenReturn(Optional.of(booking));

        assertThat(playerService.getPlayerById(10L).get().getBookings().size()).isEqualTo(0);
        assertThat(bookingService.getBookingById(20L).get().getBookingOwner()).isNull();

        ///  WHEN adding a valid booking to that player
        playerService.addBookingToPlayer(player1, booking);

        ///  THEN the player has one booking and the booking has an owner

        assertThat(playerService.getPlayerById(10L).get().getBookings().size()).isEqualTo(1);
        assertThat(bookingService.getBookingById(20L).get().getBookingOwner()).isNotNull();

    }

    @Test
    public void shouldNotAddBookingToPlayer_WhenBookingIsNotPersisted(){

        ///  GIVEN a player with no bookings and a non persisted booking
        player1 = new Player();
        player1.setName("Ana");
        player1.setId(10L);
        Booking booking = new Booking();
        booking.setBookingDate(TOMORROW);
        booking.setTimeSlot(SLOT);
        when(playerRepository.findById(10L)).thenReturn(Optional.of(player1));
        assertThat(playerService.getPlayerById(10L).get().getBookings().size()).isEqualTo(0);
        assertThat(bookingService.getAllBookings().size()).isEqualTo(0);

        ///  WHEN adding a non persisted booking to that player
        playerService.addBookingToPlayer(player1, booking);

        ///  THEN the player has no bookings and the booking has no owner
        assertThat(playerService.getPlayerById(10L).get().getBookings().size()).isEqualTo(0);
        assertThat(bookingService.getAllBookings().size()).isEqualTo(0);
    }

    private Player createPersistedPlayer(String name, Long id) {
        Player player1 = new Player();
        player1.setName(name);
        player1.setId(id);
        return player1;
    }


    @Disabled("Estos metodos me los estoy replanteando")
    @Test
    public void shouldNotAddBookingToPlayer_WhenBookingIsOwned(){

        ///  GIVEN a player with no bookings and a non persisted booking

        Player player1 = createPersistedPlayer("Ana", 10L);
        Player player2 = createPersistedPlayer("Pepe", 11L);
        Booking booking = createPersistedBooking(20L);
        playerService.addBookingToPlayer(player2, booking);

        when(playerRepository.findById(10L)).thenReturn(Optional.of(player1));
        when(playerRepository.findById(11L)).thenReturn(Optional.of(player2));
        when(bookingService.getBookingById(20L)).thenReturn(Optional.of(booking));

        assertThat(player1.getBookings().size()).isEqualTo(0);
        assertThat(player2.getBookings().size()).isEqualTo(1);
        assertThat(booking.getBookingOwner().getName()).isEqualTo("Pepe");

        ///  WHEN trying to add an owned booking to a player
        playerService.addBookingToPlayer(player1, booking);

        ///  THEN the player does not own the booking and the booking has the same owner as before
        assertThat(playerService.getPlayerById(10L).get().getBookings().size()).isEqualTo(0);
        assertThat(playerService.getPlayerById(11L).get().getBookings().size()).isEqualTo(1);
        assertThat(bookingService.getBookingById(20L).get().getBookingOwner().getName()).isEqualTo("Pepe");
    }

    private Booking createPersistedBooking(Long id) {
        Booking booking = new Booking();
        booking.setBookingDate(TOMORROW);
        booking.setTimeSlot(SLOT);
        booking.setId(id);
        return booking;
    }
}


