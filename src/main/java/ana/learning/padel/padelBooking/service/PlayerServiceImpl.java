package ana.learning.padel.padelBooking.service;

import ana.learning.padel.padelBooking.mappers.PlayerMapper;
import ana.learning.padel.padelBooking.model.Booking;
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
    private BookingService bookingService;

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
    public void deletePlayerById(Long id) {
        playerRepository.deleteById(id);
    }

//    @Override
//    public void deletePlayerById(Long id) {
//        Optional<Player> player = playerRepository.findById(id);
//        player.ifPresent(playerRepository::delete);
//    }

    @Override
    public Optional<Player> getPlayerById(Long id) {
        return playerRepository.findById(id);
    }

    @Override
    public Optional<Player> addResidenceToPlayer(Player player, Residence residence) {
        Optional<Player> playerOptional = playerRepository.findById(player.getId());
        if (playerOptional.isPresent()) {
            Residence savedResidence = residenceService.saveResidence(residence);
            player.setResidence(savedResidence);
            Player savedPlayer = savePlayer(player);
            return Optional.of(savedPlayer);
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

    @Override
    public Optional<Player> cancelBookingByPlayer(Booking bookingToCancel, Player player){
        return Optional.of(player.cancelBooking(bookingToCancel));

//        if(bookingService.getBookingById(bookingToCancel.getId()).isEmpty()){
//            log.info("The booking to cancel does not exist");
//            return Optional.empty();
//        }
//        if(playerRepository.findById(player.getId()).isEmpty()){
//            log.info("The player does not exist");
//            return Optional.empty();
//        }
//        return Optional.of(player.cancelBooking(bookingToCancel));

    }

    @Override
    public Player addBookingToPlayer(Player player, Booking booking) {
        if (booking.getId()==null) {
            log.info("The booking is not valid because it is not persisted");
            return player;
        }

        if (booking.getBookingOwner()!=null){
            log.info("The booking is not valid because it already has an owner");
            return player;
        }

        player.addBooking(booking);
        return playerRepository.save(player);
    }



}