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

    Seat findSeatByShowIdAndRowAndShowDate(Long showId, String row, Date showDate);

    @Query("SELECT distinct s.showId FROM Seat s where s.showDate between :startDate and :endDate")
    List<Long> getDistinctShowIdByShowDateBetween(Date startDate, Date endDate);

    @Query("SELECT distinct s.showId FROM Seat s where s.seatPrice between :bottomPrice and :topPrice")
    List<Long> getDistinctShowIdBySeatPriceBetween(Float bottomPrice, Float topPrice);

    @Query("SELECT distinct s.showId FROM Seat s where s.showDate between :startDate and :endDate and s.seatPrice between :bottomPrice and :topPrice")
    List<Long> getDistinctShowIdByShowDateBetweenAndSeatPriceBetween(Date startDate, Date endDate, Float bottomPrice, Float topPrice);

    @Query("SELECT s FROM Seat s where s.showId = :showId and s.showDate between :startDate and :endDate")
    List<Seat> getAllByShowDateBetween(Long showId, Date startDate, Date endDate);

    @Query("SELECT s FROM Seat s where s.showId = :showId and s.seatPrice between :bottomPrice and :topPrice")
    List<Seat> getAllBySeatPriceBetween(Long showId, Float bottomPrice, Float topPrice);

    @Query("SELECT s FROM Seat s where s.showId = :showId and s.showDate between :startDate and :endDate and s.seatPrice between :bottomPrice and :topPrice")
    List<Seat> getAllByShowDateBetweenAndSeatPriceBetween(Long showId, Date startDate, Date endDate, Float bottomPrice, Float topPrice);
}
