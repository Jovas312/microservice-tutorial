package com.user_service.service;

import com.user_service.entity.User;
import com.user_service.feignClients.CarFeignClient;
import com.user_service.feignClients.MotorbikeFeignClient;
import com.user_service.models.Car;
import com.user_service.models.Motorbike;
import com.user_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CarFeignClient carFeignClient;

    @Autowired
    private MotorbikeFeignClient motorbikeFeignClient;

    public List<Car> getCars(int userId){
        List<Car> cars = restTemplate.getForObject("http://localhost:8081/car/user/" + userId, List.class);
        return cars;
    }

    public List<Motorbike> getMotorbikes(int userId){
        List<Motorbike> motorbikes = restTemplate.getForObject("http://localhost:8082/motorbike/user/" + userId, List.class);
        return motorbikes;
    }

    public List<User> getAll(){
        return userRepository.findAll();
    }

    public User getUserById(Integer id){
        return userRepository.findById(id).orElse(null);
    }

    public User save(User user){
        User newUser = userRepository.save(user);
        return newUser;
    }

    public Car saveCar(int userId, Car car){
        car.setUserId(userId);
        Car newCar = carFeignClient.save(car);
        return newCar;
    }

    public Motorbike saveMotorbike(int userId, Motorbike motorbike){
        motorbike.setUserId(userId);
        Motorbike newMotorbike = motorbikeFeignClient.save(motorbike);
        return newMotorbike;
    }

    public Map<String, Object> getUserAndCars(Integer userId){
        Map<String, Object> result = new HashMap<>();
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            result.put("Messsage", "User not exist");
            return  result;
        }
        result.put("User", user);
        List<Car> cars = carFeignClient.getCars(userId);
        if (cars.isEmpty()){
            result.put("Messsage", "No cars found");
        } else {
            result.put("Cars", cars);
        }
        List<Motorbike> motorbikes = motorbikeFeignClient.getMotorbikes(userId);
        if (motorbikes.isEmpty()){
            result.put("Messsage", "No motorbikes found");
        } else {
            result.put("Motorbikes", motorbikes);
        }
        return result;
    }
}
