package com.car_service.service;

import com.car_service.entity.Car;
import com.car_service.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {

    @Autowired
    private CarRepository carRepository;

    public List<Car> findAll() {
        return carRepository.findAll();
    }

    public Car getCarById(Integer id) {
        return carRepository.findById(id).orElse(null);
    }

    public Car save(Car car) {
        Car newCar = carRepository.save(car);
        return newCar;
    }

    public List<Car> findByUserId(Integer userId) {
        return  carRepository.findByUserId(userId);
    }
}
