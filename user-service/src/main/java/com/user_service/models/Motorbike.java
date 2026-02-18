package com.user_service.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Motorbike {

    private String marca;
    private String modelo;
    private int userId;
}
