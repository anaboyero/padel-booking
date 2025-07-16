package ana.learning.padel.padelBooking.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
public class Booking_Calendar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate lastUpdate;
    @OneToMany
    @JoinColumn(name = "available_booking_id")
    private List<Booking> available_bookings;
    @OneToMany
    @JoinColumn(name = "reserved_booking_id")
    private List<Booking> reserved_bookings;

    public Booking_Calendar() {
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

    public List<Booking> getAvailable_bookings() {
        return available_bookings;
    }

    public void setAvailable_bookings(List<Booking> available_bookings) {
        this.available_bookings = available_bookings;
    }

    public List<Booking> getReserved_bookings() {
        return reserved_bookings;
    }

    public void setReserved_bookings(List<Booking> reserved_bookings) {
        this.reserved_bookings = reserved_bookings;
    }

    @Override
    public String toString() {
        return "BookingCalendar{" +
                "id=" + id +
                ", lastUpdate=" + lastUpdate +
                ", available_bookings=" + available_bookings +
                ", reserved_bookings=" + reserved_bookings +
                '}';
    }
}
