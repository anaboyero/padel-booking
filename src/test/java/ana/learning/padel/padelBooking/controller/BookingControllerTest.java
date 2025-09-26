package ana.learning.padel.padelBooking.controller;

import ana.learning.padel.padelBooking.mappers.PlayerMapper;
import ana.learning.padel.padelBooking.model.Booking;
import ana.learning.padel.padelBooking.model.BookingCalendar;
import ana.learning.padel.padelBooking.model.Player;
import ana.learning.padel.padelBooking.model.Residence;
import ana.learning.padel.padelBooking.repository.BookingRepository;
import ana.learning.padel.padelBooking.service.BookingService;
import ana.learning.padel.padelBooking.service.PlayerService;
import ana.learning.padel.padelBooking.service.ResidenceService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


import java.time.LocalDate;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
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
    private final MockMvc mockMvc;
    private final BookingService bookingService;
    private final BookingRepository bookingRepository;



    public BookingControllerTest(MockMvc mockMvc, BookingService bookingService, BookingRepository bookingRepository) {
        this.mockMvc = mockMvc;
        this.bookingService = bookingService;
        this.bookingRepository = bookingRepository;
    }

    @BeforeEach
    public void cleanUp() {
        bookingRepository.deleteAll();
    }

    @ParameterizedTest
    @CsvSource({
            "0,0",   // sin bookings → lista vacía
            "3,3"    // 3 bookings creados → lista con 3
    })
    public void shouldReturnABookingList_WhenThereAreBookings(int bookingsToCreate, int expectedSize) throws Exception {

        if (bookingsToCreate>0) {
            createConsecutiveBookings(bookingsToCreate); // crea bookings en días consecutivos a partir de hoy
        }

        MvcResult result = mockMvc.perform(get("/api/v1/bookings"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(org.springframework.http.MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(expectedSize)))
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();
        log.info("\n*** Response (GET): " + jsonResponse);
    }


    @Test
    public void shouldReturnBooking_WhenConsultingAnExistingBooking() throws Exception {
        createConsecutiveBookings(3); // crea y persiste 3 bookings en días consecutivos a partir de hoy

        MvcResult result = mockMvc.perform(get("/api/v1/bookings/{id}", bookingService.getAllBookings().get(0).getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(org.springframework.http.MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.bookingDate").value(TODAY.toString()))
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();
        log.info("\n*** Response de get by existing Id: " + jsonResponse);
    }


    @Test
    public void shouldReturn404NotFoundWhenConsultingANonExistingBooking() throws Exception {
        Long nonExistingId = 101L;
        MvcResult result = mockMvc.perform(get("/api/v1/bookings/" + nonExistingId))
                .andExpect(status().isNotFound())
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();
        log.info("\n*** Response de get by Non existing Id: " + jsonResponse);
    }

    private void createConsecutiveBookings(int num){
        for (int i=0; i<num; i++) {
            Booking booking = new Booking();
            booking.setBookingDate(TODAY.plusDays(i));
            booking.setTimeSlot(SLOT);
            bookingService.saveBooking(booking);
        }
    }
}
