package com.yra.jpatest

import org.springframework.data.repository.CrudRepository


interface PhoneRepository extends CrudRepository<Phone, Long> {

}