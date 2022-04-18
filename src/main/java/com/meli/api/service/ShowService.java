package com.meli.api.service;

import com.meli.api.model.Seat;
import com.meli.api.model.Show;
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

    public List<Show> getShows(){
        return this.showRepository.findAll();
    }

    public List<Seat> getSeats(Long showId) {
        return this.seatRepository.findByShowId(showId);
    }
}
