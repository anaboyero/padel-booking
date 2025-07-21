package ana.learning.padel.padelBooking.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Entity
public class BookingCalendar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate lastUpdate;
    @OneToMany
    @JoinColumn(name = "available_booking_id")
    private List<Booking> availableBookings = new ArrayList<>();
    @OneToMany
    @JoinColumn(name = "reserved_booking_id")
    private List<Booking> reservedBookings = new ArrayList<>();

    public BookingCalendar() {}

    public BookingCalendar(LocalDate localDate) {
        lastUpdate = localDate;
        availableBookings = generateAvailableBookings(localDate);
        reservedBookings  = new ArrayList<>();
        System.out.println("****** Nuevo BookingCalendar creado a partir del día " + localDate);
    }
    private List<Booking> generateAvailableBookings(LocalDate startDate){
        List<Booking> generatedBookings = new ArrayList<>();
        for (int i = 0; i<7; i++) {
            LocalDate day = startDate.plusDays(i);
            for (Booking.TimeSlot timeSlot : Booking.TimeSlot.values()) {
                Booking booking = new Booking();
                booking.setDay(day);
                booking.setTimeSlot(timeSlot);
                booking.setBooking_owner(null);
                System.out.println("****** creado el booking = " + booking);
                generatedBookings.add(booking);
            }
        }
        return generatedBookings;

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
