package ana.learning.padel.padelBooking.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Entity
public class BookingCalendar {

    private static final Logger log = LoggerFactory.getLogger(BookingCalendar.class);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate startDay;
    @OneToMany
    @JoinColumn(name = "available_booking_id")
    private List<Booking> availableBookings;
    @OneToMany
    @JoinColumn(name = "reserved_booking_id")
    private List<Booking> reservedBookings;

    public BookingCalendar() {
        reservedBookings  = new ArrayList<>();
        availableBookings  = new ArrayList<>();
    }
    public List<Booking> generateAvailableBookings(){
        if (startDay == null) {
            log.warn("No se ha podido generar un calendario de reservas porque no hay fecha de inicio.");
        }
        for (int i = 0; i<7; i++) {
            LocalDate day = startDay.plusDays(i);
            for (Booking.TimeSlot timeSlot : Booking.TimeSlot.values()) {
                Booking booking = new Booking();
                booking.setBookingDate(day);
                booking.setTimeSlot(timeSlot);
                booking.setBookingOwner(null);
                availableBookings.add(booking);
            }
        }
        return availableBookings;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getStartDay() {
        return startDay;
    }

    public void setStartDay(LocalDate startDay) {
        this.startDay = startDay;
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
                "7 days from=" + startDay +
                ". AvailableBookings=" + availableBookings +
                ". Reserved_bookings=" + reservedBookings +
                '}';
    }
}
