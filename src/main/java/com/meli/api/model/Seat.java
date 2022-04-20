package com.meli.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Lists;
import lombok.Data;
import org.apache.tomcat.util.json.JSONParser;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "seat")
@Data
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

    @Column(name = "row")
    @JsonProperty(value = "row")
    private String row;

    @JsonProperty(value = "seat_numbers")
    @Column(name = "seat_numbers")
    private String seatNumbers;

    @JsonIgnore
    public List<String> getSeatNumberList() {
        return Lists.newArrayList(this.seatNumbers.split(","));
    }

    @JsonIgnore
    public void setSeatNumbers(List<String> list) {
        this.seatNumbers = String.join(",", list);
    }

}
