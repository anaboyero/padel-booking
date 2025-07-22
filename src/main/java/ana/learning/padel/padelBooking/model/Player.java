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
    @OneToMany(mappedBy = "bookingOwner", cascade = CascadeType.PERSIST)
    List<Booking> bookings;

    public Player() {
    }

    public Player(String name, Residence residence) {
        this.name = name;
        this.residence = residence;
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
        this.residence = residence;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
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
}
