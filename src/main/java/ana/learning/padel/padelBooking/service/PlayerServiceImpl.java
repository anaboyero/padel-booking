package ana.learning.padel.padelBooking.service;

import ana.learning.padel.padelBooking.DTO.PlayerDTO;
import ana.learning.padel.padelBooking.mappers.PlayerMapper;
import ana.learning.padel.padelBooking.model.Player;
import ana.learning.padel.padelBooking.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlayerServiceImpl implements PlayerService {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    PlayerMapper mapper;

    @Override
    public Player savePlayer(PlayerDTO dto) {
        return playerRepository.save(mapper.toPlayer(dto));
    }

    @Override
    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }
}
