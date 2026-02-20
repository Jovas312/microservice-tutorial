package com.user_service.feignClients;

import com.user_service.models.Car;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "car-service")
public interface CarFeignClient {

    @PostMapping("/car")
    public Car save(@RequestBody Car car);

    @GetMapping("/car/user/{userId}")
    public List<Car> getCars(@PathVariable Integer userId);
}
