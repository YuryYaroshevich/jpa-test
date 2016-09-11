package com.yra.jpatest

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
@EqualsAndHashCode
@ToString
class Dog {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id

    String name

    long customerId

    Dog() {}

    Dog(String name, long customerId) {
        this.name = name
        this.customerId = customerId
    }

    @Override
    public String toString() {
        return "Dog{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", customerId=" + customerId +
                '}';
    }
}
