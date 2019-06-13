package app.pinjamruang.reservation.controller;

import app.pinjamruang.reservation.model.Reservation;
import app.pinjamruang.reservation.service.ReservationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
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
        when(service.getAllReservations()).thenReturn(reservations);

        mvc.perform(get("/reservations/").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0)));
    }

}
