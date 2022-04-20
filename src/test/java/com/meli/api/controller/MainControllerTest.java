package com.meli.api.controller;

import com.google.common.collect.Lists;
import com.meli.api.model.BookingDTO;
import com.meli.api.model.Seat;
import com.meli.api.model.Show;
import com.meli.api.model.exception.MeliShowException;
import com.meli.api.service.ShowService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class MainControllerTest {

    @Autowired
    private MainController mainController;

    @MockBean
    private ShowService service;

    private List<Show> shows;
    private List<Seat> seats;

    private static final Long WRONG_ID = 0L;

    @BeforeEach
    public void setup() {
        Show show = new Show();
        show.setId(1L);
        show.setName("Test");
        shows = Lists.newArrayList(show);

        Seat seat = new Seat();
        seat.setId(1L);
        seat.setShowId(show.getId());
        seat.setRow("A");
        seat.setSeatNumbers(Lists.newArrayList("1,2,3"));
        seat.setSeatPrice(150.30F);
        seats = Lists.newArrayList(seat);
    }

    @Test
    void getShows_responseOK() {
        when(service.getShows()).thenReturn(shows);

        ResponseEntity<List<Show>> response = mainController.getShows();
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    void getEvents_responseOK() {
        when(service.getSeats(any(Long.class))).thenReturn(seats);

        ResponseEntity<List<Seat>> response = mainController.getSeats(shows.get(0).getId());
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    void getEvents_responseNOTFOUND() {
        when(service.getSeats(any(Long.class))).thenReturn(Lists.newArrayList());

        ResponseEntity<List<Seat>> response = mainController.getSeats(WRONG_ID);
        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    void postBook_responseCREATED() {
        doNothing().when(service).doBooking(any(BookingDTO.class));

        ResponseEntity<String> response = mainController.postBook(BookingDTO.builder().build());
        assertEquals(response.getStatusCode(), HttpStatus.CREATED);
    }

    @Test
    void postBook_responseBADREQUEST() {
        doThrow(new MeliShowException("Test error")).when(service).doBooking(any(BookingDTO.class));

        ResponseEntity<String> response = mainController.postBook(BookingDTO.builder().build());
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }
}
