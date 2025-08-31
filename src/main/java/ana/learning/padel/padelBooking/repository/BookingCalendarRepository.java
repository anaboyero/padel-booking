package ana.learning.padel.padelBooking.repository;

import ana.learning.padel.padelBooking.model.Booking;
import ana.learning.padel.padelBooking.model.BookingCalendar;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingCalendarRepository extends CrudRepository<BookingCalendar, Long> {

}
