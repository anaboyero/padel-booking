package ana.learning.padel.padelBooking.service;

import ana.learning.padel.padelBooking.DTO.PlayerDTO;
import ana.learning.padel.padelBooking.model.Player;
import ana.learning.padel.padelBooking.model.Residence;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface PlayerService {
    public Player savePlayer(Player player);

    List<Player> getAllPlayers();

    void deleteAllPlayers();

    Optional<Player> getPlayerById(Long id);

    Optional<Player> addResidenceToPlayer(Player player, Residence residence);

    boolean hasAProperResidence(Player player);

    boolean isAProperPlayerToMakeAReservation(Player player);
}
