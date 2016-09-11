package com.yra.jpatest

import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository


interface CustomerRepository extends CrudRepository<Customer, Long>, JpaSpecificationExecutor {
    @EntityGraph(value = "Customer.phone", type = EntityGraph.EntityGraphType.LOAD)
    @Query(value = "from Customer")
    List<Customer> getAllWithPhones()

    @EntityGraph(attributePaths = ["address", "phones"], type = EntityGraph.EntityGraphType.LOAD)
    @Query(value = "from Customer")
    List<Customer> getAllWithPhonesAndAddress()
}