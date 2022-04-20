package com.meli.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Lists;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "booking")
@Data
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(value = "id")
    @Column(name = "id")
    private Long id;

    @JsonProperty(value = "name")
    @Column(name = "name")
    private String name;

    @JsonProperty(value = "document")
    @Column(name = "document")
    private String document;

    @JsonProperty(value = "seat_price")
    @Column(name = "seat_price")
    private Float seatPrice;

    @JsonProperty(value = "seat_numbers")
    @Column(name = "seat_numbers")
    private String seatNumbers;

    @JsonProperty(value = "seat_row")
    @Column(name = "seat_row")
    private String seatRow;

    @JsonProperty(value = "show_id")
    @Column(name = "show_id")
    private Long showId;

    public List<String> getSeatNumbers() {
        if (this.seatNumbers == null) return new ArrayList<>();
        return Lists.newArrayList(this.seatNumbers.split(","));
    }

}
