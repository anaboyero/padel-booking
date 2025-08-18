package ana.learning.padel.padelBooking.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Residence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Building building;
    private Floor floor;
    private Letter letter;

    public boolean hasAllFields() {
        return (building!=null && floor!=null && letter!=null);
    }
//    @OneToMany(mappedBy = "residence")
//    private List<Player> players;

    public enum Building {
        JUAN_MARTIN_EMPECINADO_21,
        JUAN_MARTIN_EMPECINADO_23,
        JUAN_MARTIN_EMPECINADO_25,
        RAMIREZ_PRADO_7,
        RAMIREZ_PRADO_8;
    }

    public enum Floor {GROUND, FIRST, SECOND, THIRD, FOURTH, FIFTH, SIXTH, SEVENTH;}
    public enum Letter {A, B, C, D, E, F, G;}

    public Residence() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public Floor getFloor() {
        return floor;
    }

    public void setFloor(Floor floor) {
        this.floor = floor;
    }

    public Letter getLetter() {
        return letter;
    }

    public void setLetter(Letter letter) {
        this.letter = letter;
    }

    @Override
    public String toString() {
        return "{   ID: " + id +
                ", Calle " +
                building +
                ", " + floor +
                ", " + letter +
                '}';
    }
}
