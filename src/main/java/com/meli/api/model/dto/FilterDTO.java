package com.meli.api.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@NoArgsConstructor
public class FilterDTO {

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date startDate;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date endDate;
    private Float bottomPrice;
    private Float topPrice;

}
