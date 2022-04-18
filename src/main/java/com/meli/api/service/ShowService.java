package com.meli.api.service;

import com.meli.api.model.Show;
import com.meli.api.repository.ShowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShowService {

    @Autowired
    private ShowRepository  repository;

    public List<Show> getShows(){
        return this.repository.findAll();
    }
}
