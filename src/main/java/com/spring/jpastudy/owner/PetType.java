package com.spring.jpastudy.owner;

import com.spring.jpastudy.model.NamedEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "types")
public class PetType extends NamedEntity {

}
