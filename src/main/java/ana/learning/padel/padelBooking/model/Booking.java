package ana.learning.padel.padelBooking.model;

import jakarta.persistence.*;

import java.time.LocalDate;

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
        NINE_AM,
        TEN_AM,
        ELEVEN_AM,
        TWELVE_PM,
        ONE_PM,
        TWO_PM,
        THREE_PM,
        FOUR_PM,
        FIVE_PM,
        SIX_PM,
        SEVEN_PM,
        EIGHT_PM,
        NINE_PM;
    }

    public Booking() {
    }

    public Booking(LocalDate bookingDate, TimeSlot timeSlot, Player bookingOwner) {
        this.bookingDate = bookingDate;
        this.timeSlot = timeSlot;
        this.bookingOwner = bookingOwner;
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
        return "Booking{" +
                "id=" + id +
                ", bookingDate=" + bookingDate +
                ", timeSlot=" + timeSlot +
                ", bookingOwner=" + bookingOwner +
                '}';
    }
}
