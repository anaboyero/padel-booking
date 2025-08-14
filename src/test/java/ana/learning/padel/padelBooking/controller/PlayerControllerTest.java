package ana.learning.padel.padelBooking.controller;

import ana.learning.padel.padelBooking.mappers.PlayerMapper;
import ana.learning.padel.padelBooking.model.Player;
import ana.learning.padel.padelBooking.repository.BookingRepository;
import ana.learning.padel.padelBooking.repository.PlayerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class PlayerControllerTest {

    private final MockMvc mockMvc;
    private final PlayerMapper playerMapper;
    private final ObjectMapper objectMapper;
    private final PlayerRepository playerRepository;
    private final BookingRepository bookingRepository;

    private static final Logger log = LoggerFactory.getLogger(PlayerControllerTest.class);


    public PlayerControllerTest(MockMvc mockMvc, PlayerMapper playerMapper, ObjectMapper objectMapper, PlayerRepository playerRepository, BookingRepository bookingRepository) {
        this.mockMvc = mockMvc;
        this.playerMapper = playerMapper;
        this.objectMapper = objectMapper;
        this.playerRepository = playerRepository;
        this.bookingRepository = bookingRepository;
    }

    @BeforeEach
    void setUp() {
        log.info("\n*** Limpiando repositorios en @BeforeEach");
        bookingRepository.deleteAll();
        playerRepository.deleteAll();
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

    }

    @Test
    public void shouldReturnNoContentWhenDeletingAllPlayers() throws Exception {
        mockMvc.perform(delete("/api/v1/players"))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/v1/players"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldReturnListOfPlayersWhenPlayersExist() throws Exception {
        Player player1 = new Player();
        player1.setName("Ana");
        playerRepository.save(player1);
        Player player2 = new Player();
        player2.setName("Pepe");
        playerRepository.save(player2);

        mockMvc.perform(get("/api/v1/players"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value("Ana"))
                .andExpect(jsonPath("$[1].name").value("Pepe"));
    }

    @Test
    public void shouldReturnNotFoundWhenDeletingNonExistentPlayer() throws Exception {
        Long playerId = 1L;
        mockMvc.perform(delete("/api/v1/players/{id}", playerId))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturnOkAndDeletedPlayerWhenDeletingExistingPlayer() throws Exception {
        Player player = new Player();
        player.setName("Ana");
        Player savedPlayer = playerRepository.save(player);

        mockMvc.perform(delete("/api/v1/players/{id}", savedPlayer.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Ana"))
        ;

    }









}
