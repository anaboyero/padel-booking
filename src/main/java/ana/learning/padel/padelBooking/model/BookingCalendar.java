package ana.learning.padel.padelBooking.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    private List<Booking> generateAvailableBookings(){
        availableBookings  = new ArrayList<>();
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
        generateAvailableBookings();
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
                "id=" + id +
                "7 days from=" + startDay +
                "\n + AvailableBookings= +\n" + availableBookings +
                "\n + Reserved_bookings= + \n"  + reservedBookings +
                '}';
    }

    public Optional<Booking> reserveBooking(Booking tentativeBooking){
        Optional<Booking> response;

        if (availableBookings.contains(tentativeBooking)) {
            availableBookings.remove(tentativeBooking);
            reservedBookings.add(tentativeBooking);
            log.info("\n*** RESERVA HECHA");
            return Optional.of(tentativeBooking);
        }
        log.info("\n*** NO SE PUEDE HACER A RESERVA");
        return Optional.empty();
    }

    public boolean isBookingAvailable(Booking tentativeBooking) {

        if (isDateBeyondCalendarRange(tentativeBooking)) {
            return false;
        }
        return availableBookings.contains(tentativeBooking);
    }

    private boolean isDateBeyondCalendarRange(Booking tentativeBooking){
        return (tentativeBooking.getBookingDate().isBefore(getStartDay()) || (tentativeBooking.getBookingDate().isAfter(getStartDay().plusDays(8))));
        }
}
