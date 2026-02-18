package com.user_service.controller;

import com.user_service.entity.User;
import com.user_service.models.Car;
import com.user_service.models.Motorbike;
import com.user_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> listAllUsers() {
        List<User> users = userService.getAll();

        if (users.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Integer id) {
        User user = userService.getUserById(id);
        if (user == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<User> saveUser(@RequestBody User user) {
        User newUser =  userService.save(user);
        return ResponseEntity.ok(newUser);
    }

    @GetMapping("/car/{userId}")
    public ResponseEntity<List<Car>> getCar(@PathVariable Integer userId){
        User user = userService.getUserById(userId);
        if (user == null){
            return ResponseEntity.notFound().build();
        }
        List<Car> cars = userService.getCars(userId);
        return ResponseEntity.ok(cars);
    }

    @GetMapping("/motorbike/{userId}")
    public ResponseEntity<List<Motorbike>> getMotorbike(@PathVariable Integer userId){
        User user = userService.getUserById(userId);
        if (user == null){
            return ResponseEntity.notFound().build();
        }
        List<Motorbike> motorbike = userService.getMotorbikes(userId);
        return ResponseEntity.ok(motorbike);
    }

    @PostMapping("/car/{userId}")
    public ResponseEntity<Car> saveCar(@PathVariable Integer userId, @RequestBody Car car){
        Car newCar = userService.saveCar(userId, car);
        return ResponseEntity.ok(newCar);
    }

    @PostMapping("/motorbike/{userId}")
    public ResponseEntity<Motorbike> saveMotorbike(@PathVariable Integer userId, @RequestBody Motorbike motorbike){
        Motorbike newMotorbike = userService.saveMotorbike(userId, motorbike);
        return ResponseEntity.ok(newMotorbike);
    }

    @GetMapping("/all/{userId}")
    public ResponseEntity<Map<String, Object>> listAllVehicles(@PathVariable Integer userId) {
        Map<String, Object> result = userService.getUserAndCars(userId);
        return ResponseEntity.ok(result);
    }

}
