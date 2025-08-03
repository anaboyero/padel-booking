package ana.learning.padel.padelBooking.controller;

import ana.learning.padel.padelBooking.DTO.PlayerDTO;
import ana.learning.padel.padelBooking.mappers.PlayerMapper;
import ana.learning.padel.padelBooking.model.Player;
import ana.learning.padel.padelBooking.service.PlayerService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/api/v1")
public class PlayerController {

    private final PlayerService playerService;
    private final PlayerMapper mapper;

    public PlayerController(PlayerService playerService, PlayerMapper mapper){
        this.playerService = playerService;
        this.mapper = mapper;
    }

    @GetMapping("/players")
    public ResponseEntity<List<Player>> getAllPlayers(){
        List<Player> players = new ArrayList<>();
        return ResponseEntity.ok(players);
    }

    @PostMapping("/players")
    public PlayerDTO savePlayer(@RequestBody PlayerDTO dto) {
        Player player = playerService.savePlayer(mapper.toPlayer(dto));
        return mapper.toDTO(player);
    }

    @DeleteMapping("/players")
    public ResponseEntity<List<Player>> DeleteAllPlayers() {
        playerService.deleteAllPlayers();
        List<Player> players = playerService.getAllPlayers();
        return ResponseEntity.ok(players);


    }
}
