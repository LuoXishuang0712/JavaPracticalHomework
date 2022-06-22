package com.oracle.team.service;

import com.oracle.team.domain.*;

import java.util.ArrayList;

public class NameListService {
    private ArrayList<Employee> employees;

    public NameListService(){
        this.employees = new ArrayList<>();

        for(int i = 0; i < Data.EMPLOYEES.length; i++){
            String[] item = Data.EMPLOYEES[i];
            switch (Integer.parseInt(item[0])){
                case Data.EMPLOYEE:
                    employees.add(new Employee(
                            Integer.parseInt(item[1]),
                            item[2],
                            Integer.parseInt(item[3]),
                            Double.parseDouble(item[4])
                            )
                    );
                    break;
                case Data.PROGRAMMER:
                    employees.add(new Programmer(
                            Integer.parseInt(item[1]),
                            item[2],
                            Integer.parseInt(item[3]),
                            Double.parseDouble(item[4]),
                            BuildEquipment(Data.EQIPMENTS[i])
                            )
                    );
                    break;
                case Data.DESIGNER:
                    employees.add(new Designer(
                            Integer.parseInt(item[1]),
                            item[2],
                            Integer.parseInt(item[3]),
                            Double.parseDouble(item[4]),
                            BuildEquipment(Data.EQIPMENTS[i]),
                            Double.parseDouble(item[5])
                            )
                    );
                    break;
                case Data.ARCHITECT:
                    employees.add(new Architect(
                            Integer.parseInt(item[1]),
                            item[2],
                            Integer.parseInt(item[3]),
                            Double.parseDouble(item[4]),
                            BuildEquipment(Data.EQIPMENTS[i]),
                            Double.parseDouble(item[5]),
                            Integer.parseInt(item[6])
                            )
                    );
                    break;
            }
        }
    }

    private Equipment BuildEquipment(String[] equipment){
        Equipment ret = null;
        switch (Integer.parseInt(equipment[0])){
            case Data.PC:
                ret = new PC(equipment[1],equipment[2]);
                break;
            case Data.NOTEBOOK:
                ret = new NoteBook(equipment[1], Double.parseDouble(equipment[2]));
                break;
            case Data.PRINTER:
                ret = new Printer(equipment[1], equipment[2]);
                break;
        }
        return ret;
    }

    public Employee[] getAllEmployees() {
        Employee[] result = employees.toArray(new Employee[0]);
        return result;
    }

    public Employee getEmployee(int id){
        for(Employee item : employees){
            if(item.getId() == id){
                return item;
            }
        }
        return null;
    }
}
