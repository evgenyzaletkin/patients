package com.home.patients.app.entities;

import java.util.Date;
import java.util.Set;

import lombok.Data;

@Data
public class LocalizedPatientDto {
    public String name;
    public Date birthdate;
    public String address;
    public String sex;
    public Set<String> groups;
}
