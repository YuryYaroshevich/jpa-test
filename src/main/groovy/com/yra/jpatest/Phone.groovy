package com.yra.jpatest

import groovy.transform.EqualsAndHashCode

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne


@Entity
@EqualsAndHashCode
class Phone {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id

    String number

    @ManyToOne
    @JoinColumn(name = "customer_id")
    Customer customer

    Phone() {}

    Phone(String number) {
        this.number = number
    }

    Phone(String number, Customer customer) {
        this.number = number
        this.customer = customer
    }

    @Override
    String toString() {
        return String.format("[phone: id=%s, number=%s]", id, number)
    }
}
