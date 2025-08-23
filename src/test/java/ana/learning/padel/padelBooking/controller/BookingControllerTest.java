package ana.learning.padel.padelBooking.controller;

import ana.learning.padel.padelBooking.model.Booking;
import ana.learning.padel.padelBooking.model.Player;
import ana.learning.padel.padelBooking.model.Residence;
import ana.learning.padel.padelBooking.repository.BookingRepository;
import ana.learning.padel.padelBooking.service.BookingService;
import ana.learning.padel.padelBooking.service.PlayerService;
import ana.learning.padel.padelBooking.service.ResidenceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


import java.time.LocalDate;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class BookingControllerTest {


    private final LocalDate TODAY = LocalDate.now();
    private static final Booking.TimeSlot SLOT = Booking.TimeSlot.TWO_PM;
    private static final String NAME_OF_PLAYER1 = "Ana";
    private static final Residence.Building RESIDENCE_BUILDING_EMPECINADO21 = Residence.Building.JUAN_MARTIN_EMPECINADO_21;
    private static final Residence.Floor RESIDENCE_5FLOOR = Residence.Floor.FIFTH;
    private static final Residence.Letter RESIDENCE_LETTER_A = Residence.Letter.A;

    private static final Logger log = LoggerFactory.getLogger(BookingControllerTest.class);
    final MockMvc mockMvc;
    private final BookingService bookingService;
    private final BookingRepository bookingRepository;
    private final ResidenceService residenceService;
    private final PlayerService playerService;
    Player player;
    Residence residence;
    Booking savedBooking;


    public BookingControllerTest(MockMvc mockMvc, BookingService bookingService, ResidenceService residenceService, PlayerService playerService, BookingRepository bookingRepository) {
        this.mockMvc = mockMvc;
        this.bookingService = bookingService;
        this.residenceService = residenceService;
        this.playerService = playerService;
        this.bookingRepository = bookingRepository;
    }


    @BeforeEach
    public void setUp() {

        log.info("\n ***Entrando en setUp()");
        bookingRepository.deleteAll();

        residence = new Residence();
        residence.setLetter(RESIDENCE_LETTER_A);
        residence.setFloor(RESIDENCE_5FLOOR);
        residence.setBuilding(RESIDENCE_BUILDING_EMPECINADO21);
        Residence savedResidence = residenceService.saveResidence(residence);

        log.info("\n *** savedResidence = " + savedResidence);

        player = new Player();
        player.setName(NAME_OF_PLAYER1);
        player.setResidence(savedResidence);
        Player savedPlayer = playerService.savePlayer(player);

        log.info("\n *** savedPlayer = " + savedPlayer);


        Booking booking = new Booking();
        booking.setBookingDate(TODAY);
        booking.setTimeSlot(SLOT);
//        booking.setBookingOwner(savedPlayer);
        // Cuando guardo un booking con un playerOwner, debería ir despues a Player para persistir esa booking.

        savedBooking = bookingService.saveBooking(booking);

        log.info("\n *** savedBooking = " + savedBooking);



    }

    @Test
    public void shouldReturnEmptyListWhenThereIsNoBookings() throws Exception {

        bookingRepository.deleteAll();

        mockMvc.perform(get("/api/v1/bookings")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(org.springframework.http.MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0)));

    }

    @Test
    public void shouldReturnABookingListWhenThereAreBookings() throws Exception {

        MvcResult result = mockMvc.perform(get("/api/v1/bookings"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(org.springframework.http.MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].bookingDate").value(TODAY.toString()))
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();
        log.info("\n*** Response (GET): " + jsonResponse);

    }

    @Test
    public void shouldReturnBookingWhenConsultingAnExistingBooking() throws Exception {
        Long id = savedBooking.getId();
        MvcResult result = mockMvc.perform(get("/api/v1/bookings/" + id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(org.springframework.http.MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.bookingDate").value(TODAY.toString()))
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();
        log.info("\n*** Response de get by existing Id: " + jsonResponse);
    }

    // sería útil saber si está available? Tengo un metodo en calendar y otro en booking.




    @Test
    public void shouldReturn404NotFoundWhenConsultingANonExistingBooking() throws Exception {
        Long nonExistingId = 1L;
        MvcResult result = mockMvc.perform(get("/api/v1/bookings/" + nonExistingId))
                .andExpect(status().isNotFound())
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();
        log.info("\n*** Response de get by Non existing Id: " + jsonResponse);
    }
}
