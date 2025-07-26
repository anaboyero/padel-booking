package ana.learning.padel.padelBooking.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;

@Entity
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate bookingDate;
    private TimeSlot timeSlot;
    @ManyToOne
    @JoinColumn(name = "bookingOwner_id")
    private Player bookingOwner;


    public enum TimeSlot {
//        NINE_AM,
//        TEN_AM,
//        ELEVEN_AM,
//        TWELVE_PM,
//        ONE_PM,
        TWO_PM,
        THREE_PM,
        FOUR_PM,
        FIVE_PM,
//        SIX_PM,
//        SEVEN_PM,
//        EIGHT_PM,
//        NINE_PM;
    }

    public Booking() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDate bookingDate) {
        this.bookingDate = bookingDate;
    }

    public TimeSlot getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(TimeSlot timeSlot) {
        this.timeSlot = timeSlot;
    }

    public Player getBookingOwner() {
        return bookingOwner;
    }

    public void setBookingOwner(Player bookingOwner) {
        this.bookingOwner = bookingOwner;
    }

    @Override
    public String toString() {
        return "|||" +
                "id=" + id +
                "/" + bookingDate +
                "/" + timeSlot +
                "/" + bookingOwner +
                "||| +\n";
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Booking booking = (Booking) o;
        return Objects.equals(bookingDate, booking.bookingDate) && timeSlot == booking.timeSlot;
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookingDate, timeSlot);
    }
}
