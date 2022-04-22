package com.meli.api.service;

import com.google.common.collect.Lists;
import com.meli.api.model.Booking;
import com.meli.api.model.Seat;
import com.meli.api.model.Show;
import com.meli.api.model.dto.BookingDTO;
import com.meli.api.model.dto.BookingSeatDTO;
import com.meli.api.model.dto.FilterDTO;
import com.meli.api.repository.BookingRepository;
import com.meli.api.repository.SeatRepository;
import com.meli.api.repository.ShowRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Date;
import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ShowServiceTest {

    @Autowired
    private ShowService showService;

    @MockBean
    private ShowRepository showRepository;
    @MockBean
    private SeatRepository seatRepository;
    @MockBean
    private BookingRepository bookingRepository;

    private static final Long WRONG_ID = 0L;
    private static final Long RIGHT_ID = 1L;
    private static final String ROW = "A";
    private static final List<String> SEATS = Lists.newArrayList("1","2","3");
    private static final Float PRICE = 150.30F;
    private static final List<Long> ID_LIST = Lists.newArrayList(1L,2L);

    private List<Show> shows;
    private List<Seat> seats;
    private BookingDTO bookingDTO;
    private Seat seat;
    private Show show;

    @BeforeEach
    private void setup() {
        shows = Lists.newArrayList();

        show = new Show();
        show.setId(RIGHT_ID);
        show.setName("Test");
        shows.add(show);

        show.setId(RIGHT_ID+1);
        show.setName("Test 2");
        shows.add(show);

        seats = Lists.newArrayList();

        seat = new Seat();
        seat.setId(RIGHT_ID);
        seat.setShowId(RIGHT_ID);
        seat.setRow(ROW);
        seat.setSeatNumbers(Lists.newArrayList("1,2,3"));
        seat.setSeatPrice(150.30F);
        seat.setShowDate(new Date(System.currentTimeMillis()));
        seats.add(seat);

        seat.setId(RIGHT_ID+1);
        seat.setShowId(RIGHT_ID+1);
        seat.setRow(ROW);
        seat.setSeatNumbers(Lists.newArrayList("1,2,3"));
        seat.setSeatPrice(100.70F);
        seat.setShowDate(new Date(System.currentTimeMillis()));
        seats.add(seat);

        bookingDTO = setBookingDTO();
    }

    @Test
    void getShows_responseList() {
        when(showRepository.findAll()).thenReturn(shows);

        List<Show> showList = this.showService.getShows(new FilterDTO());
        assertEquals(showList.size(), shows.size());
    }

    @Test
    void getShows_responseList_withAllFilters() {
        FilterDTO filter = new FilterDTO();
        filter.setStartDate(new Date());
        filter.setEndDate(new Date());
        filter.setBottomPrice(1F);
        filter.setTopPrice(1F);

        when(seatRepository.getDistinctShowIdByShowDateBetweenAndSeatPriceBetween(any(Date.class), any(Date.class),
                any(Float.class), any(Float.class))).thenReturn(ID_LIST);
        when(showRepository.getAllByIdList(any(List.class))).thenReturn(shows);

        List<Show> showList = this.showService.getShows(filter);
        assertEquals(showList.size(), shows.size());
    }

    @Test
    void getShows_responseList_withDateFilters() {
        FilterDTO filter = new FilterDTO();
        filter.setStartDate(new Date());
        filter.setEndDate(new Date());

        when(seatRepository.getDistinctShowIdByShowDateBetween(any(Date.class), any(Date.class))).thenReturn(ID_LIST);
        when(showRepository.getAllByIdList(any(List.class))).thenReturn(shows);

        List<Show> showList = this.showService.getShows(filter);
        assertEquals(showList.size(), shows.size());
    }

    @Test
    void getShows_responseList_withPriceFilters() {
        FilterDTO filter = new FilterDTO();
        filter.setBottomPrice(1F);
        filter.setTopPrice(1F);

        when(seatRepository.getDistinctShowIdBySeatPriceBetween(any(Float.class), any(Float.class))).thenReturn(ID_LIST);
        when(showRepository.getAllByIdList(any(List.class))).thenReturn(shows);

        List<Show> showList = this.showService.getShows(filter);
        assertEquals(showList.size(), shows.size());
    }

    @Test
    void getSeats_responseList() {
        when(seatRepository.findByShowId(anyLong())).thenReturn(seats);

        List<Seat> seatList = this.showService.getSeats(RIGHT_ID, new FilterDTO());
        assertEquals(seatList.size(), seats.size());
    }

    @Test
    void getSeats_responseList_withAllFilters() {
        FilterDTO filter = new FilterDTO();
        filter.setStartDate(new Date());
        filter.setEndDate(new Date());
        filter.setBottomPrice(1F);
        filter.setTopPrice(1F);

        when(seatRepository.getAllByShowDateBetweenAndSeatPriceBetween(any(Long.class), any(Date.class),
                any(Date.class), any(Float.class), any(Float.class))).thenReturn(seats);

        List<Seat> seatList = this.showService.getSeats(RIGHT_ID, filter);
        assertEquals(seatList.size(), seats.size());
    }

    @Test
    void getSeats_responseList_withDateFilters() {
        FilterDTO filter = new FilterDTO();
        filter.setStartDate(new Date());
        filter.setEndDate(new Date());

        when(seatRepository.getAllByShowDateBetween(any(Long.class), any(Date.class), any(Date.class)))
                .thenReturn(seats);

        List<Seat> seatList = this.showService.getSeats(RIGHT_ID, filter);
        assertEquals(seatList.size(), seats.size());
    }

    @Test
    void getSeats_responseList_withPriceFilters() {
        FilterDTO filter = new FilterDTO();
        filter.setBottomPrice(1F);
        filter.setTopPrice(1F);

        when(seatRepository.getAllBySeatPriceBetween(any(Long.class), any(Float.class), any(Float.class)))
                .thenReturn(seats);

        List<Seat> seatList = this.showService.getSeats(RIGHT_ID, filter);
        assertEquals(seatList.size(), seats.size());
    }

    @Test
    void doBooking_OK() {
        seat.setId(RIGHT_ID);
        seat.setShowId(RIGHT_ID);
        seat.setRow(ROW);

        when(seatRepository.findSeatByShowIdAndRow(anyLong(), anyString()))
                .thenReturn(seat);
        when(seatRepository.save(any(Seat.class))).thenReturn(seat);
        when(bookingRepository.save(any(Booking.class))).thenReturn(translateDTO(bookingDTO));

        showService.doBooking(bookingDTO);

        inOrder(seatRepository).verify(seatRepository, calls(1))
                .findSeatByShowIdAndRow(anyLong(), anyString());
        inOrder(seatRepository).verify(seatRepository, calls(1))
            .save(any(Seat.class));
        inOrder(bookingRepository).verify(bookingRepository, calls(1))
                .save(any(Booking.class));
    }

    @Test
    void doBooking_WrongShowID_Exception() {

        when(seatRepository.findSeatByShowIdAndRow(anyLong(), anyString()))
                .thenReturn(null);

        try {
            showService.doBooking(bookingDTO);
        } catch (Exception e) {
            assertEquals(e.getMessage(), ShowService.INVALID_SHOW_ID_ERROR_MESSAGE + bookingDTO.getSeats().get(0).getShowId());
        }

        inOrder(seatRepository).verify(seatRepository, calls(1))
                .findSeatByShowIdAndRow(anyLong(), anyString());
        inOrder(seatRepository).verify(seatRepository, never()).save(any(Seat.class));
        inOrder(bookingRepository).verify(bookingRepository, never()).save(any(Booking.class));
    }

    @Test
    void doBooking_WrongSeatList_Exception() {
        seat.setId(RIGHT_ID);
        seat.setShowId(RIGHT_ID);
        seat.setSeatNumbers(SEATS.subList(0,1));

        when(seatRepository.findSeatByShowIdAndRow(anyLong(), anyString()))
                .thenReturn(seat);
        when(seatRepository.save(any(Seat.class))).thenReturn(seat);

        try {
            showService.doBooking(bookingDTO);
        } catch (Exception e) {
            assertEquals(e.getMessage(), ShowService.INVALID_SEAT_ERROR_MESSAGE + bookingDTO.getSeats().get(0).getShowId());
        }

        inOrder(seatRepository).verify(seatRepository, calls(1))
                .findSeatByShowIdAndRow(anyLong(), anyString());
        inOrder(seatRepository).verify(seatRepository, never()).save(any(Seat.class));
        inOrder(bookingRepository).verify(bookingRepository, never()).save(any(Booking.class));
    }

    private Booking translateDTO(BookingDTO bookingDTO) {
        Booking booking = new Booking();

        booking.setId(RIGHT_ID);
        booking.setDocument(bookingDTO.getDocument());
        booking.setName(bookingDTO.getName());
        booking.setSeatPrice(PRICE);
        booking.setSeatRow(bookingDTO.getSeats().get(0).getRow());
        booking.setSeatNumbers(bookingDTO.getSeats().get(0).getSeatNumbers());

        return booking;
    }

    private BookingDTO setBookingDTO() {
        return BookingDTO.builder()
                .document("123456789")
                .name("Test Name")
                .seats(asList(BookingSeatDTO.builder()
                        .showId(RIGHT_ID)
                        .numbers(SEATS)
                        .row(ROW)
                        .build()))
                .date(new Date(System.currentTimeMillis()))
                .build();
    }
}
