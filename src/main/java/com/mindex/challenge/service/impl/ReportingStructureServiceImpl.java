package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.EmployeeService;
import com.mindex.challenge.service.ReportingStructureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;

@Service
public class ReportingStructureServiceImpl implements ReportingStructureService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReportingStructureServiceImpl.class);

    private final EmployeeService employeeService;

    public ReportingStructureServiceImpl(@NotNull EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Override
    public ReportingStructure create(Employee employee) {
        LOGGER.debug("Creating reporting structure for employee [{}]", employee);

        return new ReportingStructure(employee, getEmployeeReportsCount(employee));
    }

    private int getEmployeeReportsCount(Employee employee){
        List<Employee> reports = employee.getDirectReports();
        int sum = reports.size();

        for (Employee emp : reports) { //Iterate reports list
            if (emp.getEmployeeId() != null) { // check null
                //Retrieve employee from the DB using Employee ID
                Employee employeeSearched = employeeService.read(emp.getEmployeeId());
                //check employee DirectReports are not null
                if(employeeSearched.getDirectReports() != null){
                    //assign report counts
                    sum = sum + employeeSearched.getDirectReports().size();
                }
            }
        }

        return sum;
    }
}