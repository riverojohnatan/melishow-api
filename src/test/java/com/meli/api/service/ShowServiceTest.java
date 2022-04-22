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

    @Test
    void getShows_responseList() {
        Show show = new Show();
        show.setId(RIGHT_ID);
        show.setName("Test");
        when(showRepository.findAll()).thenReturn(asList(show));

        List<Show> showList = this.showService.getShows(new FilterDTO());
        assertEquals(showList.get(0).getName(), show.getName());
    }

    @Test
    void getSeats_responseList() {
        Seat seat = new Seat();
        seat.setId(RIGHT_ID);
        seat.setShowId(RIGHT_ID);
        seat.setRow(ROW);
        seat.setSeatNumbers(Lists.newArrayList("1,2,3"));
        seat.setSeatPrice(150.30F);
        seat.setShowDate(new Date(System.currentTimeMillis()));
        when(seatRepository.findByShowId(anyLong())).thenReturn(asList(seat));

        List<Seat> seatList = this.showService.getSeats(RIGHT_ID);
        assertEquals(seatList.get(0).getRow(), ROW);
    }

    @Test
    void doBooking_OK() {
        Seat seat = new Seat();
        seat.setId(RIGHT_ID);
        seat.setShowId(RIGHT_ID);
        seat.setRow(ROW);
        seat.setSeatNumbers(SEATS);
        seat.setSeatPrice(PRICE);
        seat.setShowDate(new Date(System.currentTimeMillis()));
        BookingDTO bookingDTO = setBookingDTO();

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
        BookingDTO bookingDTO = setBookingDTO();

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
        Seat seat = new Seat();
        seat.setId(RIGHT_ID);
        seat.setShowId(RIGHT_ID);
        seat.setRow(ROW);
        seat.setSeatNumbers(SEATS.subList(0,1));
        seat.setSeatPrice(PRICE);
        BookingDTO bookingDTO = setBookingDTO();

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
