package ana.learning.padel.padelBooking.mappers;

import ana.learning.padel.padelBooking.DTO.BookingCalendarDTO;
import ana.learning.padel.padelBooking.DTO.BookingDTO;
import ana.learning.padel.padelBooking.DTO.PlayerDTO;
import ana.learning.padel.padelBooking.DTO.ResidenceDTO;
import ana.learning.padel.padelBooking.model.Booking;
import ana.learning.padel.padelBooking.model.BookingCalendar;
import ana.learning.padel.padelBooking.model.Player;
import ana.learning.padel.padelBooking.model.Residence;
import ana.learning.padel.padelBooking.service.BookingCalendarService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
@SpringBootTest
public class MapperTest {

    @Autowired
    PlayerMapper playerMapper;
    @Autowired
    BookingMapper bookingMapper;
    @Autowired
    ResidenceMapper residenceMapper;
    @Autowired
    BookingCalendarMapper bookingCalendarMapper;
    @Autowired
    BookingCalendarMapperHelper bookingCalendarMapperHelper;
    @Autowired
    BookingCalendarService bookingCalendarService;

    private Player player;
    private PlayerDTO playerDTO;
    private Residence residence;
    private ResidenceDTO residenceDTO;

    private static final Residence.Building RESIDENCE_BUILDING_EMPECINADO_21 = Residence.Building.JUAN_MARTIN_EMPECINADO_21;
    private static final Residence.Floor RESIDENCE_5FLOOR = Residence.Floor.FIFTH;
    private static final Residence.Letter RESIDENCE_LETTER_A = Residence.Letter.A;
    private static final Booking.TimeSlot SLOT = Booking.TimeSlot.TWO_PM;
    private static final LocalDate TODAY = LocalDate.now();
    private static final int MAX_NUM_OF_SLOTS_PER_WEEK = 5*7;

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

    @Test
    public void shouldMapBookingToDTO() {
        ///  GIVEN AN AVAILABLE BOOKING
        Booking availableBooking = new Booking();
        availableBooking.setTimeSlot(SLOT);
        availableBooking.setBookingDate(TODAY);

        BookingDTO dto = bookingMapper.toDTO(availableBooking);

        assertThat(dto.getBookingDate()).isEqualTo(TODAY);
        assertThat(dto.getTimeSlot()).isEqualTo(SLOT);
    }

    @Test
    public void shouldMapBookingToDTO_reservation() {
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

    @Test
    public void shouldMapCalendarToDTO_noReservations() {
        ///  given a calendar
        BookingCalendar calendar = new BookingCalendar();
        calendar.setStartDay(TODAY);
        calendar.setId(100L);
        assertThat(calendar.getAvailableBookings().size()).isEqualTo(0);
        assertThat(calendar.getReservedBookings().size()).isEqualTo(0);

        BookingCalendarDTO dto = bookingCalendarMapperHelper.customToDTO(calendar);

        // tengo una lista de bookingDTO, no de long.

        assertThat(dto.getAvailableBookings().size()).isEqualTo(0);
        assertThat(dto.getReservedBookings().size()).isEqualTo(0);
        assertThat(dto.getStartDay()).isEqualTo(TODAY);
        assertThat(dto.getId()).isEqualTo(100L);
    }

    @Test
    public void  shouldMapCalendarToDTO_WithBookings() {
        BookingCalendar calendar = bookingCalendarService.createBookingCalendar(TODAY);
        List<Booking> availableBookings = calendar.getAvailableBookings();
        assertThat(calendar.getAvailableBookings().size()).isEqualTo(MAX_NUM_OF_SLOTS_PER_WEEK);
        assertThat(calendar.getReservedBookings().size()).isEqualTo(0);

        BookingCalendarDTO dto = bookingCalendarMapperHelper.customToDTO(calendar);

        assertThat(dto.getAvailableBookings().size()).isEqualTo(MAX_NUM_OF_SLOTS_PER_WEEK);
        assertThat(dto.getReservedBookings().size()).isEqualTo(0);
        assertThat(dto.getStartDay()).isEqualTo(TODAY);
        assertThat(dto.getId()).isEqualTo(calendar.getId());
        assertThat(dto.getAvailableBookings().get(0).getId()).isEqualTo(availableBookings.get(0).getId());
        assertThat(dto.getAvailableBookings().get(0).getTimeSlot()).isEqualTo(availableBookings.get(0).getTimeSlot());
        assertThat(dto.getAvailableBookings().get(0).getBookingDate()).isEqualTo(availableBookings.get(0).getBookingDate());
    }

    @Test
    public void  shouldMapCalendarToDTO_WithReservation() {
        BookingCalendar calendar = bookingCalendarService.createBookingCalendar(TODAY);
        List<Booking> availableBookings = calendar.getAvailableBookings();
        assertThat(calendar.getAvailableBookings().size()).isEqualTo(MAX_NUM_OF_SLOTS_PER_WEEK);
        assertThat(calendar.getReservedBookings().size()).isEqualTo(0);

        BookingCalendarDTO dto = bookingCalendarMapperHelper.customToDTO(calendar);

        assertThat(dto.getAvailableBookings().size()).isEqualTo(MAX_NUM_OF_SLOTS_PER_WEEK);
        assertThat(dto.getReservedBookings().size()).isEqualTo(0);
        assertThat(dto.getStartDay()).isEqualTo(TODAY);
        assertThat(dto.getId()).isEqualTo(calendar.getId());
        assertThat(dto.getAvailableBookings().get(0).getId()).isEqualTo(availableBookings.get(0).getId());

    }

    @Test
    public void shouldMapToResidence() {
        residence = residenceMapper.toEntity(residenceDTO);
        assertThat(residence.getBuilding()).isEqualTo(RESIDENCE_BUILDING_EMPECINADO_21);
        assertThat(residence.getFloor()).isEqualTo(RESIDENCE_5FLOOR);
        assertThat(residence.getLetter()).isEqualTo(RESIDENCE_LETTER_A);
    }

    @Test
    public void shouldMapToResidenceDTO() {
        residence = residenceMapper.toEntity(residenceDTO);
        assertThat(residence.getBuilding()).isEqualTo(RESIDENCE_BUILDING_EMPECINADO_21);
        assertThat(residence.getFloor()).isEqualTo(RESIDENCE_5FLOOR);
        assertThat(residence.getLetter()).isEqualTo(RESIDENCE_LETTER_A);
    }


//    @Test
//    public void shouldMapPlayerToDTO_WithoutReservation() {
//        ///  GIVEN a player
//
//        Player player = new Player();
//        player.setName("Ana");
//        player.setId(10L);
//
//        Residence residence = new Residence();
//        residence.setFloor(RESIDENCE_5FLOOR);
//        residence.setBuilding(RESIDENCE_BUILDING_EMPECINADO_21);
//        residence.setLetter(Residence.Letter.A);
//        residence.setId(20L);
//
//        player.setResidence(residence);
//
//        ///  WHEN mapping it to Player DTO
//        PlayerDTO playerDTO  = playerMapper.toDTO(player);
//
//        /// THEN all the fields are transmited
//        assertThat(playerDTO.getName()).isEqualTo("Ana");
//        assertThat(playerDTO.getId()).isEqualTo(10L);
//        assertThat(playerDTO.getResidence().getFloor()).isEqualTo(RESIDENCE_5FLOOR);
//        assertThat(playerDTO.getResidence().getBuilding()).isEqualTo(RESIDENCE_BUILDING_EMPECINADO_21);
//        assertThat(playerDTO.getResidence().getLetter()).isEqualTo(RESIDENCE_LETTER_A);
//    }
//


}
