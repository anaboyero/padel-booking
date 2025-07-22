package ana.learning.padel.padelBooking.repository;

import ana.learning.padel.padelBooking.model.Booking;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends CrudRepository<Booking, Long> {
    // This interface will automatically provide CRUD operations for Booking entities
    // Additional custom query methods can be defined here if needed
}
