package com.donggua.lambda;

import com.donggua.employee.Employee;

/**
 * @author IT_donggua
 * @version V1.0
 * @create 2017-08-17 下午 02:59
 */
public class FilterEmployeeForSalary implements MyPredicate<Employee> {

    @Override
    public boolean apply(Employee employee) {
        return employee.getSalary() >= 4000;
    }
}
