package ana.learning.padel.padelBooking.mappers;

import ana.learning.padel.padelBooking.DTO.BookingCalendarDTO;
import ana.learning.padel.padelBooking.DTO.BookingDTO;
import ana.learning.padel.padelBooking.DTO.PlayerDTO;
import ana.learning.padel.padelBooking.DTO.ResidenceDTO;
import ana.learning.padel.padelBooking.model.Booking;
import ana.learning.padel.padelBooking.model.BookingCalendar;
import ana.learning.padel.padelBooking.model.Player;
import ana.learning.padel.padelBooking.model.Residence;
import ana.learning.padel.padelBooking.repository.BookingCalendarRepository;
import ana.learning.padel.padelBooking.repository.BookingRepository;
import ana.learning.padel.padelBooking.repository.PlayerRepository;
import ana.learning.padel.padelBooking.repository.ResidenceRepository;
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
    @Autowired
    PlayerRepository playerRepository;
    @Autowired
    ResidenceRepository residenceRepository;
    @Autowired
    BookingRepository bookingRepository;
    @Autowired
    BookingCalendarRepository bookingCalendarRepository;

    private Player player;
    private PlayerDTO playerDTO;
    private Residence residence;
    private ResidenceDTO residenceDTO;

    private static final Residence.Building RESIDENCE_BUILDING_EMPECINADO_21 = Residence.Building.JUAN_MARTIN_EMPECINADO_21;
    private static final Residence.Floor RESIDENCE_5FLOOR = Residence.Floor.FIFTH;
    private static final Residence.Letter RESIDENCE_LETTER_A = Residence.Letter.A;
    private static final Booking.TimeSlot SLOT = Booking.TimeSlot.ONE_PM;
    private static final LocalDate TODAY = LocalDate.now();
    private static final int MAX_NUM_OF_SLOTS_PER_WEEK = 5*7;

    @BeforeEach
    public void setUp() {
        residenceDTO = new ResidenceDTO();
        residenceDTO.setBuilding("JUAN_MARTIN_EMPECINADO_21");
        residenceDTO.setFloor("FIFTH");
        residenceDTO.setLetter("A");
    }


    @Test
    public void shouldMapBookingToDTO() {

        Booking availableBooking = bookingCalendarService.createBookingCalendar(TODAY).getAvailableBookings().get(0);

        BookingDTO dto = bookingMapper.toDTO(availableBooking);

        assertThat(dto.getId()).isEqualTo(availableBooking.getId());
        assertThat(dto.getBookingDate()).isEqualTo(TODAY);
        assertThat(dto.getTimeSlot()).isEqualTo(SLOT);
        assertThat(dto.getBookingOwnerId()).isNull();
        assertThat(dto.getCalendarId()).isEqualTo(availableBooking.getCalendar().getId());
    }

    @Test
    public void shouldMapBookingToDTO_reservation() {

        Booking booking = bookingCalendarService.createBookingCalendar(TODAY).getAvailableBookings().get(0);

        Residence residence = new Residence();
        residence.setBuilding(Residence.Building.JUAN_MARTIN_EMPECINADO_21);
        residence.setFloor(Residence.Floor.FIFTH);
        residence.setLetter(Residence.Letter.A);
        Residence savedResidence = residenceRepository.save(residence);


        Player player = new Player();
        player.setResidence(savedResidence);
        Player savedPlayer = playerRepository.save(player);

        Booking reservation = bookingCalendarService.reserveBooking(booking.getId(), player.getId()).get();

        BookingDTO dto = bookingMapper.toDTO(reservation);

        assertThat(dto.getBookingDate()).isEqualTo(TODAY);
        assertThat(dto.getTimeSlot()).isEqualTo(SLOT);
        assertThat(dto.getId()).isEqualTo(reservation.getId());
        assertThat(dto.getBookingOwnerId()).isEqualTo(savedPlayer.getId());
        assertThat(dto.getCalendarId()).isEqualTo(reservation.getCalendar().getId());
        System.out.println("\n ***** ");
        System.out.println(dto.toString());
        System.out.println("\n ***** ");
    }

    @Test
    public void shouldMapCalendarToDTO_noReservations() {
        ///  given a calendar

        BookingCalendar calendar = bookingCalendarService.createBookingCalendar(TODAY);
        assertThat(calendar.getAvailableBookings().size()).isEqualTo(MAX_NUM_OF_SLOTS_PER_WEEK);
        assertThat(calendar.getReservedBookings().size()).isEqualTo(0);

        ///  WHEN mapping it to DTO
        BookingCalendarDTO dto = bookingCalendarMapperHelper.customToDTO(calendar);

        ///  THEN I get a DTO with all the fields, and a list of bookingDTO ids

        assertThat(dto.getAvailableBookings().size()).isEqualTo(MAX_NUM_OF_SLOTS_PER_WEEK);
        assertThat(dto.getReservedBookings().size()).isEqualTo(0);
        assertThat(dto.getStartDay()).isEqualTo(TODAY);
        assertThat(dto.getId()).isEqualTo(calendar.getId());
    }

    @Test
    public void  shouldMapCalendarToDTO_WithBookings() {
        BookingCalendar calendar = bookingCalendarService.createBookingCalendar(TODAY);
        BookingCalendar savedCalendar = bookingCalendarService.saveBookingCalendar(calendar);
        List<Booking> availableBookings = savedCalendar.getAvailableBookings();
        assertThat(calendar.getAvailableBookings().size()).isEqualTo(MAX_NUM_OF_SLOTS_PER_WEEK);
        assertThat(calendar.getReservedBookings().size()).isEqualTo(0);
    }

    @Test
    public void shouldMapBookingToDTO_withCalendar() {
        ///  GIVEN AN AVAILABLE BOOKING
        Booking availableBooking = bookingCalendarService.createBookingCalendar(TODAY).getAvailableBookings().get(0);

        /// WHEN mapping it to BookingDTO
        BookingDTO dto = bookingMapper.toDTO(availableBooking);
        ///  IT RETURNS A BOOKINGDTO WITH ALL THE FIELDS
        assertThat(dto.getId()).isEqualTo(availableBooking.getId());
        assertThat(dto.getBookingDate()).isEqualTo(availableBooking.getBookingDate());
        assertThat(dto.getTimeSlot()).isEqualTo(availableBooking.getTimeSlot());
        assertThat(dto.getBookingOwnerId()).isNull();
        assertThat(dto.getCalendarId()).isEqualTo(availableBooking.getCalendar().getId());

    }

    @Test
    public void  shouldMapCalendarToDTO_WithReservation() {
        BookingCalendar savedCalendar = bookingCalendarService.createBookingCalendar(TODAY);
        List<Booking> availableBookings = savedCalendar.getAvailableBookings();
        assertThat(savedCalendar.getAvailableBookings().size()).isEqualTo(MAX_NUM_OF_SLOTS_PER_WEEK);
        assertThat(savedCalendar.getReservedBookings().size()).isEqualTo(0);

        BookingCalendarDTO dto = bookingCalendarMapperHelper.customToDTO(savedCalendar);

        assertThat(dto.getAvailableBookings().size()).isEqualTo(MAX_NUM_OF_SLOTS_PER_WEEK);
        assertThat(dto.getReservedBookings().size()).isEqualTo(0);
        assertThat(dto.getStartDay()).isEqualTo(TODAY);
        assertThat(dto.getId()).isEqualTo(savedCalendar.getId());
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

    @Test
    public void shouldMapPlayerToDTO_withReservation() {
        Booking booking = bookingCalendarService.createBookingCalendar(TODAY).getAvailableBookings().get(0);

        Residence residence = new Residence();
        residence.setBuilding(Residence.Building.JUAN_MARTIN_EMPECINADO_21);
        residence.setFloor(Residence.Floor.FIFTH);
        residence.setLetter(Residence.Letter.A);
        Residence savedResidence = residenceRepository.save(residence);

        Player player = new Player();
        player.setResidence(savedResidence);
        Player savedPlayer = playerRepository.save(player);

        Booking reservation = bookingCalendarService.reserveBooking(booking.getId(), savedPlayer.getId()).get();

        PlayerDTO dto = playerMapper.toDTO(reservation.getBookingOwner());

        assertThat(dto.getId()).isEqualTo(savedPlayer.getId());
        assertThat(dto.getBookings().size()).isEqualTo(1);

    }

}
