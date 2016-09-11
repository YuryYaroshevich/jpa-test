package com.yra.jpatest

import org.springframework.data.repository.CrudRepository


interface DogRepository extends CrudRepository<Dog, Long> {

}