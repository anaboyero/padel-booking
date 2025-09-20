package ana.learning.padel.padelBooking.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToOne
    @JoinColumn (name = "residence_id")
    private Residence residence;
    @OneToMany(mappedBy = "bookingOwner")
    List<Booking> bookings;

    public Player() {
        bookings = new java.util.ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Residence getResidence() {
        return residence;
    }

    public void setResidence(Residence residence) {
        if (residence.getId()!=null) {
            this.residence = residence;
        }
        else {
            System.out.println("\n ***No se puede añadir una residencia que no está persistida");
        }
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    public void addBooking(Booking booking) {
        this.bookings.add(booking);
        booking.setBookingOwner(this);
    }

    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", residence=" + residence +
                ", bookings=" + bookings +
                '}';
    }

    //Incluir validaciones aqui o deberian estar en el service?
    public Player cancelBooking(Booking bookingToCancel) {
        for (Booking booking: bookings) {
            if (booking.getId().equals(bookingToCancel.getId())) { // Estoy comparando por ID.
                if(booking.getBookingDate().isBefore(java.time.LocalDate.now())){
                    System.out.println("\n ***No se puede cancelar una reserva pasada");
                    return this;
                }
                bookings.remove(booking);
                return this;
            }
        }
        return this;
    }
}
