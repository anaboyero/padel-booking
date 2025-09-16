package ana.learning.padel.padelBooking.controller;


import ana.learning.padel.padelBooking.DTO.CreateCalendarRequestDTO;
import ana.learning.padel.padelBooking.mappers.PlayerMapper;
import ana.learning.padel.padelBooking.model.Booking;
import ana.learning.padel.padelBooking.model.BookingCalendar;
import ana.learning.padel.padelBooking.model.Player;
import ana.learning.padel.padelBooking.model.Residence;
import ana.learning.padel.padelBooking.repository.BookingCalendarRepository;
import ana.learning.padel.padelBooking.service.BookingCalendarService;
import ana.learning.padel.padelBooking.service.PlayerService;
import ana.learning.padel.padelBooking.service.ResidenceService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDate;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class BookingCalendarControllerTest {

    private static final LocalDate TODAY = LocalDate.now();
    private static final LocalDate TOMORROW = LocalDate.now().plusDays(1);
    private static final LocalDate AFTER_TOMORROW = LocalDate.now().plusDays(2);
    private static final String NAME_OF_PLAYER1 = "Ana";
    private static final Residence.Building RESIDENCE_BUILDING_EMPECINADO21 = Residence.Building.JUAN_MARTIN_EMPECINADO_21;
    private static final Residence.Floor RESIDENCE_5FLOOR = Residence.Floor.FIFTH;
    private static final Residence.Letter RESIDENCE_LETTER_A = Residence.Letter.A;

    final BookingCalendarService bookingCalendarService;
    final BookingCalendarRepository bookingCalendarRepository;
    final PlayerService playerService;
    private final ResidenceService residenceService;
    final MockMvc mockMvc;
    final ObjectMapper objectMapper;
    private final PlayerMapper playerMapper;

    private final Logger log = LoggerFactory.getLogger(BookingCalendarControllerTest.class);

    public BookingCalendarControllerTest(BookingCalendarService bookingCalendarService, BookingCalendarRepository bookingCalendarRepository, ResidenceService residenceService, MockMvc mockMvc, ObjectMapper objectMapper, PlayerService playerService, PlayerMapper playerMapper){
        this.bookingCalendarService = bookingCalendarService;
        this.bookingCalendarRepository = bookingCalendarRepository;
        this.residenceService = residenceService;
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
        this.playerService = playerService;
        this.playerMapper = playerMapper;
    }

    @BeforeEach
    public void cleanUp() {
        bookingCalendarRepository.deleteAll();
    }

    @Test
    public void shouldCreateCalendarWithStartDay() throws Exception{

        CreateCalendarRequestDTO createCalendarRequestDTO = new CreateCalendarRequestDTO(TODAY);

        MvcResult result = mockMvc.perform(post("/api/v1/booking-calendars")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createCalendarRequestDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.startDay").value(TODAY.toString()))
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();
        log.info("\n*** Response (POST): " + jsonResponse);
    }

    @Test
    public void shouldReturnEmptyListWhenThereIsNoCalendar() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/v1/booking-calendars"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0)))
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();
        log.info("\n*** Response (GET): " + jsonResponse);
    }

    @Test
    public void shouldReturnListOfOneWhenThereIsACalendar() throws Exception {

        BookingCalendar calendar = new BookingCalendar();
        bookingCalendarService.saveBookingCalendar(calendar);

        MvcResult result = mockMvc.perform(get("/api/v1/booking-calendars"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();
        log.info("\n*** Response (GET): " + jsonResponse);
    }

    @Test
    public void shouldReturnListOf3BookingCalendars() throws Exception {

        BookingCalendar bookingCalendarToday = new BookingCalendar();
        bookingCalendarToday.setStartDay(TODAY);
        bookingCalendarService.saveBookingCalendar(bookingCalendarToday);

        BookingCalendar bookingCalendarTomorrow = new BookingCalendar();
        bookingCalendarTomorrow.setStartDay(TOMORROW);
        bookingCalendarService.saveBookingCalendar(bookingCalendarTomorrow);

        BookingCalendar bookingCalendarAfterTomorrow = new BookingCalendar();
        bookingCalendarAfterTomorrow.setStartDay(AFTER_TOMORROW);
        bookingCalendarService.saveBookingCalendar(bookingCalendarAfterTomorrow);

        MvcResult result = mockMvc.perform(get("/api/v1/booking-calendars"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(org.springframework.http.MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].startDay").value(TODAY.toString()))
                .andExpect(jsonPath("$[1].startDay").value(TOMORROW.toString()))
                .andExpect(jsonPath("$[2].startDay").value(AFTER_TOMORROW.toString()))
                .andExpect(jsonPath("$", hasSize(3)))
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();
        log.info("\n*** Response de get available bookings " + jsonResponse);
    }

  @Test
    public void shouldReserveAnAvailableBookingByAPlayerWithResidence() throws Exception {
        // Arrange
        BookingCalendar calendar = new BookingCalendar();
        calendar.setStartDay(TODAY);
        BookingCalendar savedCalendar = bookingCalendarService.saveBookingCalendar(calendar);
        Long calendarId = savedCalendar.getId();

        Residence residence = new Residence();
        residence.setBuilding(RESIDENCE_BUILDING_EMPECINADO21);
        residence.setFloor(RESIDENCE_5FLOOR);
        residence.setLetter(RESIDENCE_LETTER_A);
        Residence savedResidence = residenceService.saveResidence(residence);

        Player player = new Player();
        player.setName(NAME_OF_PLAYER1);
        player.setResidence(savedResidence);
        Player savedPlayer = playerService.savePlayer(player);

        Booking booking = savedCalendar.getAvailableBookings().get(0);
        Long bookingId = booking.getId();

      // Act & Assert

        MvcResult result = mockMvc.perform(post("/api/v1/booking-calendars/{calendarId}/bookings/{bookingId}", calendarId, bookingId)
                        .content(objectMapper.writeValueAsString(playerMapper.toDTO(savedPlayer)))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(bookingId))
                .andExpect(jsonPath("$.bookingOwner.id").value(savedPlayer.getId()))
                .andReturn();

        // Logging

      String jsonResponse = result.getResponse().getContentAsString();
      log.info("\n*** RESPONSE: " + jsonResponse);
    }

    @Test
    public void shouldNotReserveAnAvailableBookingByAPlayerWithNoResidence() throws Exception {

        // Arrange
        BookingCalendar calendar = new BookingCalendar();
        calendar.setStartDay(TODAY);
        BookingCalendar savedCalendar = bookingCalendarService.saveBookingCalendar(calendar);
        Long calendarId = savedCalendar.getId();

        Player player = new Player();
        player.setName(NAME_OF_PLAYER1);
        Player savedPlayer = playerService.savePlayer(player);

        Booking availableBooking = savedCalendar.getAvailableBookings().get(0);
        Long availableBookingId = availableBooking.getId();

        // Act & Assert

        MvcResult result = mockMvc.perform(post("/api/v1/booking-calendars/{calendarId}/bookings/{availableBookingId}", calendarId, availableBookingId)
                        .content(objectMapper.writeValueAsString(playerMapper.toDTO(savedPlayer)))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        // Logging

        String jsonResponse = result.getResponse().getContentAsString();
        log.info("\n*** RESPONSE: " + jsonResponse);
    }

//    @Test
//    public void shouldNotReserveAnUnavailableBookingByAPlayerWithResidence() throws Exception {
//    }

}
