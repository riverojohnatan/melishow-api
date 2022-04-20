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
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "document")
    private String document;

    @Column(name = "seat_price")
    private Float seatPrice;

    @Column(name = "seat_numbers")
    private String seatNumbers;

    @Column(name = "seat_row")
    private String seatRow;

    @Column(name = "show_id")
    private Long showId;

    public List<String> getSeatNumberList() {
        if (this.seatNumbers == null) return new ArrayList<>();
        return Lists.newArrayList(this.seatNumbers.split(","));
    }

}
