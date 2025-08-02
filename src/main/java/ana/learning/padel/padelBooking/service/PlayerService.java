package ana.learning.padel.padelBooking.service;

import ana.learning.padel.padelBooking.DTO.PlayerDTO;
import ana.learning.padel.padelBooking.model.Player;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface PlayerService {
    public Player savePlayer(PlayerDTO dto);

    List<Player> getAllPlayers();
}
