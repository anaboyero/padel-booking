package ana.learning.padel.padelBooking.controller;

import ana.learning.padel.padelBooking.model.Player;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/api/v1")
public class PlayerController {

    @GetMapping("/players")
    public ResponseEntity<List<Player>> getAllPlayers(){
        List<Player> players = new ArrayList<>();
        return ResponseEntity.ok(players);
    }
}
