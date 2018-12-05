package com.home.patients.app.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "diseases")
@Data
public class Disease {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Long id;
    String name;
}
