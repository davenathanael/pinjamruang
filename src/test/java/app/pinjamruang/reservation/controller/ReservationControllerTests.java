package app.pinjamruang.reservation.controller;

import app.pinjamruang.reservation.model.Reservation;
import app.pinjamruang.reservation.service.ReservationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static app.pinjamruang.reservation.ReservationTestHelper.*;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(ReservationController.class)
public class ReservationControllerTests {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private ReservationService service;

    @Test
    public void getAllReservations_noReservation_success() throws Exception {
        when(service.getAllReservations()).thenReturn(new ArrayList<>());

        mvc.perform(get("/reservations/").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void getAllReservations_notEmptyReservations_success() throws Exception {
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(createDummyReservation(createDummyRoom()));
        reservations.add(createDummyReservation(createDummyRoom()));

        when(service.getAllReservations()).thenReturn(reservations);

        mvc.perform(get("/reservations/").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].agenda", is(reservations.get(0).getAgenda())))
                .andExpect(jsonPath("$[1].agenda", is(reservations.get(1).getAgenda())));
    }

    @Test
    public void getReservationById_success() throws Exception {
        Reservation dummyReservation = createDummyReservation(createDummyRoom());
        when(service.getReservationById(1L)).thenReturn(dummyReservation);

        mvc.perform(get("/reservations/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.agenda", is(dummyReservation.getAgenda())))
                .andExpect(jsonPath("$.attendees", is(dummyReservation.getAttendees())))
                .andExpect(jsonPath("$.startDate", is(dummyReservation.getStartDate().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))))
                .andExpect(jsonPath("$.endDate", is(dummyReservation.getEndDate().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))))
        ;
    }

    @Test
    public void getReservationById_reservationNotFound_throwsResourceNotFoundException() throws Exception {
        when(service.getReservationById(1L)).thenThrow(new ResourceNotFoundException());

        mvc.perform(get("/reservations/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }


}
