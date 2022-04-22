package com.meli.api.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Data
@Builder
public class BookingDTO {

    @JsonProperty(value = "id")
    private Long id;

    @JsonProperty(value = "name", required = true)
    private String name;

    @JsonProperty(value = "document", required = true)
    private String document;

    @JsonProperty(value = "date", required = true)
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date date;

    @JsonProperty(value = "seats", required = true)
    private List<BookingSeatDTO> seats;

}
