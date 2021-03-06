package com.meli.api.controller;

import com.google.common.collect.Lists;
import com.meli.api.model.Seat;
import com.meli.api.model.Show;
import com.meli.api.model.dto.BookingDTO;
import com.meli.api.model.dto.FilterDTO;
import com.meli.api.model.exception.MeliShowException;
import com.meli.api.service.ShowService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
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
    private static final Calendar calendar = Calendar.getInstance();

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
        seat.setShowDate(new Date(System.currentTimeMillis()));
        seats = Lists.newArrayList(seat);
    }

    @Test
    void getShows_responseOK() {
        when(service.getShows(any(FilterDTO.class))).thenReturn(shows);

        ResponseEntity<List<Show>> response = mainController.getShows(new FilterDTO());
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    void getShows_responseNOTFOUND() {
        when(service.getShows(any(FilterDTO.class))).thenReturn(Lists.newArrayList());

        ResponseEntity<List<Show>> response = mainController.getShows(new FilterDTO());
        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    void getShows_response_WrongDate() {
        Date now = calendar.getTime();
        calendar.add(Calendar.DATE, -1);
        Date yesterday = calendar.getTime();
        FilterDTO filter = new FilterDTO();
        filter.setStartDate(now);
        filter.setEndDate(yesterday);

        when(service.getShows(any(FilterDTO.class))).thenCallRealMethod();

        ResponseEntity<List<Show>> response = mainController.getShows(filter);
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
        assertNull(response.getBody());
    }

    @Test
    void getShows_response_WrongPrice() {
        FilterDTO filter = new FilterDTO();
        filter.setBottomPrice(2F);
        filter.setTopPrice(1F);

        when(service.getShows(any(FilterDTO.class))).thenCallRealMethod();

        ResponseEntity<List<Show>> response = mainController.getShows(filter);
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
        assertNull(response.getBody());
    }

    @Test
    void getEvents_responseOK() {
        when(service.getSeats(any(Long.class), any(FilterDTO.class))).thenReturn(seats);

        ResponseEntity<List<Seat>> response = mainController.getSeats(shows.get(0).getId(), new FilterDTO());
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    void getEvents_responseNOTFOUND() {
        when(service.getSeats(any(Long.class), any(FilterDTO.class))).thenReturn(Lists.newArrayList());

        ResponseEntity<List<Seat>> response = mainController.getSeats(WRONG_ID, new FilterDTO());
        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    void getEvents_response_WrongDate() {
        Date now = calendar.getTime();
        calendar.add(Calendar.DATE, -1);
        Date yesterday = calendar.getTime();
        FilterDTO filter = new FilterDTO();
        filter.setStartDate(now);
        filter.setEndDate(yesterday);

        when(service.getSeats(any(Long.class), any(FilterDTO.class))).thenCallRealMethod();

        ResponseEntity<List<Seat>> response = mainController.getSeats(shows.get(0).getId(), filter);
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
        assertNull(response.getBody());
    }

    @Test
    void getEvents_response_WrongPrice() {
        FilterDTO filter = new FilterDTO();
        filter.setBottomPrice(2F);
        filter.setTopPrice(1F);

        when(service.getSeats(any(Long.class), any(FilterDTO.class))).thenCallRealMethod();

        ResponseEntity<List<Seat>> response = mainController.getSeats(shows.get(0).getId(), filter);
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
        assertNull(response.getBody());
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
