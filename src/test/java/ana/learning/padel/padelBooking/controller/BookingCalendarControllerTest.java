package ana.learning.padel.padelBooking.controller;


import ana.learning.padel.padelBooking.DTO.CreateCalendarRequest;
import ana.learning.padel.padelBooking.model.BookingCalendar;
import ana.learning.padel.padelBooking.model.Player;
import ana.learning.padel.padelBooking.model.Residence;
import ana.learning.padel.padelBooking.repository.BookingCalendarRepository;
import ana.learning.padel.padelBooking.service.BookingCalendarService;
import ana.learning.padel.padelBooking.service.PlayerService;
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
    final MockMvc mockMvc;
    final ObjectMapper objectMapper;
    private final Logger log = LoggerFactory.getLogger(BookingCalendarControllerTest.class);


    public BookingCalendarControllerTest(BookingCalendarService bookingCalendarService, BookingCalendarRepository bookingCalendarRepository, MockMvc mockMvc, ObjectMapper objectMapper, PlayerService playerService){
        this.bookingCalendarService = bookingCalendarService;
        this.bookingCalendarRepository = bookingCalendarRepository;
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
        this.playerService = playerService;
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






//    @Test
//    public void shouldReserveAnAvailableBookingAsAPlayerWithResidence() throws Exception {
//        BookingCalendar calendar = new BookingCalendar();
//        calendar.setStartDay(TODAY);
//        bookingCalendarService.saveBookingCalendar(calendar);
//
//        Residence residence = new Residence();
//        residence.setBuilding(RESIDENCE_BUILDING_EMPECINADO21);
//        residence.setFloor(RESIDENCE_5FLOOR);
//        residence.setLetter(RESIDENCE_LETTER_A);
//
//        Player player = new Player();
//        player.setName(NAME_OF_PLAYER1);
//        player.setResidence(residence);
//        Player savedPlayer = playerService.savePlayer(player);
//
//        Long id = savedPlayer.getId();
//
//        MvcResult result = mockMvc.perform(get("/api/v1/booking-calendars/idBooking/")
//                        .content()
//                        .contentType((MediaType.APPLICATION_JSON)))
//                .andExpect(status().isOk())
//                .andExpect()
//
//        MvcResult result = mockMvc.perform(get("/api/v1/booking-calendars"))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$", hasSize(1)))
//                .andReturn();
//
//        String jsonResponse = result.getResponse().getContentAsString();
//        log.info("\n*** Response (GET): " + jsonResponse);
//
//
//        log.info("Test in progress");
//    }



}
