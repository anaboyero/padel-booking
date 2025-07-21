package ana.learning.padel.padelBooking.repository;

import ana.learning.padel.padelBooking.model.Player;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends CrudRepository<Player, Long> {
    // This interface will automatically provide CRUD operations for Player entities
    // Additional custom query methods can be defined here if needed
}
