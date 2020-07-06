package com.howtodoinjava.rest.controller;

import java.awt.PageAttributes.MediaType;
import java.net.URI;

import javax.activation.MimeTypeParameterList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.ClientCodecConfigurer.MultipartCodecs;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.howtodoinjava.rest.dao.EmployeeDAO;
import com.howtodoinjava.rest.model.Employee;
import com.howtodoinjava.rest.model.Employees;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(path = "/employees")
public class EmployeeController 
{
    @Autowired
    private EmployeeDAO employeeDao;
    
    @GetMapping(path="/", produces = "application/json")
    public Employees getEmployees() 
    {
        return employeeDao.getAllEmployees();
    }
    
    @CrossOrigin(origins = "*")
    @PostMapping(path= "/", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Object> addEmployee(
                        @RequestHeader(name = "X-COM-PERSIST", required = true) String headerPersist,
                        @RequestHeader(name = "X-COM-LOCATION", required = false, defaultValue = "ASIA") String headerLocation,
                        @RequestBody Employee employee) 
                 throws Exception 
    {       
        //Generate resource id
        Integer id = employeeDao.getAllEmployees().getEmployeeList().size() + 1;
        employee.setId(id);
        
        //add resource
        employeeDao.addEmployee(employee);
        
        //Create resource location
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                                    .path("/{id}")
                                    .buildAndExpand(employee.getId())
                                    .toUri();
        
        //Send location in response
        return ResponseEntity.created(location).build();
    }
    
    @CrossOrigin(origins = "*")
    @PostMapping(path= "/users/authenticate", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Object> authenticate() 
                 throws Exception 
    {       
    	String output = "[{ id: 1, username: \"test\", password: \"test\", firstName: \"Test\", lastName: \"User\" }]";

    	return new ResponseEntity(output, HttpStatus.OK);
    }
    
    @CrossOrigin(origins = "*")
    @GetMapping(path= "/user", produces = "application/json")
    public ResponseEntity<Object> users(){       
    	String output = "[{ id: 1, username: \"test\", password: \"test\", firstName: \"Test\", lastName: \"User\" }]";

    	return new ResponseEntity(output, HttpStatus.OK);

    }
    
    @GetMapping(path = "/prices", produces = "application/json")
    public ResponseEntity getAllPrices(){
    	String output = "[{\"type\":\"Overnight\",\"price\":25.99},{\"type\":\"2-Day\",\"price\":9.99},{\"type\":\"Postal\",\"price\":2.99}]";

    	return new ResponseEntity(output, HttpStatus.OK);
    }
    
}
