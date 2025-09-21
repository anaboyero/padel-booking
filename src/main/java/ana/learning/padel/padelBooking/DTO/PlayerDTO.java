package ana.learning.padel.padelBooking.DTO;

import java.util.List;

public class PlayerDTO {
    private Long id;
    private String name;
    private List<BookingDTO> bookings;

    public PlayerDTO() {
    }

    public PlayerDTO(String name) {
        this.name = name;
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

    public List<BookingDTO> getBookings() {
        return bookings;
    }

    public void setBookings(List<BookingDTO> bookings) {
        this.bookings = bookings;
    }

    @Override
    public String toString() {
        return "PlayerDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", bookings'" + bookings + '\'' +
                '}';
    }
}
