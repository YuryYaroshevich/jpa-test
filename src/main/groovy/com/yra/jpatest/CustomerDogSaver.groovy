package com.yra.jpatest

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class CustomerDogSaver {
    static main(args) {
        SpringApplication.run(CustomerDogSaver)
    }

    @Bean
    CommandLineRunner demo(CustomerRepository customerRepo, DogRepository dogRepo) {
        { args ->
            cascadeRemove(customerRepo, dogRepo)
        }
    }

    void cascadeRemove(CustomerRepository customerRepo, DogRepository dogRepo) {
        Customer cust = customerRepo.save(new Customer("John", "Smith"))
        dogRepo.save(new Dog("Sharik", cust.id))
        println customerRepo.findOne(cust.id)
        assert dogRepo.count() == 1

        customerRepo.deleteAll()
        assert dogRepo.count() == 0
    }
}
