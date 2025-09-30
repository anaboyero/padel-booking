package ana.learning.padel.padelBooking.DTO;

import java.time.LocalDate;
import java.util.List;

public class BookingCalendarDTO {
    private Long id;
    private LocalDate startDay;
    private List<Long> availableBookings;
    private List<Long> reservedBookings;

    public List<Long> getReservedBookings() {
        return reservedBookings;
    }

    public void setReservedBookings(List<Long> reservedBookings) {
        this.reservedBookings = reservedBookings;
    }

    public List<Long> getAvailableBookings() {
        return availableBookings;
    }

    public void setAvailableBookings(List<Long> availableBookings) {
        this.availableBookings = availableBookings;
    }

    public LocalDate getStartDay() {
        return startDay;
    }

    public void setStartDay(LocalDate startDay) {
        this.startDay = startDay;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }



}
