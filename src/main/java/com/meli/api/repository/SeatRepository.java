package com.meli.api.repository;

import com.meli.api.model.Seat;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface SeatRepository extends CrudRepository<Seat, Long> {

    List<Seat> findByShowId(Long showId);

    Seat findSeatByShowIdAndRow(Long showId, String row);

    @Query("SELECT distinct s.showId FROM Seat s where s.showDate between :startDate and :endDate")
    List<Long> getDistinctShowIdByShowDateBetween(Date startDate, Date endDate);

    @Query("SELECT distinct s.showId FROM Seat s where s.seatPrice between :bottomPrice and :topPrice")
    List<Long> getDistinctShowIdBySeatPriceBetween(Float bottomPrice, Float topPrice);

    @Query("SELECT distinct s.showId FROM Seat s where s.showDate between :startDate and :endDate and s.seatPrice between :bottomPrice and :topPrice")
    List<Long> getDistinctShowIdByShowDateBetweenAndSeatPriceBetween(Date startDate, Date endDate, Float bottomPrice, Float topPrice);
}
