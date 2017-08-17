package com.donggua.lambda;

import com.donggua.employee.Employee;

/**
 * @author IT_donggua
 * @version V1.0
 * @create 2017-08-17 下午 02:58
 */
public class FilterEmployeeForAge implements MyPredicate<Employee> {

    @Override
    public boolean apply(Employee employee) {
        return employee.getAge() <= 30;
    }
}
