package ana.learning.padel.padelBooking.service;

import ana.learning.padel.padelBooking.model.Player;
import ana.learning.padel.padelBooking.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PlayerServiceImpl implements PlayerService {

    @Autowired
    private PlayerRepository playerRepository;

    public Player savePlayer(Player player) {
        return playerRepository.save(player);
    }
}
