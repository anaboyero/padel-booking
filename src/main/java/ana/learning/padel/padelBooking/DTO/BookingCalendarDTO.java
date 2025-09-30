package ana.learning.padel.padelBooking.DTO;

import java.time.LocalDate;
import java.util.List;

public class BookingCalendarDTO {
    private Long id;
    private LocalDate startDay;
    private List<Long> availableBookingsId;
    private List<Long> reservedBookingsId;

    public List<Long> getReservedBookingsId() {
        return reservedBookingsId;
    }

    public void setReservedBookingsId(List<Long> reservedBookingsId) {
        this.reservedBookingsId = reservedBookingsId;
    }

    public List<Long> getAvailableBookingsId() {
        return availableBookingsId;
    }

    public void setAvailableBookingsId(List<Long> availableBookingsId) {
        this.availableBookingsId = availableBookingsId;
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
