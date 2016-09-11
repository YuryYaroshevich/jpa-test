package com.yra.jpatest

import groovy.transform.EqualsAndHashCode
import groovy.transform.TupleConstructor
import org.hibernate.annotations.Cascade

import javax.persistence.*

@Entity
@NamedEntityGraph(name = "Customer.phone",
        attributeNodes = @NamedAttributeNode("phones"))
@EqualsAndHashCode
@TupleConstructor
class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id
    String firstName
    String lastName

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "customer")
    //@Cascade(org.hibernate.annotations.CascadeType.ALL)
    List<Phone> phones

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "customerId")
    List<Dog> dogs

    Customer() {}

    Customer(String firstName, String lastName, List<Phone> phones) {
        this.firstName = firstName
        this.lastName = lastName
        this.phones = phones
    }

    Customer(String firstName, String lastName) {
        this.firstName = firstName
        this.lastName = lastName
        this.dogs = dogs
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phones=" + phones +
                ", dogs=" + dogs +
                '}';
    }
}
