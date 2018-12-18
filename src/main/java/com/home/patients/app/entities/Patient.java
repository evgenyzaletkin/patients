package com.home.patients.app.entities;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;

@Data
@Entity
@Table(name = "patients")
public class Patient {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Long id;
    private String name;
    private Date birthDate;
    private Sex sex;
    private String address;
    @ManyToMany
    @JoinTable(name = "patients_to_diseases",
        joinColumns = @JoinColumn(name = "patient_id"),
        inverseJoinColumns = @JoinColumn(name = "disease_id")
    )
    private List<Disease> diseases;
    private Status status;
    private boolean smoker;
    private boolean drinker;
    private boolean drugUser;
    private boolean fsin;
    private boolean tuberculosis;
    private int kkf;
    @Transient
    private Set<Group> groups;
}
