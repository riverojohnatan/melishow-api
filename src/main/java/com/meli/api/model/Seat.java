package com.meli.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
@Table(name = "seat")
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @JsonProperty(value = "seat_id")
    private Long id;

    @Column(name = "show_id")
    private Long showId;

    @Column(name = "seat_price")
    @JsonProperty(value = "seat_price")
    private Float seatPrice;

    @Column(name = "seat_available")
    @JsonProperty(value = "seat_available")
    private Integer seatAvailable;

}
