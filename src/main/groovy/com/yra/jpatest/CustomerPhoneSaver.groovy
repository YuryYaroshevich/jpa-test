package com.yra.jpatest

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean



@SpringBootApplication
class CustomerPhoneSaver {
    static main(args) {
        SpringApplication.run(CustomerPhoneSaver)
    }

    @Bean
    CommandLineRunner demo(CustomerRepository customerRepo, PhoneRepository phoneRepo) {
        { args ->
            //cascadeRemove(customerRepo, phoneRepo)
            cascadeSave(customerRepo, phoneRepo)
        }
    }

    static cascadeSave(CustomerRepository customerRepo, PhoneRepository phoneRepo) {
        Customer cust = customerRepo.save(new Customer("John", "Smith"))
        phoneRepo.save([new Phone("234-98-32", cust)])

        assert phoneRepo.count() == 1
        assert customerRepo.findOne(cust.id).phones.size() == 1


        def newPhone = phoneRepo.save(new Phone("544-34-12"))

        cust = customerRepo.findOne(cust.id)
        cust.phones.add(newPhone)
        customerRepo.save(cust)

        println customerRepo.findOne(cust.id)
        assert customerRepo.findOne(cust.id).phones.size() == 2
    }

    static cascadeRemove(CustomerRepository customerRepo, PhoneRepository phoneRepo) {
        Customer cust = customerRepo.save(new Customer("John", "Smith"))
        println customerRepo.findOne(cust.id)

        Customer cust1WithIdOnly = new Customer(id: cust.id)
        phoneRepo.save(new Phone("565-67-12", cust1WithIdOnly))
        assert phoneRepo.count() == 1

        customerRepo.delete(cust.id)
        assert phoneRepo.count() == 0
    }
}
