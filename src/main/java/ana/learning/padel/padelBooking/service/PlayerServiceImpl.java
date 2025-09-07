package ana.learning.padel.padelBooking.service;

import ana.learning.padel.padelBooking.mappers.PlayerMapper;
import ana.learning.padel.padelBooking.model.Player;
import ana.learning.padel.padelBooking.model.Residence;
import ana.learning.padel.padelBooking.repository.PlayerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlayerServiceImpl implements PlayerService {

    private static final Logger log = LoggerFactory.getLogger(PlayerServiceImpl.class);

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private ResidenceService residenceService;

    @Autowired
    PlayerMapper mapper;

    @Override
    public Player savePlayer(Player player) {
        return playerRepository.save(player);
    }

    @Override
    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }

    @Override
    public void deleteAllPlayers() {
        playerRepository.deleteAll();
    }

    @Override
    public Optional<Player> getPlayerById(Long id) {
        return playerRepository.findById(id);
    }

    @Override
    public Optional<Player> addResidenceToPlayer(Player player, Residence residence) {
        if (playerRepository.findById(player.getId()).isPresent()) {
            Residence saveResidence = residenceService.saveResidence(residence);
            player.setResidence(saveResidence);
            player = savePlayer(player);
            return Optional.of(player);
        }
        return Optional.empty();
    }

    @Override
    public boolean hasAProperResidence(Player player){
        boolean result = (player.getResidence() != null) && (residenceService.isACompleteResidence(player.getResidence()));
        return result;
    }

    @Override
    public boolean isAProperPlayerToMakeAReservation(Player player) {
        return ((player !=null) && (player.getId()!=null) && (this.hasAProperResidence(player)));
    }
}