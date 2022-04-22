package com.meli.api.repository;

import com.meli.api.model.Show;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShowRepository extends CrudRepository<Show, Long> {

    @Query("select s from Show s WHERE s.id in :idList")
    List<Show> getAllByIdList(List<Long> idList);

}
