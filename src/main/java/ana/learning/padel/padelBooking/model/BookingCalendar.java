package ana.learning.padel.padelBooking.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
public class BookingCalendar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate lastUpdate;
    @OneToMany
    @JoinColumn(name = "available_booking_id")
    private List<Booking> availableBookings;
    @OneToMany
    @JoinColumn(name = "reserved_booking_id")
    private List<Booking> reservedBookings;

    public BookingCalendar() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDate lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public List<Booking> getAvailableBookings() {
        return availableBookings;
    }

    public void setAvailableBookings(List<Booking> availableBookings) {
        this.availableBookings = availableBookings;
    }

    public List<Booking> getReservedBookings() {
        return reservedBookings;
    }

    public void setReservedBookings(List<Booking> reservedBookings) {
        this.reservedBookings = reservedBookings;
    }

    @Override
    public String toString() {
        return "BookingCalendar{" +
                "id=" + id +
                ", lastUpdate=" + lastUpdate +
                ", availableBookings=" + availableBookings +
                ", reserved_bookings=" + reservedBookings +
                '}';
    }
}
