//package ana.learning.padel.padelBooking.mappers;
//
//import ana.learning.padel.padelBooking.DTO.BookingDTO;
//import ana.learning.padel.padelBooking.DTO.PlayerDTO;
//import ana.learning.padel.padelBooking.DTO.ResidenceDTO;
//import ana.learning.padel.padelBooking.model.Player;
//import ana.learning.padel.padelBooking.model.Residence;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.List;
//
//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//@SpringBootTest
//public class MapperTest {
//
//    @Autowired
//    PlayerMapper playerMapper;
//    @Autowired
//    ResidenceMapper residenceMapper;
//
//    private Player player;
//    private PlayerDTO playerDTO;
//    private Residence residence;
//    private ResidenceDTO residenceDTO;
//
//    private static final Residence.Building RESIDENCE_BUILDING_EMPECINADO_21 = Residence.Building.JUAN_MARTIN_EMPECINADO_21;
//    private static final Residence.Floor RESIDENCE_5FLOOR = Residence.Floor.FIFTH;
//    private static final Residence.Letter RESIDENCE_LETTER_A = Residence.Letter.A;
//
//
//    @BeforeEach
//    public void setUp() {
//        playerDTO = new PlayerDTO("Ana");
//        playerDTO.setId(1L);
//        residenceDTO = new ResidenceDTO();
//        residenceDTO.setBuilding("JUAN_MARTIN_EMPECINADO_21");
//        residenceDTO.setFloor("FIFTH");
//        residenceDTO.setLetter("A");
//        playerDTO.setResidence(residenceDTO);
//    }
//
//    @Test
//    public void shouldMapToPlayer() {
//        ///  GIVEN a playerDTO
//        ///  WHEN mapping it to Player
//        player = playerMapper.toPlayer(playerDTO);
//        /// THEN all the fields are transmited
//        assertThat(player.getName()).isEqualTo("Ana");
//        assertThat(player.getId()).isEqualTo(1L);
//        assertThat(player.getResidence().getFloor()).isEqualTo(RESIDENCE_5FLOOR);
//    }
//
//    @Test
//    public void shouldMapToResidence() {
//        residence = residenceMapper.toResidence(residenceDTO);
//        assertThat(residence.getBuilding()).isEqualTo(RESIDENCE_BUILDING_EMPECINADO_21);
//        assertThat(residence.getFloor()).isEqualTo(RESIDENCE_5FLOOR);
//        assertThat(residence.getLetter()).isEqualTo(RESIDENCE_LETTER_A);
//    }
//
//}
