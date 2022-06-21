package com.oracle.team.service;

import com.oracle.team.domain.Architect;
import com.oracle.team.domain.Designer;
import com.oracle.team.domain.Employee;
import com.oracle.team.domain.Programmer;
import org.junit.Test;

public class NameListServiceTest {

    @Test
    public void testGetAllEmployees() {
        NameListService listSvc = new NameListService();
        Employee[] employees = listSvc.getAllEmployees();
        listAllEmployees(employees);
    }

    @Test
    public void testGetEmployee() {
        NameListService listSvc = new NameListService();
        Employee[] toTest = new Employee[1];
        toTest[0] = listSvc.getEmployee(2);
        System.out.println(toTest[0]);
        listAllEmployees(toTest);
        toTest[0] = listSvc.getEmployee(13);
        System.out.println(toTest[0]);
        toTest[0] = listSvc.getEmployee(0);
        System.out.println(toTest[0]);
    }

    private static void listAllEmployees(Employee[] employees){
        System.out.println("------------------------------------------开发团队调度软件------------------------------------------");
        System.out.println();
        System.out.println("ID\t\t姓名\t\t年龄\t\t工资\t\t\t职位\t\t\t状态\t\t\t奖金\t\t\t股票\t\t\t领用设备");
        for (Employee employee : employees) {
            System.out.print(employee.getId() + "\t\t");
            System.out.print(employee.getName() + " \t");
            System.out.print(employee.getAge() + "\t\t");
            System.out.print(employee.getSalary() + "\t\t");
            System.out.print(getPositionName(employee) + "\t\t");
            if (employee instanceof Programmer) {
                switch (((Programmer) employee).getStatus()) {
                    case Programmer.FREE:
                        System.out.print("FREE");
                        break;
                    case Programmer.BUSY:
                        System.out.print("BUSY");
                        break;
                    case Programmer.VOCATION:
                        System.out.print("VOCATION");
                        break;
                }
                System.out.print("\t\t");
                if (employee instanceof Designer) {
                    System.out.print(((Designer) employee).getBonus() + "\t\t");
                    if (employee instanceof Architect) {
                        System.out.print(((Architect) employee).getStock() + "\t\t");
                    } else {
                        System.out.print("\t\t\t");
                    }
                } else {
                    System.out.print("\t\t\t\t\t\t");
                }
                System.out.print(((Programmer) employee).getEquipment().getDescription());
            }
            System.out.println();
        }
        System.out.println("-------------------------------------------------------------------------------------------------");
    }

    private static String getPositionName(Employee employee){
        if(employee instanceof Architect){
            return "架构师";
        }
        if(employee instanceof Designer){
            return "设计师";
        }
        if(employee instanceof Programmer){
            return "程序员";
        }
        return "";
    }
}