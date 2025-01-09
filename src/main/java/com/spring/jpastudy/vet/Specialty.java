package com.spring.jpastudy.vet;

import com.spring.jpastudy.model.NamedEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "specialties")
public class Specialty extends NamedEntity {

}
