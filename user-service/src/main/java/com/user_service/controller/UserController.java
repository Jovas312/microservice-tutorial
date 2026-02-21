package com.user_service.controller;

import com.user_service.entity.User;
import com.user_service.models.Car;
import com.user_service.models.Motorbike;
import com.user_service.service.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @CircuitBreaker(name = "carsCB", fallbackMethod = "fallBackGetCar")
    @GetMapping("/car/{userId}")
    public ResponseEntity<List<Car>> getCar(@PathVariable Integer userId){
        User user = userService.getUserById(userId);
        if (user == null){
            return ResponseEntity.notFound().build();
        }
        List<Car> cars = userService.getCars(userId);
        return ResponseEntity.ok(cars);
    }

    @CircuitBreaker(name = "motorbikesCB", fallbackMethod = "fallBackGetMotorbike")
    @GetMapping("/motorbike/{userId}")
    public ResponseEntity<List<Motorbike>> getMotorbike(@PathVariable Integer userId){
        User user = userService.getUserById(userId);
        if (user == null){
            return ResponseEntity.notFound().build();
        }
        List<Motorbike> motorbike = userService.getMotorbikes(userId);
        return ResponseEntity.ok(motorbike);
    }

    @CircuitBreaker(name = "carsCB", fallbackMethod = "fallBackSaveCar")
    @PostMapping("/car/{userId}")
    public ResponseEntity<Car> saveCar(@PathVariable Integer userId, @RequestBody Car car){
        Car newCar = userService.saveCar(userId, car);
        return ResponseEntity.ok(newCar);
    }

    @CircuitBreaker(name = "motorbikesCB", fallbackMethod = "fallBackSaveMotorbike")
    @PostMapping("/motorbike/{userId}")
    public ResponseEntity<Motorbike> saveMotorbike(@PathVariable Integer userId, @RequestBody Motorbike motorbike){
        Motorbike newMotorbike = userService.saveMotorbike(userId, motorbike);
        return ResponseEntity.ok(newMotorbike);
    }

    @CircuitBreaker(name = "allCB", fallbackMethod = "fallBackGetAll")
    @GetMapping("/all/{userId}")
    public ResponseEntity<Map<String, Object>> listAllVehicles(@PathVariable Integer userId) {
        Map<String, Object> result = userService.getUserAndCars(userId);
        return ResponseEntity.ok(result);
    }

    private ResponseEntity<List<Car>> fallBackGetCar(@PathVariable Integer userId, RuntimeException exception){
        return new ResponseEntity("El usuario:" + userId + "tiene los carros en el taller", HttpStatus.OK);
    }

    private ResponseEntity<List<Car>> fallBackSaveCar(@PathVariable Integer userId, @RequestBody Car car, RuntimeException exception){
        return new ResponseEntity("El usuario:" + userId + "no tiene dinero para los carros", HttpStatus.OK);
    }

    private ResponseEntity<List<Motorbike>> fallBackGetMotorbike(@PathVariable Integer userId, RuntimeException exception){
        return new ResponseEntity("El usuario:" + userId + "tiene las motos en el taller", HttpStatus.OK);
    }

    private ResponseEntity<List<Motorbike>> fallBackSaveMotorbike(@PathVariable Integer userId, @RequestBody Motorbike motorbike, RuntimeException exception){
        return new ResponseEntity("El usuario:" + userId + "no tiene dinero para las motos", HttpStatus.OK);
    }

    private ResponseEntity<List<Car>> fallBackGetAll(@PathVariable Integer userId, RuntimeException exception){
        return new ResponseEntity("El usuario:" + userId + "tiene los veiculos en el taller", HttpStatus.OK);
    }

}
