package ana.learning.padel.padelBooking.service;

import ana.learning.padel.padelBooking.model.Booking;
import ana.learning.padel.padelBooking.model.Residence;
import ana.learning.padel.padelBooking.repository.ResidenceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ResidenceServiceTests {

    private static final Residence.Floor RESIDENCE_5FLOOR = Residence.Floor.FIFTH_FLOOR;
    private static final Residence.Letter RESIDENCE_LETTER_A = Residence.Letter.A;
    private static final Residence.Building RESIDENCE_BUILDING_EMPECINADO25 = Residence.Building.JUAN_MARTIN_EMPECINADO_25;

    Residence residenceToSave;

    private static final Logger log = LoggerFactory.getLogger(ResidenceServiceTests.class);

    @Mock
    ResidenceRepository residenceRepository;

    @InjectMocks
    ResidenceService residenceService = new ResidenceServiceImpl();

    @BeforeEach
    public void setUp() {
        residenceToSave = new Residence();
        residenceToSave.setBuilding(RESIDENCE_BUILDING_EMPECINADO25);
        residenceToSave.setFloor(RESIDENCE_5FLOOR);
        residenceToSave.setLetter(RESIDENCE_LETTER_A);
    }

    @Test
    public void shouldSaveAResidenceUsingRepository(){
        when(residenceRepository.save(any(Residence.class))).thenReturn(residenceToSave);

        Residence newResidence = residenceService.saveResidence(new Residence());

        assertEquals(RESIDENCE_BUILDING_EMPECINADO25, newResidence.getBuilding());
        assertEquals(RESIDENCE_5FLOOR, newResidence.getFloor());
        assertEquals(RESIDENCE_LETTER_A, newResidence.getLetter());
        verify(residenceRepository).save(any(Residence.class));
    }

}
