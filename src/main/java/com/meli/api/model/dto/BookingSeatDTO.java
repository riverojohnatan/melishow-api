package com.meli.api.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class BookingSeatDTO {

    @JsonProperty(value = "numbers")
    private List<String> numbers;

    @JsonProperty(value = "row")
    private String row;

    public String getSeatNumbers() {
        return String.join(",", this.numbers);
    }
}
