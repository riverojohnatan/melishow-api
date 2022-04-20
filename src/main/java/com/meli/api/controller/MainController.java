package com.meli.api.controller;

import com.meli.api.model.BookingDTO;
import com.meli.api.model.Seat;
import com.meli.api.model.Show;
import com.meli.api.model.exception.MeliShowException;
import com.meli.api.service.ShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
class MainController {

    @Autowired
    private ShowService service;

    @GetMapping(value = "/shows")
    public ResponseEntity<List<Show>> getShows() {
        List<Show> shows = this.service.getShows();
        return new ResponseEntity<>(shows, HttpStatus.OK);
    }

    @GetMapping(value = "/show/{id}/seats")
    public ResponseEntity<List<Seat>> getEvents(@PathVariable(value = "id") final Long showId) {
        List<Seat> seats = this.service.getSeats(showId);
        if (seats.size() == 0) return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(seats, HttpStatus.OK);
    }

    @PostMapping(value = "/book")
    public ResponseEntity<String> postBook(@RequestBody BookingDTO request) {
        try {
            this.service.doBooking(request);
            return new ResponseEntity<>(HttpStatus.CREATED.getReasonPhrase(), HttpStatus.CREATED);
        } catch (MeliShowException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
