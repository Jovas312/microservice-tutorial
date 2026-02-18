package com.motorbike_service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Motorbike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer motorbikeId;
    private String marca;
    private String modelo;
    private int userId;
}
