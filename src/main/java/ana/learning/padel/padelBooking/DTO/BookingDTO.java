package ana.learning.padel.padelBooking.DTO;

import ana.learning.padel.padelBooking.model.Booking;

import java.time.LocalDate;

public class BookingDTO {
    private Long id;
    private LocalDate bookingDate;
    private Booking.TimeSlot timeSlot;
    private PlayerDTO bookingOwner;

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

    public Booking.TimeSlot getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(Booking.TimeSlot timeSlot) {
        this.timeSlot = timeSlot;
    }

    public PlayerDTO getBookingOwner() {
        return bookingOwner;
    }

    public void setBookingOwner(PlayerDTO bookingOwner) {
        this.bookingOwner = bookingOwner;
    }

    @Override
    public String toString() {
        return "BookingDTO{" +
                "id=" + id +
                ", bookingDate=" + bookingDate +
                ", timeSlot=" + timeSlot +
                '}';
    }
}
