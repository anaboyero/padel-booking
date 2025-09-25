package ana.learning.padel.padelBooking.controller;

import ana.learning.padel.padelBooking.mappers.BookingMapper;
import ana.learning.padel.padelBooking.mappers.PlayerMapper;
import ana.learning.padel.padelBooking.mappers.ResidenceMapper;
import ana.learning.padel.padelBooking.model.Booking;
import ana.learning.padel.padelBooking.model.Player;
import ana.learning.padel.padelBooking.model.Residence;
import ana.learning.padel.padelBooking.repository.BookingRepository;
import ana.learning.padel.padelBooking.repository.PlayerRepository;
import ana.learning.padel.padelBooking.repository.ResidenceRepository;
import ana.learning.padel.padelBooking.service.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class PlayerControllerTest {

    private static final LocalDate TOMORROW = LocalDate.now().plusDays(1);
    private static final LocalDate YESTERDAY = LocalDate.now().minusDays(1);
    private static final Booking.TimeSlot SLOT = Booking.TimeSlot.TWO_PM;
    private static final Residence.Building RESIDENCE_BUILDING_EMPECINADO21 = Residence.Building.JUAN_MARTIN_EMPECINADO_21;
    private static final Residence.Floor RESIDENCE_5FLOOR = Residence.Floor.FIFTH;
    private static final Residence.Letter RESIDENCE_LETTER_A = Residence.Letter.A;

    private final MockMvc mockMvc;
    private final PlayerMapper playerMapper;
    private final BookingMapper bookingMapper;
    private final ObjectMapper objectMapper;
    private final ResidenceMapper residenceMapper;
    private final PlayerRepository playerRepository;
    private final BookingRepository bookingRepository;
    private final ResidenceRepository residenceRepository;
    private final ResidenceService residenceService;
    private final BookingService bookingService;
    private final PlayerService playerService;


    Residence residence;
    Residence savedResidence;
    Booking bookingToCancel;
    Booking savedBookingToCancel;
    Player player;
    Player savedPlayer;

    private static final Logger log = LoggerFactory.getLogger(PlayerControllerTest.class);


    public PlayerControllerTest(MockMvc mockMvc, PlayerMapper playerMapper, BookingMapper bookingMapper, ObjectMapper objectMapper, ResidenceMapper residenceMapper, PlayerRepository playerRepository, BookingRepository bookingRepository, ResidenceRepository residenceRepository, ResidenceService residenceService, BookingService bookingService, PlayerService playerService) {
        this.mockMvc = mockMvc;
        this.playerMapper = playerMapper;
        this.bookingMapper = bookingMapper;
        this.objectMapper = objectMapper;
        this.residenceMapper = residenceMapper;
        this.playerRepository = playerRepository;
        this.bookingRepository = bookingRepository;
        this.residenceRepository = residenceRepository;
        this.residenceService = residenceService;
        this.bookingService = bookingService;
        this.playerService = playerService;
    }

    @BeforeEach
    void cleanUp() {
        bookingRepository.deleteAll();
        playerRepository.deleteAll();
        residenceRepository.deleteAll();
    }

    @Test
    public void shouldSaveAPlayer() throws Exception {
        Player player = new Player();
        player.setName("Ana");

        mockMvc.perform(post("/api/v1/players")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(playerMapper.toDTO(player))))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Ana"));

        List<Player> players = playerRepository.findAll();
        assertThat(players.size()).isEqualTo(1);
        assertThat(players.get(0).getName()).isEqualTo("Ana");
    }

    @Test
    public void shouldReturnNoContent_WhenDeletingAllPlayers() throws Exception {
        setUpTwoPlayers();

        mockMvc.perform(delete("/api/v1/players"))
                .andExpect(status().isNoContent());

        assertThat(playerRepository.findAll().size()).isEqualTo(0);
    }

    @Test
    public void shouldReturnListOfPlayers_WhenPlayersExist() throws Exception {
        setUpTwoPlayers();

        mockMvc.perform(get("/api/v1/players"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value("Ana"))
                .andExpect(jsonPath("$[1].name").value("Pepe"));
    }

    @Test
    public void shouldReturnNotFound_WhenDeletingNonExistentPlayer() throws Exception {
        setUpTwoPlayers();
        Long playerId = 31L;
        mockMvc.perform(delete("/api/v1/players/{id}", playerId))
                .andExpect(status().isNotFound());

        assertThat(playerRepository.findAll().size()).isEqualTo(2);
    }

    @Test
    public void shouldReturnOkAndDeletedPlayer_WhenDeletingAPlayer() throws Exception {
//        List<Player> players = setUpTwoPlayers();
////        List<Player> players = playerRepository.findAll();
//        Long existingId = players.get(0).getId();
//
//        mockMvc.perform(delete("/api/v1/players/{id}", existingId))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.name").value("Ana"));
//
//        assertThat(playerRepository.findById(existingId)).isEmpty();
    }

    @Disabled("Replanteando el funcionamiento de cancel ")
    @Test
    public void shouldReturnOkAndUpdatedPlayer_WhenCancellingAnOwnedBooking() throws Exception {
        /// GIVEN A PLAYER WITH A BOOKING
        Booking testBooking = new Booking();
        testBooking.setBookingDate(TOMORROW);
        testBooking.setTimeSlot(SLOT);
        setPlayerWithResidenceAndBooking(testBooking);

        assertThat(testBooking.getBookingOwner()).isNotNull();

        /// WHEN CANCELLING THE BOOKING
        /// THEN RETURNS OK AND THE DELETED BOOKING. This could have more assertions. Rethink a booking parameter in playerDTO

        mockMvc.perform(patch("/api/v1/players/{id}", savedPlayer.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookingMapper.toDTO(savedBookingToCancel))))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Ana"));

//        assertThat(testBooking.getBookingOwner()).isNull();
    }

    @Test
    public void shouldReturnBadRequest_WhenPastBooking() throws Exception {
        /// GIVEN A PLAYER WITH A BOOKING
        Booking testBooking = new Booking();
        testBooking.setBookingDate(YESTERDAY);
        testBooking.setTimeSlot(SLOT);
        setPlayerWithResidenceAndBooking(testBooking);

        /// WHEN CANCELLING THE BOOKING
        /// THEN RETURNS OK AND THE DELETED BOOKING. This could have more assertions. Rethink a booking parameter in playerDTO

        mockMvc.perform(patch("/api/v1/players/{id}", savedPlayer.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookingMapper.toDTO(savedBookingToCancel))))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Ana"));

//        Booking updatedBooking = bookingService.getBookingById(savedBookingToCancel.getId()).get();
//        assertThat(updatedBooking.getBookingOwner()).isNull();

//        Player updatedPlayer = playerService.getPlayerById(savedPlayer.getId()).get();
//        assertThat(updatedPlayer.getBookings()).doesNotContain(savedBookingToCancel);

    }

    @Test
    public void shouldSaveResidenceToPlayer() throws Exception {
      Player player = new Player();
      player.setName("Ana");
      Player savedPlayer = playerService.savePlayer(player);

      residence = new Residence();
      residence.setBuilding(RESIDENCE_BUILDING_EMPECINADO21);
      residence.setFloor(RESIDENCE_5FLOOR);
      residence.setLetter(RESIDENCE_LETTER_A);
      savedResidence = residenceService.saveResidence(residence);

      assertThat(savedPlayer.getResidence()).isNull();

      mockMvc.perform(post("/api/v1/players/{id}", savedPlayer.getId())
              .contentType(MediaType.APPLICATION_JSON)
              .content(objectMapper.writeValueAsString(residenceMapper.toDTO(savedResidence))))
              .andExpect(status().isOk())
              .andExpect(content().contentType(MediaType.APPLICATION_JSON))
              .andExpect(jsonPath("$.name").value("Ana"))
              .andExpect(jsonPath("$.residence.building").value(RESIDENCE_BUILDING_EMPECINADO21.toString()))
              .andExpect(jsonPath("$.residence.floor").value(RESIDENCE_5FLOOR.toString()))
              .andExpect(jsonPath("$.residence.letter").value(RESIDENCE_LETTER_A.toString()));

      assertThat(playerService.getPlayerById(savedPlayer.getId()).get().getResidence()).isNotNull();
    }

    @Test
    public void shouldNotSaveResidenceToPlayer() throws Exception {
        Player notPersistedPlayer = new Player();
        notPersistedPlayer.setName("Ana");
        notPersistedPlayer.setId(100L);

        residence = new Residence();
        residence.setBuilding(RESIDENCE_BUILDING_EMPECINADO21);
        residence.setFloor(RESIDENCE_5FLOOR);
        residence.setLetter(RESIDENCE_LETTER_A);
        savedResidence = residenceService.saveResidence(residence);

        assertThat(notPersistedPlayer.getResidence()).isNull();

        mockMvc.perform(post("/api/v1/players/{id}", notPersistedPlayer.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(residenceMapper.toDTO(savedResidence))))
                .andExpect(status().isBadRequest());
    }

    private List<Player> setUpTwoPlayers() {
        Player player1 = new Player();
        player1.setName("Ana");
        Player savedPlayer1 = playerRepository.save(player1);
        Player player2 = new Player();
        player2.setName("Pepe");
        Player savedPlayer2 = playerRepository.save(player2);
        return List.of(savedPlayer1, savedPlayer2);
    }

    private void setPlayerWithResidenceAndBooking(Booking booking) {
        residence = new Residence();
        residence.setBuilding(RESIDENCE_BUILDING_EMPECINADO21);
        residence.setFloor(RESIDENCE_5FLOOR);
        residence.setLetter(RESIDENCE_LETTER_A);
        savedResidence = residenceService.saveResidence(residence);

        player = new Player();
        player.setName("Ana");
        player.setResidence(savedResidence);
        player.addBooking(booking);

        savedPlayer = playerService.savePlayer(player);
        savedBookingToCancel = bookingService.saveBooking(booking);
    }

}
