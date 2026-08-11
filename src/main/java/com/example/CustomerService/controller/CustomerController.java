package com.example.CustomerService.controller;

import com.example.CustomerService.model.Customer;
import com.example.CustomerService.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

@RestController
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @PostMapping(value = "/addCustomer", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addCustomer(@Valid @RequestBody Customer customer) {
        customer.setId(UUID.randomUUID().toString());
        customer.setMobile(formatMobileNumber(customer.getMobile()));
        Customer addedCustomer = customerRepository.save(customer);
        return new ResponseEntity<>(addedCustomer.getId(), HttpStatus.CREATED);
    }

    @PutMapping(value = "/updateCustomer/{id}", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateCustomer(@PathVariable String id,
                                               @Valid @RequestBody Customer customer) {
        customer.setMobile(formatMobileNumber(customer.getMobile()));
        customerRepository.updateCustomer(customer, id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    private String formatMobileNumber(String mobile) {
        if (mobile == null) {
            return null;
        }
        try {
            com.google.i18n.phonenumbers.PhoneNumberUtil phoneNumberUtil = com.google.i18n.phonenumbers.PhoneNumberUtil.getInstance();
            String defaultRegion = mobile.trim().startsWith("+") ? null : "US";
            com.google.i18n.phonenumbers.Phonenumber.PhoneNumber number = phoneNumberUtil.parse(mobile, defaultRegion);
            return phoneNumberUtil.format(number, com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberFormat.E164);
        } catch (Exception e) {
            return mobile;
        }
    }

    @GetMapping(value = "/getCustomer/{id}")
    public ResponseEntity<Customer> getCustomer(@PathVariable String id) {
        Optional<Customer> customer = customerRepository.findById(id);
        return new ResponseEntity<>(customer.get(), HttpStatus.OK);
    }
}
