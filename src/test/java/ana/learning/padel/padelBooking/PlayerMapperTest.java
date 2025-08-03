package ana.learning.padel.padelBooking;

import ana.learning.padel.padelBooking.DTO.PlayerDTO;
import ana.learning.padel.padelBooking.mappers.PlayerMapper;
import ana.learning.padel.padelBooking.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class PlayerMapperTest {

    PlayerMapper INSTANCE = Mappers.getMapper(PlayerMapper.class);
    private Player player;
    private PlayerDTO dto;

    @BeforeEach
    public void setUp() {
        dto = new PlayerDTO("Ana");
        dto.setId(1L);
    }

    @Test
    public void shouldMapToPlayer() {
        player = INSTANCE.toPlayer(dto);
        assertThat(player.getName()).isEqualTo("Ana");
        assertThat(player.getId()).isEqualTo(1L);
    }

}
