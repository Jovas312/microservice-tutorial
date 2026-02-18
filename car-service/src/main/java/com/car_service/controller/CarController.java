package com.car_service.controller;

import com.car_service.entity.Car;
import com.car_service.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/car")
public class CarController {
    @Autowired
    private CarService carService;

    @GetMapping
    public ResponseEntity<List<Car>> getCars(){
        List<Car> cars = carService.findAll();
        if (cars.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(cars);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Car> getCarById(@PathVariable Integer id){
        Car car = carService.getCarById(id);
        if (car == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(car);
    }

    @PostMapping
    public ResponseEntity<Car> saveCar(@RequestBody Car car){
        Car newCar = carService.save(car);
        return ResponseEntity.ok(newCar);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Car>> getCarsByUserId(@PathVariable Integer userId){
        List<Car> cars = carService.findByUserId(userId);
        if (cars.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(cars);
    }
}
