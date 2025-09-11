package ana.learning.padel.padelBooking.controller;

import ana.learning.padel.padelBooking.DTO.PlayerDTO;
import ana.learning.padel.padelBooking.DTO.ResidenceDTO;
import ana.learning.padel.padelBooking.mappers.PlayerMapper;
import ana.learning.padel.padelBooking.mappers.ResidenceMapper;
import ana.learning.padel.padelBooking.model.Player;
import ana.learning.padel.padelBooking.model.Residence;
import ana.learning.padel.padelBooking.service.PlayerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/players")
public class PlayerController {

    private final PlayerService playerService;
    private final PlayerMapper playerMapper;
    private final ResidenceMapper residenceMapper;
    private static final Logger log = LoggerFactory.getLogger(PlayerController.class);


    public PlayerController(PlayerService playerService, PlayerMapper playerMapper, ResidenceMapper residenceMapper){
        this.playerService = playerService;
        this.playerMapper = playerMapper;
        this.residenceMapper = residenceMapper;
    }

    @GetMapping
    public ResponseEntity<List<Player>> getAllPlayers(){
        // Nota: no parece prudente devolver la direccion de cualquier persona,
        // pero de momento me es util para saber que funciona

        List<Player> players = playerService.getAllPlayers();
        if (players.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(players);
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<Optional<Player>> getPlayerById(@PathVariable Long id) {
//        Optional<Player> player = playerService.getPlayerById(id);
//        if (player.isEmpty()){
//            return ResponseEntity.noContent().build();
//        }
//        return ResponseEntity.ok(player);
//    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<PlayerDTO>> getPlayerById(@PathVariable Long id) {
        Optional<Player> player = playerService.getPlayerById(id);
        if (player.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        PlayerDTO playerDTO = playerMapper.toDTO(player.get());
        return ResponseEntity.ok(Optional.of(playerDTO));
    }

    @PostMapping
    public PlayerDTO savePlayer(@RequestBody PlayerDTO dto) {
        Player player = playerService.savePlayer(playerMapper.toPlayer(dto));
        return playerMapper.toDTO(player);
    }

    @DeleteMapping
    public ResponseEntity<Player> DeleteAllPlayers() {
        playerService.deleteAllPlayers();
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Player> deletePlayerById (@PathVariable Long id) {
        Optional<Player> result = playerService.getPlayerById(id);
        if (result.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result.get());
    }

    @PostMapping("/{id}")
    public Optional<PlayerDTO> addResidenceToPlayer(@PathVariable Long id, @RequestBody ResidenceDTO residenceDTO) {
        Optional<PlayerDTO> resultDTO;
        if (playerService.getPlayerById(id).isPresent()){
            Residence residence = residenceMapper.toResidence(residenceDTO);
            Optional<Player> result = playerService.addResidenceToPlayer(playerService.getPlayerById(id).get(), residence);
            resultDTO = Optional.of(playerMapper.toDTO(result.get()));
            return resultDTO;
        }
        else {
            return Optional.empty();
        }
    }
}
