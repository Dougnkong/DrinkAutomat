package com.service;

import com.model.DrinkMachine;

public class InputService {

    public InputService() {
        System.out.println("Hello! Please choose the drink you want to buy ans gives ");
    }

    public void getUserOrder(DrinkMachine drinkMachine) {

        if (!drinkMachine.getCompartments().isEmpty()) {
            System.out.println("Here is the list of the available drinks.");
            for (String drinkName : drinkMachine.getCompartments().keySet()) {
                System.out.print(drinkName + " : ");
            }
        } else {
            System.out.println("Sorry! The machine is out of service for the moment.");
        }
    }
}


