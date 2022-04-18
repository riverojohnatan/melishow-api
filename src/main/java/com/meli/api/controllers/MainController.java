package com.meli.api.controllers;

import com.meli.api.model.Seat;
import com.meli.api.model.Show;
import com.meli.api.service.ShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
class MainController {

    @Autowired
    private ShowService service;

    @GetMapping(value = "/shows")
    @ResponseStatus(HttpStatus.OK)
    public List<Show> getShows() {
        return this.service.getShows();
    }

    @GetMapping(value = "/show/{id}/seats")
    @ResponseStatus(HttpStatus.OK)
    public List<Seat> getEvents(@PathVariable(value = "id") final Long showId) {
        return this.service.getSeats(showId);
    }
}
