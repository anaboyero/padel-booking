package ana.learning.padel.padelBooking.repository;

import ana.learning.padel.padelBooking.model.Residence;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResidenceRepository extends CrudRepository<Residence, Long> {
    // This interface will automatically provide CRUD operations for Residence entities
    // Additional custom query methods can be defined here if needed
}
