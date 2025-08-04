package ana.learning.padel.padelBooking;

import ana.learning.padel.padelBooking.controller.PlayerController;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class PlayerControllerTest {

    private MockMvc mockMvc;

    private PlayerMapper playerMapper;

    private ObjectMapper objectMapper;

    private PlayerRepository playerRepository;

    private BookingRepository bookingRepository;

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
        log.info("\n*** TEST. Entrando en @BeforeEach");
        bookingRepository.deleteAll();
        playerRepository.deleteAll();
    }

//    @Test
//    public void shouldSaveAPlayer() throws Exception {
//        log.info("\n*** TEST. Entrando en shouldSaveAPlayer");
//        Player player = new Player();
//        player.setName("Ana");
//
//        mockMvc.perform(post("/players")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(player)))
//                .andExpect(status().isOk());
//    }
}

//
////    @Test
////    public void should
//
////    @Test
////    public void shouldSaveAPlayer() throws Exception {
////        // ARRANGE
////        PlayerDTO dto = new PlayerDTO("Ana");
////
////        // Mock
////        Player savedPlayer = new Player();
////        savedPlayer.setName("Ana");
////        savedPlayer.setId(1L);
////        when(playerService.savePlayer(any(Player.class))).thenReturn(savedPlayer);
////
////        // ACT
////        ResponseEntity<PlayerDTO> response = mockMvc.perform(post("/players"))
////                .contentType(MediaType.APPLICATION_JSON)
////                .content(new ObjectMapper().writeValueAsString(dto));
////
////
////    }
//}
