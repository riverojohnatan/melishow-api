package com.meli.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class BookingSeatDTO {

    @JsonProperty(value = "numbers")
    private List<String> numbers;

    @JsonProperty(value = "row")
    private String row;

    @JsonProperty(value = "show_id")
    private Long showId;

    public String getSeatNumbers() {
        return String.join(",", this.numbers);
    }
}
