package com.example.CustomerService.repository;

import com.example.CustomerService.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update customer set " +
            "first_name= :#{#customer.firstName}, " +
            "last_name= :#{#customer.lastName}, " +
            "dob= :#{#customer.dob} where id= :id", nativeQuery = true)
    void updateCustomer(@Param("customer") Customer customer, String id);
}
