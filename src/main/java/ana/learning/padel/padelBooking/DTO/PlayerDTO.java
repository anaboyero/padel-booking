package ana.learning.padel.padelBooking.DTO;

import java.util.List;

public class PlayerDTO {
    private Long id;
    private String name;
    private List<Long> bookingIds;

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

//    public List<Long> getBookingIds() {
//        return bookingIds;
//    }
//
//    public void setBookingIds(List<Long> bookingIds) {
//        this.bookingIds = bookingIds;
//    }

    @Override
    public String toString() {
        return "PlayerDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
