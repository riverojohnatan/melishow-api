package com.meli.api.service;

import com.meli.api.model.Booking;
import com.meli.api.model.Seat;
import com.meli.api.model.Show;
import com.meli.api.repository.BookingRepository;
import com.meli.api.repository.SeatRepository;
import com.meli.api.repository.ShowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShowService {

    @Autowired
    private ShowRepository showRepository;
    @Autowired
    private SeatRepository seatRepository;
    @Autowired
    private BookingRepository bookingRepository;

    public List<Show> getShows(){
        return this.showRepository.findAll();
    }

    public List<Seat> getSeats(Long showId) {
        return this.seatRepository.findByShowId(showId);
    }

    public void doBooking(Booking booking) {
        Seat seat = this.seatRepository.findSeatByShowIdAndRow(booking.getShowId(), booking.getSeatRow());
        List<String> seatNumbers = seat.getSeatNumbers();
        List<String> bookingSeatNumbers = booking.getSeatNumbers();

        for(String seatNumber : bookingSeatNumbers) {
            seatNumbers.remove(seatNumber);
        }

        seat.setSeatNumbers(seatNumbers);
        seat.setSeatAvailable(seat.getSeatAvailable() - bookingSeatNumbers.size());
        this.seatRepository.save(seat);
        this.bookingRepository.save(booking);
    }
}
