package com.meli.api.controller;

import com.meli.api.model.Seat;
import com.meli.api.model.Show;
import com.meli.api.model.dto.BookingDTO;
import com.meli.api.model.dto.FilterDTO;
import com.meli.api.model.exception.MeliShowException;
import com.meli.api.service.ShowService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
class MainController {

    @Autowired
    private ShowService service;

    @ApiOperation(value = "Get show list", response = Iterable.class, tags = "getShows", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 404, message = "Not Found")
    })
    @GetMapping(value = "/shows")
    public ResponseEntity<List<Show>> getShows(FilterDTO filter) {
        List<Show> shows = this.service.getShows(filter);
        if (shows.size() == 0) return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(shows, HttpStatus.OK);
    }

    @ApiOperation(value = "Get seat list by show id", response = Iterable.class, tags = "getSeats",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 404, message = "Not Found")
    })
    @GetMapping(value = "/show/{id}/seats")
    public ResponseEntity<List<Seat>> getSeats(@PathVariable(value = "id") final Long showId, FilterDTO filter) {
        List<Seat> seats = this.service.getSeats(showId, filter);
        if (seats.size() == 0) return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(seats, HttpStatus.OK);
    }

    @ApiOperation(value = "Post booking request", response = Iterable.class, tags = "postBook", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 400, message = "Bad Request")
    })
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
