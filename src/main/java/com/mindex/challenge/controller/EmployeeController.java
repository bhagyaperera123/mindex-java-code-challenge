package com.mindex.challenge.controller;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.CompensationService;
import com.mindex.challenge.service.EmployeeService;
import com.mindex.challenge.service.ReportingStructureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@RestController
public class EmployeeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeController.class);

    private final EmployeeService employeeService;

    private final ReportingStructureService reportingStructureService;

    private final CompensationService compensationService;

    public EmployeeController(@NotNull EmployeeService employeeService,
                              @NotNull ReportingStructureService reportingStructureService,
                              @NotNull CompensationService compensationService) {

        this.employeeService = employeeService;
        this.reportingStructureService = reportingStructureService;
        this.compensationService = compensationService;
    }

    @PostMapping("/employee")
    public Employee create(@RequestBody Employee employee) {
        LOGGER.debug("Received employee create request for [{}]", employee);

        return employeeService.create(employee);
    }

    @GetMapping("/employee/{id}")
    public Employee read(@PathVariable String id) {
        LOGGER.debug("Received employee create request for id [{}]", id);

        return employeeService.read(id);
    }

    @PutMapping("/employee/{id}")
    public Employee update(@PathVariable String id, @RequestBody Employee employee) {
        LOGGER.debug("Received employee create request for id [{}] and employee [{}]", id, employee);
        employee.setEmployeeId(id);

        return employeeService.update(employee);
    }

    @GetMapping("/employee/{id}/reportingStructure")
    public ReportingStructure getEmployeeReports(@PathVariable String id) {
        LOGGER.debug("Received employee reporting structure lookup request for employee with id [{}]", id);

        return reportingStructureService.create(employeeService.read(id));
    }

    @PostMapping("/compensation")
    public Compensation createCompensation(@RequestBody Compensation compensation) {
        LOGGER.debug("Received compensation create request for employee compensation: [{}]", compensation);

        return compensationService.create(compensation);
    }

    @GetMapping("/compensation/{id}")
    public Compensation readCompensation(@PathVariable String id) {
        LOGGER.debug("Received employee compensation read request for id [{}]", id);

        return compensationService.read(id);
    }
}
