package ana.learning.padel.padelBooking.DTO;

public class PlayerDTO {
    private Long id;
    private String name;

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

    @Override
    public String toString() {
        return "PlayerDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
