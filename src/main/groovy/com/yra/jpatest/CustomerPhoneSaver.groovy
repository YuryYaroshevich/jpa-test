package com.yra.jpatest

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cglib.core.Local
import org.springframework.context.annotation.Bean

import javax.persistence.EntityManager
import javax.persistence.Query
import java.time.LocalDate


@SpringBootApplication
class CustomerPhoneSaver {
    static main(args) {
        SpringApplication.run(CustomerPhoneSaver)
    }

    @Bean
    CommandLineRunner demo(CustomerRepository customerRepo, PhoneRepository phoneRepo, EntityManager entityManager) {
        { args ->
            //cascadeRemove(customerRepo, phoneRepo)
            //cascadeSave(customerRepo, phoneRepo)
            checkDateWithCustomSqlQuery(customerRepo, phoneRepo, entityManager)
        }
    }

    static checkDateWithCustomSqlQuery(CustomerRepository customerRepo, PhoneRepository phoneRepo, EntityManager entityManager) {
        def savedCustomers = customerRepo.save([new Customer("jack", "jackson", LocalDate.of(2017, 1, 2), LocalDate.of(2034, 1, 1)),
                           new Customer("bob", "bobson", LocalDate.of(2024, 4, 15), LocalDate.of(2045, 4, 12))])

        phoneRepo.save([new Phone("222-33-55", savedCustomers[0]), new Phone("111-44-77", savedCustomers[1])])
        Query q = entityManager.createNativeQuery("select c.* from customer c inner join phone p on c.id = p.customer_id" +
                " where :date between c.date_Of_Birth AND c.date_Of_Mariage order by c.first_name", Customer)
        q.setParameter("date", LocalDate.of(2029, 1, 1))
        List<Customer> customers = q.getResultList()
        customers.each { println it }
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
