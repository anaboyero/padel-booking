package ana.learning.padel.padelBooking.model;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
    @ManyToOne
    @JoinColumn(name = "calendar_id")
    private BookingCalendar calendar;

    public Booking() {
    }

    public BookingCalendar getCalendar() {
        return calendar;
    }

    public void setCalendar(BookingCalendar calendar) {
        this.calendar = calendar;
    }

    public enum TimeSlot {
//        NINE_AM,
//        TEN_AM,
//        ELEVEN_AM,
//        TWELVE_PM,
        ONE_PM,
        TWO_PM,
        THREE_PM,
        FOUR_PM,
        FIVE_PM,
//        SIX_PM,
//        SEVEN_PM,
//        EIGHT_PM,
//        NINE_PM;
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

    public Boolean isAvailable(){
        return (getBookingOwner()==null);
    }

//    @Override
//    public String toString() {
//        String message = " \n|||" +
//                "id=" + id +
//                "/" + bookingDate +
//                "/" + timeSlot;
//        if (bookingOwner == null) {
//            message += "/ AVAILABLE ";
//        } else {
//            message += "/ RESERVED by BookingOwnerId:" + bookingOwner.getId() +"\n";
//        }
//      return message;
//    }

    @Override
    public String toString() {
        // 1. Usar StringBuilder para construir la cadena de manera eficiente
        StringBuilder sb = new StringBuilder();

        sb.append(" \n|||")
                .append("id=").append(id)
                .append("/").append(bookingDate)
                .append("/").append(timeSlot);

        // 2. Aplicar la lógica condicional con append
        if (bookingOwner == null) {
            sb.append("/ AVAILABLE ");
        } else {
            sb.append("/ RESERVED by BookingOwnerId:")
                    .append(bookingOwner.getId())
                    .append("\n");
        }

        // 3. Devolver el resultado final
        return sb.toString();
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
