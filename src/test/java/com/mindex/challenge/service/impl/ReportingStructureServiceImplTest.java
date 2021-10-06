package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReportingStructureServiceImplTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String employeeUrl;

    private String reportingStructureUrl;

    @Before
    public void setup() {
        employeeUrl = "http://localhost:" + port + "/employee";
        reportingStructureUrl = "http://localhost:" + port + "/employee/{id}/reportingStructure";
    }

    @Test
    public void testReportingStructureTwoReports() {
        Employee member = new Employee();
        member.setFirstName("Bhagya");
        member.setLastName("perera");
        member.setDepartment("Development");
        member.setPosition("Java Developer");

        Employee memberTwo = new Employee();
        memberTwo.setFirstName("John");
        memberTwo.setLastName("Cena");
        memberTwo.setDepartment("WWE");
        memberTwo.setPosition("Wrestler");

        Employee memberThree = new Employee();
        memberThree.setFirstName("Under");
        memberThree.setLastName("Taker");
        memberThree.setDepartment("WWE");
        memberThree.setPosition("Wrestler");

        List<Employee> employees = new ArrayList<>();
        employees.add(memberTwo);
        employees.add(memberThree);

        member.setDirectReports(employees);

        restTemplate.postForEntity(employeeUrl, memberTwo, Employee.class).getBody();
        restTemplate.postForEntity(employeeUrl, memberThree, Employee.class).getBody();
        Employee createdEmployee = restTemplate.postForEntity(employeeUrl, member, Employee.class).getBody();

        ReportingStructure reports = restTemplate.getForEntity(reportingStructureUrl, ReportingStructure.class,
                createdEmployee.getEmployeeId()).getBody();

        assertEquals(2, reports.getNumberOfReports());
    }
}