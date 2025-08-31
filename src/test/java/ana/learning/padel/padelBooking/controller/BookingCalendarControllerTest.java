package ana.learning.padel.padelBooking.controller;


import ana.learning.padel.padelBooking.DTO.CreateCalendarRequest;
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
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.bind.annotation.RestController;

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
    Long bookingCalendarId;


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
    public void setUp() {
        bookingCalendarRepository.deleteAll();
        log.info("\n*** Limpiando repositorios de Booking Calendar en @BeforeEach");
    }

    @Test
    public void shouldCreateCalendarWithStartDay() throws Exception{

        CreateCalendarRequest createCalendarRequest = new CreateCalendarRequest(TODAY);

        MvcResult result = mockMvc.perform(post("/api/v1/booking-calendars")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createCalendarRequest)))
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

        //
    }

//    @Test // ACASO NO VEMOS EXACTAMENTE ESTO CUANDO HACEMOS GET DE UN CALENDARIO????
//    public void shouldReturnAvailableBookings() throws Exception {
//        CreateCalendarRequest createCalendarRequest = new CreateCalendarRequest(TODAY);
//
//        Long calendarId = 1L;
//        MvcResult result = mockMvc.perform(get("/api/v1/booking-calendars/{calendarId}/available-bookings", calendarId))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(org.springframework.http.MediaType.APPLICATION_JSON))
////                .andExpect(jsonPath("$.bookingDate").value(TODAY.toString()))
//                .andExpect(jsonPath("$", hasSize(1)))
//                .andReturn();
//
//        String jsonResponse = result.getResponse().getContentAsString();
//        log.info("\n*** Response de get available bookings " + jsonResponse);
//    }

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

//  EN PROCESO
//
//
//  @Test
//    public void shouldReserveAnAvailableBookingAsAPlayerWithResidence() throws Exception {
//        BookingCalendar calendar = new BookingCalendar();
//        calendar.setStartDay(TODAY);
//        BookingCalendar savedCalendar = bookingCalendarService.saveBookingCalendar(calendar);
//
//        Residence residence = new Residence();
//        residence.setBuilding(RESIDENCE_BUILDING_EMPECINADO21);
//        residence.setFloor(RESIDENCE_5FLOOR);
//        residence.setLetter(RESIDENCE_LETTER_A);
//        Residence savedResidence = residenceService.saveResidence(residence);
//
//        Player player = new Player();
//        player.setName(NAME_OF_PLAYER1);
//        player.setResidence(savedResidence);
//        Player savedPlayer = playerService.savePlayer(player);
//
//        Long calendarId = savedCalendar.getId();
//        Booking booking = savedCalendar.getAvailableBookings().get(0);
//        Long bookingId = booking.getId();
//
//        log.info("\n  *** ESTOS SON LOS DATOS CON LOS QUE ENTRO AL TEST");
//        log.info(savedPlayer.toString());
//        log.info(savedResidence.toString());
//        log.info(savedCalendar.toString());
//        log.info(booking.toString());
//
//
//        MvcResult result = mockMvc.perform(post("/api/v1/booking-calendars/{calendarId}/bookings/{bookingId}", calendarId, bookingId)
//                        .content(objectMapper.writeValueAsString(playerMapper.toDTO(savedPlayer)))
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
////                .andExpect(jsonPath("$.bookingOwner").value(savedPlayer))
//                .andReturn();
//
//        String jsonResponse = result.getResponse().getContentAsString();
//        log.info("\n*** Debería devolverme el booking ya modificado con el nuevo player: " + jsonResponse);
//
//        log.info("Test in progress: RED");
//
//    }
//
//


}
