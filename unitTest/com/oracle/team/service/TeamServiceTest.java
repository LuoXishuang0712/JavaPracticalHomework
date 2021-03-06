package com.oracle.team.service;

import com.oracle.team.domain.Architect;
import com.oracle.team.domain.Designer;
import com.oracle.team.domain.Employee;
import com.oracle.team.domain.Programmer;
import org.junit.Before;
import org.junit.Test;

public class TeamServiceTest {
    NameListService listSvc = null;

    @Before
    public void updateBeforeClass(){
        listSvc = new NameListService();
    }

    @Test
    public void testAddMember() {
        TeamService teamSvc = new TeamService();
        listTeam(teamSvc.getTeam());
        try{
            teamSvc.addMember(listSvc.getEmployee(2));
        } catch (TeamException e) {
            System.out.println(e.getMessage());
        }
        listTeam(teamSvc.getTeam());
        try{
            teamSvc.addMember(listSvc.getEmployee(8));
        } catch (TeamException e) {
            System.out.println(e.getMessage());
        }
        listTeam(teamSvc.getTeam());
        try{
            teamSvc.addMember(listSvc.getEmployee(1));
        } catch (TeamException e) {
            System.out.println(e.getMessage());
        }
        listTeam(teamSvc.getTeam());
        try{
            teamSvc.addMember(null);
        } catch (TeamException e) {
            System.out.println(e.getMessage());
        }
        listTeam(teamSvc.getTeam());
        try{
            for(int i = 3; i <= 7; i++){
                teamSvc.addMember(listSvc.getEmployee(i));
            }
            teamSvc.addMember(listSvc.getEmployee(11));
        } catch (TeamException e) {
            System.out.println(e.getMessage());
        }
        listTeam(teamSvc.getTeam());
    }

    @Test
    public void testRemoveMembers() {
        TeamService teamSvc = new TeamService();
        try{
            for(int i = 2; i <= 7; i++){
                teamSvc.addMember(listSvc.getEmployee(i));
            }
        } catch (TeamException e) {
            System.out.println(e.getMessage());
        }
        listTeam(teamSvc.getTeam());
        try{
            for(int i = 1; i <= 6; i++){
                teamSvc.removeMembers(i);
            }
        } catch (TeamException e) {
            System.out.println(e.getMessage());
        }
        listTeam(teamSvc.getTeam());
        try{
            teamSvc.removeMembers(7);
        } catch (TeamException e) {
            System.out.println(e.getMessage());
        }
        listTeam(teamSvc.getTeam());
    }

    private static void listTeam(Programmer[] team){
        System.out.println("--------------------??????????????????---------------------");
        System.out.println();
        if(team.length == 0){
            System.out.println("?????????????????????????????????");
        }
        else{
            System.out.println("TID/ID\t\t??????\t\t??????\t\t??????\t\t\t??????\t\t\t??????\t\t\t??????");
        }
        for(Programmer employee : team){
            System.out.print(employee.getMemberId() + "/" + employee.getId() + " \t\t");
            System.out.print(employee.getName() + " \t");
            System.out.print(employee.getAge() + "\t\t");
            System.out.print(employee.getSalary() + "\t\t");
            System.out.print(getPositionName(employee) + "\t\t");
            if(employee instanceof Designer){
                System.out.print(((Designer) employee).getBonus() + "\t\t");
                if(employee instanceof Architect){
                    System.out.print(((Architect) employee).getStock() + "\t\t");
                }
                else{
                    System.out.print("\t\t\t");
                }
            }
            else{
                System.out.print("\t\t\t\t\t\t");
            }
            System.out.println();
        }
        System.out.println("-----------------------------------------------------");
    }

    private static String getPositionName(Employee employee){
        if(employee instanceof Architect){
            return "?????????";
        }
        if(employee instanceof Designer){
            return "?????????";
        }
        if(employee instanceof Programmer){
            return "?????????";
        }
        return "";
    }
}