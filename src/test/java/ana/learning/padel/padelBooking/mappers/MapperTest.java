package ana.learning.padel.padelBooking.mappers;

import ana.learning.padel.padelBooking.DTO.BookingDTO;
import ana.learning.padel.padelBooking.DTO.PlayerDTO;
import ana.learning.padel.padelBooking.DTO.ResidenceDTO;
import ana.learning.padel.padelBooking.model.Booking;
import ana.learning.padel.padelBooking.model.BookingCalendar;
import ana.learning.padel.padelBooking.model.Player;
import ana.learning.padel.padelBooking.model.Residence;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//@SpringBootTest
public class MapperTest {

    PlayerMapper playerMapper = new PlayerMapperImpl();
    BookingMapper bookingMapper = new BookingMapperImpl();

//    @Autowired
//    PlayerMapper playerMapper;
//    @Autowired
//    ResidenceMapper residenceMapper;
//    @Autowired
//    BookingMapper bookingMapper;

    private Player player;
    private PlayerDTO playerDTO;
    private Residence residence;
    private ResidenceDTO residenceDTO;

    private static final Residence.Building RESIDENCE_BUILDING_EMPECINADO_21 = Residence.Building.JUAN_MARTIN_EMPECINADO_21;
    private static final Residence.Floor RESIDENCE_5FLOOR = Residence.Floor.FIFTH;
    private static final Residence.Letter RESIDENCE_LETTER_A = Residence.Letter.A;
    private static final Booking.TimeSlot SLOT = Booking.TimeSlot.TWO_PM;
    private static final LocalDate TODAY = LocalDate.now();



    @BeforeEach
    public void setUp() {
        playerDTO = new PlayerDTO("Ana");
        playerDTO.setId(1L);
        residenceDTO = new ResidenceDTO();
        residenceDTO.setBuilding("JUAN_MARTIN_EMPECINADO_21");
        residenceDTO.setFloor("FIFTH");
        residenceDTO.setLetter("A");
        playerDTO.setResidence(residenceDTO);
    }

//    @ParameterizedTest
//    @CsvSource({
//            "0,0",   // sin bookings → lista vacía
//            "3,3"    // 3 bookings creados → lista con 3
//    })
    @Test
    public void shouldMapToBookingDTO() {
        ///  GIVEN AN AVAILABLE BOOKING
        Booking availableBooking = new Booking();
        availableBooking.setTimeSlot(SLOT);
        availableBooking.setBookingDate(TODAY);

        BookingDTO dto = bookingMapper.toDTO(availableBooking);

        assertThat(dto.getBookingDate()).isEqualTo(TODAY);
        assertThat(dto.getTimeSlot()).isEqualTo(SLOT);
    }

    @Test
    public void shouldMapToBookingDTO_reservation() {
        ///  GIVEN A RESERVATION

        BookingCalendar calendar = new BookingCalendar();
        calendar.setId(100L);

        Booking booking = new Booking();
        booking.setTimeSlot(SLOT);
        booking.setBookingDate(TODAY);
        booking.setId(13L);
        booking.setCalendar(calendar);

        Player player = new Player();
        player.setName("Ana");
        player.setId(10L);


        Residence residence = new Residence();
        residence.setFloor(RESIDENCE_5FLOOR);
        residence.setId(20L);
        residence.setBuilding(RESIDENCE_BUILDING_EMPECINADO_21);
        residence.setLetter(Residence.Letter.A);
        player.setResidence(residence);

        booking.reserveBooking(player);

        assertThat(booking.getBookingOwner().getName()).isEqualTo("Ana");
        assertThat(player.getBookings().size()).isEqualTo(1);

        BookingDTO dto = bookingMapper.toDTO(booking);

        assertThat(dto.getBookingDate()).isEqualTo(TODAY);
        assertThat(dto.getTimeSlot()).isEqualTo(SLOT);
        assertThat(dto.getId()).isEqualTo(13L);
        assertThat(dto.getBookingOwnerId()).isEqualTo(10L);
        assertThat(dto.getBookingOwnerId()).isEqualTo(10L);
        System.out.println("\n ***** ");
        System.out.println(dto.toString());
        System.out.println("\n ***** ");
    }



//    @Test
//    public void shouldMapToPlayer() {
//        ///  GIVEN a playerDTO
//        ///  WHEN mapping it to Player
//        player = playerMapper.toPlayer(playerDTO);
//        /// THEN all the fields are transmited
//        assertThat(player.getName()).isEqualTo("Ana");
//        assertThat(player.getId()).isEqualTo(1L);
//        assertThat(player.getResidence().getFloor()).isEqualTo(RESIDENCE_5FLOOR);
//    }
//
//    @Test
//    public void shouldMapToResidence() {
//        residence = residenceMapper.toResidence(residenceDTO);
//        assertThat(residence.getBuilding()).isEqualTo(RESIDENCE_BUILDING_EMPECINADO_21);
//        assertThat(residence.getFloor()).isEqualTo(RESIDENCE_5FLOOR);
//        assertThat(residence.getLetter()).isEqualTo(RESIDENCE_LETTER_A);
//    }

}
