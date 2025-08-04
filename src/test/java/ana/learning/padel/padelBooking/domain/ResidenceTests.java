package ana.learning.padel.padelBooking.domain;

import ana.learning.padel.padelBooking.model.Residence;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.StatusResultMatchersExtensionsKt.isEqualTo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootTest
public class ResidenceTests {

    private static final Residence.Building RESIDENCE_BUILDING_EMPECINADO21 = Residence.Building.JUAN_MARTIN_EMPECINADO_21;
    private static final Residence.Floor RESIDENCE_5FLOOR = Residence.Floor.FIFTH;
    private static final Residence.Letter RESIDENCE_LETTER_A = Residence.Letter.A;

    private static final Logger log = LoggerFactory.getLogger(ResidenceTests.class);

    @Test
    public void shouldCreateANewResidence(){
        Residence residence = new Residence();
        residence.setBuilding(RESIDENCE_BUILDING_EMPECINADO21);
        residence.setFloor(RESIDENCE_5FLOOR);
        residence.setLetter(RESIDENCE_LETTER_A);
        assertThat(residence.getBuilding()).isEqualTo(Residence.Building.JUAN_MARTIN_EMPECINADO_21);
        assertThat(residence.getFloor()).isEqualTo(Residence.Floor.FIFTH);
        assertThat(residence.getLetter()).isEqualTo(Residence.Letter.A);
    }

}
