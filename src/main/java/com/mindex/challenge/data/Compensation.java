package com.mindex.challenge.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class Compensation {

    private static final Logger LOGGER = LoggerFactory.getLogger(Compensation.class);

    private String employeeId;

    private BigDecimal salary;

    private Date effectiveDate;

    @Override
    public int hashCode() {
        return Objects.hash(employeeId, salary, effectiveDate);
    }

    public String getEmployeeId() {
        return this.employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getSalary() {
        return this.salary.toPlainString();
    }

    public void setSalary(String salary) {
        this.salary = new BigDecimal(salary);
    }

    public String getEffectiveDate() {
        return new SimpleDateFormat("mm-dd-yyyy").format(this.effectiveDate);
    }

    public void setEffectiveDate(String effectiveDate) {
        try {
            this.effectiveDate = new SimpleDateFormat("mm-dd-yyyy").parse(effectiveDate);
        } catch (ParseException e) {
            LOGGER.debug("Unable to parse date: [{}]", effectiveDate);
        }
    }
}