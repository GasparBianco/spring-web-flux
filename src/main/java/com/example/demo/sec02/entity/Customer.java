package com.example.demo.sec02.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
public class Customer {

    @Id
    private Integer id;
    private String name;
    private String email;
}
