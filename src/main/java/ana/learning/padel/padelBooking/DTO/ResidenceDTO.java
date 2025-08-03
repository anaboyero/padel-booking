package ana.learning.padel.padelBooking.DTO;

import ana.learning.padel.padelBooking.model.Residence;

public class ResidenceDTO {

    private Long id;
    private String building;
    private String floor;
    private String letter;

    public ResidenceDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }
}
