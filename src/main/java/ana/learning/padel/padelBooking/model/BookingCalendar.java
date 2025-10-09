package ana.learning.padel.padelBooking.model;

import ana.learning.padel.padelBooking.exceptions.PastDateException;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import jakarta.transaction.Transactional;
import org.hibernate.annotations.Where;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Entity
public class BookingCalendar {

    private static final Logger log = LoggerFactory.getLogger(BookingCalendar.class);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate startDay;
    @OneToMany(mappedBy = "calendar", cascade = CascadeType.ALL, orphanRemoval = true)
    @Where(clause = "booking_owner_id IS NULL")
    private List<Booking> availableBookings;
    @OneToMany(mappedBy = "calendar", cascade = CascadeType.ALL, orphanRemoval = true)
    @Where(clause = "booking_owner_id IS NOT NULL")
    private List<Booking> reservedBookings;


    public BookingCalendar() {
        availableBookings = new ArrayList<>();
        reservedBookings  = new ArrayList<>();
    }

//    public BookingCalendar(LocalDate startDay) {
//        if (startDay.isBefore(LocalDate.now())) {
//            log.error("The start date for the booking calendar cannot be in the past.");
//            throw new PastDateException("The start date for the booking calendar cannot be in the past.");
//        }
//        this.startDay = startDay;
//        reservedBookings  = new ArrayList<>();
//        setAvailableBookings(fillAvailableBookings());
//    }

//    private List<Booking> fillAvailableBookings(){
//        List<Booking> availableBookings  = new ArrayList<>();
//        for (int i = 0; i<7; i++) {
//            LocalDate day = startDay.plusDays(i);
//            for (Booking.TimeSlot timeSlot : Booking.TimeSlot.values()) {
//                Booking booking = new Booking();
//                booking.setBookingDate(day);
//                booking.setTimeSlot(timeSlot);
//                booking.setId(null);
//                booking.setBookingOwner(null);
//                booking.setCalendar(this);
//                availableBookings.add(booking);
//            }
//        }
//        return availableBookings;
//    }

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
//        setAvailableBookings(fillAvailableBookings());
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
        return "\n + BookingCalendar{" +
                "id = " + id +
                "\n startDay =" + startDay +
                "\n + AvailableBookings = +\n" + availableBookings +
                "\n + Reserved_bookings = + \n"  + reservedBookings +
                '}';
    }

}
