package ana.learning.padel.padelBooking.controller;


import ana.learning.padel.padelBooking.DTO.CreateCalendarRequestDTO;
import ana.learning.padel.padelBooking.mappers.PlayerMapper;
import ana.learning.padel.padelBooking.model.Booking;
import ana.learning.padel.padelBooking.model.BookingCalendar;
import ana.learning.padel.padelBooking.model.Player;
import ana.learning.padel.padelBooking.model.Residence;
import ana.learning.padel.padelBooking.repository.BookingCalendarRepository;
import ana.learning.padel.padelBooking.service.BookingCalendarService;
import ana.learning.padel.padelBooking.service.BookingService;
import ana.learning.padel.padelBooking.service.PlayerService;
import ana.learning.padel.padelBooking.service.ResidenceService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
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
    final BookingService bookingService;
    private final ResidenceService residenceService;
    final MockMvc mockMvc;
    final ObjectMapper objectMapper;
    private final PlayerMapper playerMapper;

    Residence savedResidence;
    Player savedPlayer;

    private final Logger log = LoggerFactory.getLogger(BookingCalendarControllerTest.class);

    public BookingCalendarControllerTest(BookingCalendarService bookingCalendarService, BookingCalendarRepository bookingCalendarRepository, BookingService bookingService, ResidenceService residenceService, MockMvc mockMvc, ObjectMapper objectMapper, PlayerService playerService, PlayerMapper playerMapper){
        this.bookingCalendarService = bookingCalendarService;
        this.bookingCalendarRepository = bookingCalendarRepository;
        this.bookingService = bookingService;
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

        /// GIVEN A START DAY

        CreateCalendarRequestDTO createCalendarRequestDTO = new CreateCalendarRequestDTO(TODAY);

        MvcResult result = mockMvc.perform(post("/api/v1/booking-calendars")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createCalendarRequestDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.startDay").isNotEmpty())
                .andExpect(jsonPath("$.availableBookingsId").isNotEmpty())
                .andExpect(jsonPath("$.reservedBookingsId").isEmpty())
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

        List<BookingCalendar> calendars = createConsecutiveBookingCalendars(1); // crea y persiste 1 calendario para esta semana

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

        createConsecutiveBookingCalendars(3); // crea y persiste 3 calendarios en tres semanas consecutivas a partir de hoy

        MvcResult result = mockMvc.perform(get("/api/v1/booking-calendars"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(org.springframework.http.MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].startDay").value(TODAY.toString()))
                .andExpect(jsonPath("$[1].startDay").value(TODAY.plusDays(7).toString()))
                .andExpect(jsonPath("$[2].startDay").value(TODAY.plusDays(14).toString()))
                .andExpect(jsonPath("$", hasSize(3)))
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();
        log.info("\n*** Response de get available bookings " + jsonResponse);
    }

    @Test
    public void shouldDeleteAllCalendars() throws Exception{
        /// GIVEN 2 calendars in the repository
        BookingCalendar bookingCalendarToday = new BookingCalendar();
        bookingCalendarToday.setStartDay(TODAY);
        bookingCalendarService.saveBookingCalendar(bookingCalendarToday);

        BookingCalendar bookingCalendarNextWeek = new BookingCalendar();
        bookingCalendarNextWeek.setStartDay(TODAY.plusDays(7));
        bookingCalendarService.saveBookingCalendar(bookingCalendarNextWeek);

        /// WHEN deleting all calendars

        mockMvc.perform(delete("/api/v1/booking-calendars"))
                .andExpect(status().isNoContent());

        /// THEN the repository is empty

        assertThat(bookingCalendarService.getAllBookingCalendars().size()).isEqualTo(0);

    }


//  @Disabled("Este test está desactivado temporalmente")
//  @Test
//    public void shouldReserveAnAvailableBookingByAPlayerWithResidence() throws Exception {
//      BookingCalendar calendar = createConsecutiveBookingCalendars(1).get(0);
//      savedPlayer = createPlayerWithResidence();
//
//      Booking firstAvailableBooking = calendar.getAvailableBookings().get(0);
//      Long bookingId = firstAvailableBooking.getId();
//      Long playerId = savedPlayer.getId();
//
//      MvcResult result = mockMvc.perform(post("/api/v1/booking-calendars/{calendarId}/bookings/{bookingId}", calendar.getId(), firstAvailableBooking.getId())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(playerMapper.toDTO(savedPlayer))))
//                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                        .andExpect(jsonPath("$.id").value(bookingId))
//                        .andExpect(jsonPath("$.bookingOwner.id").value(playerId))
//                        .andReturn();
//
//      Booking savedBooking = bookingService.getBookingById(bookingId).get();
//      assertThat(savedBooking.getBookingOwner().getId()).isEqualTo(playerId);
//
//      String jsonResponse = result.getResponse().getContentAsString();
//      log.info("\n*** RESPONSE: " + jsonResponse);
//    }
//
//    @Disabled("Este test está desactivado temporalmente")
//    @Test
//    public void shouldNotReserveAnAvailableBookingByAPlayerWithNoResidence() throws Exception {
//
//        // Arrange
//        BookingCalendar calendar = new BookingCalendar();
//        calendar.setStartDay(TODAY);
//        BookingCalendar savedCalendar = bookingCalendarService.saveBookingCalendar(calendar);
//        Long calendarId = savedCalendar.getId();
//
//        Player player = new Player();
//        player.setName(NAME_OF_PLAYER1);
//        Player savedPlayer = playerService.savePlayer(player);
//
//        Booking availableBooking = savedCalendar.getAvailableBookings().get(0);
//        Long availableBookingId = availableBooking.getId();
//
//        // Act & Assert
//
//        MvcResult result = mockMvc.perform(post("/api/v1/booking-calendars/{calendarId}/bookings/{availableBookingId}", calendarId, availableBookingId)
//                        .content(objectMapper.writeValueAsString(playerMapper.toDTO(savedPlayer)))
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isBadRequest())
//                .andReturn();
//
//        // Logging
//
//        String jsonResponse = result.getResponse().getContentAsString();
//        log.info("\n*** RESPONSE: " + jsonResponse);
//    }
//
//
//
////    @Test
////    public void shouldNotReserveAnUnavailableBookingByAPlayerWithResidence() throws Exception {
////    }
//
    private List<BookingCalendar> createConsecutiveBookingCalendars(int num){
        List<BookingCalendar>  createdCalendars = new ArrayList<>();
        if (num<0) {return createdCalendars;}

        for (int i=0; i<num; i++) {
            BookingCalendar bookingCalendar = new BookingCalendar();
            bookingCalendar.setStartDay(TODAY.plusDays(7L *i));
            BookingCalendar savedCalendar = bookingCalendarService.saveBookingCalendar(bookingCalendar);
            createdCalendars.add(savedCalendar);
        }
        return createdCalendars;
    }

    private Player createPlayerWithResidence() {
        Residence residence = new Residence();
        residence.setBuilding(RESIDENCE_BUILDING_EMPECINADO21);
        residence.setFloor(RESIDENCE_5FLOOR);
        residence.setLetter(RESIDENCE_LETTER_A);
        savedResidence = residenceService.saveResidence(residence);

        Player player = new Player();
        player.setName(NAME_OF_PLAYER1);
        player.setResidence(savedResidence);
        savedPlayer = playerService.savePlayer(player);
        return savedPlayer;
    }

}
