package ana.learning.padel.padelBooking.DTO;

import java.time.LocalDate;
import java.util.List;

public class BookingCalendarDTO {
    private Long id;
    private LocalDate startDay;
    private List<BookingDTO> availableBookings;
    private List<BookingDTO> reservedBookings;

    public List<BookingDTO> getReservedBookings() {
        return reservedBookings;
    }

    public void setReservedBookings(List<BookingDTO> reservedBookingsId) {
        this.reservedBookings = reservedBookingsId;
    }

    public List<BookingDTO> getAvailableBookings() {
        return availableBookings;
    }

    public void setAvailableBookings(List<BookingDTO> availableBookings) {
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
