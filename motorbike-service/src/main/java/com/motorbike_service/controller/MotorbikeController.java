package com.motorbike_service.controller;

import com.motorbike_service.entity.Motorbike;
import com.motorbike_service.service.MotorbikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/motorbike")
public class MotorbikeController {

    @Autowired
    private MotorbikeService motorbikeService;

    @GetMapping
    public ResponseEntity<List<Motorbike>> findAll(){
        List<Motorbike> motorbikes = motorbikeService.getAll();
        if (motorbikes.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(motorbikes);
    }

    @GetMapping("/{motorbikeId}")
    public ResponseEntity<Motorbike> findById(@PathVariable Integer motorbikeId){
        Motorbike motorbike = motorbikeService.getMotorbikeById(motorbikeId);
        if (motorbike == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(motorbike);
    }

    @PostMapping
    public ResponseEntity<Motorbike> save(@RequestBody Motorbike motorbike){
        Motorbike newMotorbike = motorbikeService.save(motorbike);
        return ResponseEntity.ok(newMotorbike);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Motorbike>> findByUserId(@PathVariable Integer userId){
        List<Motorbike> motorbikes = motorbikeService.byUserId(userId);
        if (motorbikes.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(motorbikes);
    }
}
