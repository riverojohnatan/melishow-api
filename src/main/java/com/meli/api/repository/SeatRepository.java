package com.meli.api.repository;

import com.meli.api.model.Seat;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepository extends CrudRepository<Seat, Long> {

    List<Seat> findByShowId(Long showId);

    Seat findSeatByShowIdAndRow(Long showId, String row);
}
