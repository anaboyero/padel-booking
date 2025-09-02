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


    // ESTO DEBERIA SER DE SERVICE, PORQUE LLAMA A REPOSITORIO PARA SACAR LAS ID ?
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
                "id = " + id +
                "\n startDay =" + startDay +
                "\n + AvailableBookings = +\n" + availableBookings +
                "\n + Reserved_bookings = + \n"  + reservedBookings +
                '}';
    }

    public Optional<Booking> reserveBooking(Booking tentativeBooking){
        if (isBookingValid(tentativeBooking) && (availableBookings.contains(tentativeBooking))) {
                availableBookings.remove(tentativeBooking);
                reservedBookings.add(tentativeBooking);
                log.info("\n*** Congratulations, you just booked a reservation!");
                return Optional.of(tentativeBooking);
        }
        log.info("\n*** Sorry, we couldn't process your booking. This may be due to a lack of availability or invalid booking details");
        log.info("\n*** EL FALLO ES ISBOOKINGVALID?" + !isBookingValid(tentativeBooking));
        log.info("\n*** RECORDEMOS: TENTATIVE BOOKING ES" + tentativeBooking);
        log.info("\n*** RECORDEMOS: START DAY IN CALENDAR ES" + startDay);

        log.info("\n*** EL FALLO ES CALENDARIO NO CONTIENE EL BOOKING?" + !availableBookings.contains(tentativeBooking));

        return Optional.empty();
    }

    private boolean isBookingValid(Booking tentativeBooking) {
        if (isDateBeyondCalendarRange(tentativeBooking)) {
            log.info("\n*** NO SE PUEDE RESERVAR PORQUE + isDateBeyondCalendarRange(tentativeBooking)");
            return false;
        }
        if (tentativeBooking.getBookingOwner()==null) {
            log.info("\n*** NO SE PUEDE RESERVAR PORQUE + tentativeBooking.getBookingOwner()==null");
            return false;
        }
        if (tentativeBooking.getBookingOwner().getResidence() == null) {
            log.info("\n*** NO SE PUEDE RESERVAR PORQUE + tentativeBooking.getBookingOwner()==null");
            return false;
        }
        return true;
    }

    public boolean isBookingAvailable(Booking tentativeBooking) {
        return availableBookings.contains(tentativeBooking);
    }

    private boolean isDateBeyondCalendarRange(Booking tentativeBooking){
        return (tentativeBooking.getBookingDate().isBefore(getStartDay()) || (tentativeBooking.getBookingDate().isAfter(getStartDay().plusDays(8))));
        }
}
