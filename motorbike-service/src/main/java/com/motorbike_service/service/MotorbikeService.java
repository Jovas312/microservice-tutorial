package com.motorbike_service.service;

import com.motorbike_service.entity.Motorbike;
import com.motorbike_service.repository.MotorbikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MotorbikeService {

    @Autowired
    private MotorbikeRepository motorbikeRepository;

    public List<Motorbike> getAll(){
        return motorbikeRepository.findAll();
    }

    public Motorbike getMotorbikeById(Integer id){
        return motorbikeRepository.findById(id).orElse(null);
    }

    public Motorbike save(Motorbike motorbike){
        Motorbike newMotorbike = motorbikeRepository.save(motorbike);
        return newMotorbike;
    }

    public List<Motorbike> byUserId(Integer id){
        return motorbikeRepository.findByUserId(id);
    }
}
