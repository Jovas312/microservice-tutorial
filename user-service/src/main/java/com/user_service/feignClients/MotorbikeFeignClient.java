package com.user_service.feignClients;

import com.user_service.models.Motorbike;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "motorbike-service")
public interface MotorbikeFeignClient {
    @PostMapping("/motorbike")
    public Motorbike save(@RequestBody Motorbike motorbike);

    @GetMapping("/motorbike/user/{userId}")
    public List<Motorbike> getMotorbikes(@PathVariable Integer userId);
}
