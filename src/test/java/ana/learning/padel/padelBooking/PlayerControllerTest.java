//package ana.learning.padel.padelBooking;
//
//import ana.learning.padel.padelBooking.DTO.PlayerDTO;
//import ana.learning.padel.padelBooking.model.Player;
//import ana.learning.padel.padelBooking.service.PlayerService;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.junit.jupiter.api.extension.MediaType;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.http.ResponseEntity;
//import org.springframework.test.web.servlet.MockMvc;
//
//import javax.print.attribute.standard.Media;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//
//@ExtendWith(MockitoExtension.class)
//public class PlayerControllerTest {
//
//    @Mock
//    private MockMvc mockMvc;
//
//    @InjectMocks
//    PlayerService playerService;
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
