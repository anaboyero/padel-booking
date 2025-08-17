package ana.learning.padel.padelBooking.service;

import ana.learning.padel.padelBooking.model.Player;
import ana.learning.padel.padelBooking.model.Residence;
import ana.learning.padel.padelBooking.repository.PlayerRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PlayerServiceTest {

    private static final Logger log = LoggerFactory.getLogger(PlayerServiceTest.class);

    @Mock
    private PlayerRepository playerRepository;

    @Mock
    private PlayerRepository residenceRepository;

    @InjectMocks
    private PlayerService playerService = new PlayerServiceImpl();

    @InjectMocks
    private ResidenceService residenceService = new ResidenceServiceImpl();

    private Player player0;
    private Player player1;

    private static final Residence.Building RESIDENCE_BUILDING_EMPECINADO_21 = Residence.Building.JUAN_MARTIN_EMPECINADO_21;
    private static final Residence.Building RESIDENCE_BUILDING_EMPECINADO_23 = Residence.Building.JUAN_MARTIN_EMPECINADO_23;
    private static final Residence.Floor RESIDENCE_5FLOOR = Residence.Floor.FIFTH;
    private static final Residence.Letter RESIDENCE_LETTER_A = Residence.Letter.A;


    @BeforeEach
    public void setUp() {
        player0 = new Player();
        player0.setName("Ana");
        player0.setId(null);
        player1 = new Player();
        player1.setName("Javier");
        player1.setId(1L);
    }

    @Test
    public void shouldSaveANewPlayer() {
        when(playerRepository.save(any(Player.class))).thenReturn(player1);
        Player testPlayer = playerService.savePlayer(player0);
        assertThat(testPlayer.getId()).isEqualTo(1L);
        assertThat(testPlayer.getName()).isEqualTo("Javier");
    }
    
//    @Test
//    public void shouldSavePersistedResidence() {
//        Residence residence0 = new Residence();
//
//        when(playerRepository.save(any(Player.class))).thenReturn(player1);
//        when(residenceRepository.save(any(Residence.class))).thenReturn(residence1);
//
//
//
//    }

}



