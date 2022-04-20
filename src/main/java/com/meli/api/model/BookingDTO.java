package com.meli.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Lists;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
public class BookingDTO {

    @JsonProperty(value = "id")
    private Long id;

    @JsonProperty(value = "name", required = true)
    private String name;

    @JsonProperty(value = "document", required = true)
    private String document;

    @JsonProperty(value = "seats", required = true)
    private List<BookingSeatDTO> seats;

}
