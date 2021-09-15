package com.example.CustomerService;

import com.example.CustomerService.controller.CustomerController;
import com.example.CustomerService.model.Customer;
import com.example.CustomerService.repository.CustomerRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.validation.ConstraintViolationException;
import java.util.Date;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class CustomerServiceApplicationTests {

    @InjectMocks
    private CustomerController customerController;

    @Mock
    private CustomerRepository customerRepository;

    @Test
    public void testAddCustomer_Success() throws Exception {
        Customer customer = getCustomer();
        when(customerRepository.save(any())).thenReturn(customer);
        ResponseEntity<String> customerResponseEntity = customerController.addCustomer(customer);
        Assert.assertEquals(HttpStatus.CREATED, customerResponseEntity.getStatusCode());
    }

    @Test
    public void testGetCustomer_Success() throws Exception {
        Customer customer = getCustomer();
        when(customerRepository.findById(anyString())).thenReturn(Optional.of(customer));
        ResponseEntity<Customer> customerResponseEntity = customerController.getCustomer("123");
        Assert.assertEquals(HttpStatus.OK, customerResponseEntity.getStatusCode());
    }

    @Test(expected = ConstraintViolationException.class)
    public void testGetCustomer_Error() throws Exception {
        when(customerRepository.findById(anyString())).thenThrow(ConstraintViolationException.class);
        customerController.getCustomer("123");
    }

    @Test
    public void testUpdateCustomer_Success() throws Exception {
        ResponseEntity<Void> customerResponseEntity = customerController.updateCustomer("123", getCustomer());
        Assert.assertEquals(HttpStatus.ACCEPTED, customerResponseEntity.getStatusCode());
    }

    private Customer getCustomer() {
        Customer customer = new Customer();
        customer.setFirstName("Subhransu");
        customer.setLastName("Das");
        customer.setDob(new Date());
        return customer;
    }

}
