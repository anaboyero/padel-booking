package ana.learning.padel.padelBooking.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // LocalDate	Represents a date (year, month, day (yyyy-MM-dd))
    private LocalDate day;
    private TimeSlot timeSlot;
    @ManyToOne
    @JoinColumn(name = "booking_owner_id")
    private Player booking_owner;


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

    public Booking(LocalDate day, TimeSlot timeSlot, Player booking_owner) {
        this.day = day;
        this.timeSlot = timeSlot;
        this.booking_owner = booking_owner;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDay() {
        return day;
    }

    public void setDay(LocalDate day) {
        this.day = day;
    }

    public TimeSlot getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(TimeSlot timeSlot) {
        this.timeSlot = timeSlot;
    }

    public Player getBooking_owner() {
        return booking_owner;
    }

    public void setBooking_owner(Player booking_owner) {
        this.booking_owner = booking_owner;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "id=" + id +
                ", day=" + day +
                ", timeSlot=" + timeSlot +
                ", booking_owner=" + booking_owner +
                '}';
    }
}
