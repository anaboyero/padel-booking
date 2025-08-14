package ana.learning.padel.padelBooking.controller;


import ana.learning.padel.padelBooking.DTO.CreateCalendarRequest;
import ana.learning.padel.padelBooking.model.BookingCalendar;
import ana.learning.padel.padelBooking.repository.BookingCalendarRepository;
import ana.learning.padel.padelBooking.service.BookingCalendarService;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class BookingCalendarControllerTest {

    private static final LocalDate TODAY = LocalDate.now();

    final BookingCalendarService bookingCalendarService;
    final BookingCalendarRepository bookingCalendarRepository;
    final MockMvc mockMvc;
    final ObjectMapper objectMapper;
    private final Logger log = LoggerFactory.getLogger(BookingCalendarControllerTest.class);

    public BookingCalendarControllerTest(BookingCalendarService bookingCalendarService, BookingCalendarRepository bookingCalendarRepository, MockMvc mockMvc, ObjectMapper objectMapper){
        this.bookingCalendarService = bookingCalendarService;
        this.bookingCalendarRepository = bookingCalendarRepository;
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }


    @Test
    public void shouldCreateCalendarWithStartDay() throws Exception{

        log.info("\n***" + TODAY.toString());

        CreateCalendarRequest createCalendarRequest = new CreateCalendarRequest(TODAY);

        MvcResult result = mockMvc.perform(post("/api/v1/booking-calendars")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createCalendarRequest)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.startDay").value(TODAY.toString()))
                .andReturn();

        // Imprimir el contenido como String
        String jsonResponse = result.getResponse().getContentAsString();
        log.info("\n*** RESPUESTA DEL POST: " + jsonResponse);
    }

}
