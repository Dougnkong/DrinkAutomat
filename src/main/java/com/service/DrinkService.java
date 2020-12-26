package com.service;

import com.model.Drink;
import com.repository.DrinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DrinkService implements IDrinkService {
    @Autowired
    private DrinkRepository repository;

    @Override
    public List<Drink> findAll() {
        return (List<Drink>) repository.findAll();
    }
}

