package com.oracle.team.service;

import com.oracle.team.domain.*;

import java.io.IOException;
import java.util.ArrayList;

public class TeamService {
    static int counter = 1;
    final int MAX_MEMBER = 6;
    final int MAX_ARCH = 1;
    final int MAX_DESI = 2;
    final int MAX_PROG = 3;
    static int archCnt = 0;
    static int desiCnt = 0;
    static int progCnt = 0;
    private ArrayList<Programmer> team = new ArrayList<>();
    public FileHelper fileHelper = new FileHelper();

    public void addMember(Employee employee) throws TeamException{
        addMember(employee, -1);
    }

    private void addMember(Employee employee, int memberId) throws TeamException{
        if(team.size() >= MAX_MEMBER){
            throw new TeamException("成员已满，无法添加");
        }
        if(!(employee instanceof Programmer)){
            throw new TeamException("该员工不是开发人员，无法添加");
        }

        Programmer programmer = (Programmer) employee;
        switch (programmer.getStatus()){
            case Programmer.BUSY: throw new TeamException("该员工已是团队成员");
            case Programmer.VOCATION: throw new TeamException("该员工正在休假，无法添加");
        }

        int arch = archCnt, desi = desiCnt, prog = progCnt;
        if(programmer instanceof Architect){
            arch++;
        }
        else if(programmer instanceof Designer){
            desi++;
        }
        else{ // programmer instanceof Programmer
            prog++;
        }

        if(arch > MAX_ARCH){
            throw new TeamException("团队中只能有一名架构师");
        }
        if(desi > MAX_DESI){
            throw new TeamException("团队中只能有两名设计师");
        }
        if(prog > MAX_PROG){
            throw new TeamException("团队中只能有三名程序员");
        }

        programmer.setMemberId(memberId == -1 ? counter++ : memberId);
        programmer.setStatus(Programmer.BUSY);
        team.add(programmer);
        archCnt = arch; desiCnt = desi; progCnt = prog;
    }

    public void removeMembers(int memberId) throws TeamException{
        for(Programmer item : team){
            if(item.getMemberId() == memberId){
                if(!team.remove(item)){
                    throw new TeamException("成员删除失败！");
                }
                item.setStatus(Programmer.FREE);
                if(item instanceof Architect){
                    archCnt--;
                }
                else if(item instanceof Designer){
                    desiCnt--;
                }
                else{
                    progCnt--;
                }
                return;
            }
        }
        throw new TeamException("找不到该成员，无法删除");
    }

    public Programmer[] getTeam(){
        Programmer[] result = team.toArray(new Programmer[0]);
        return result;
    }

    public String saveData(String path) throws IOException {
        return fileHelper.objectWriter(path, new TeamServicePack(counter, team));
    }

    public void readData(String path, String fileName, NameListService listSvc) throws IOException, ClassNotFoundException, TeamException {
        TeamServicePack fileData = (TeamServicePack) fileHelper.objectReader(path, fileName);
        counter = fileData.getCounter();
        for(Programmer programmer : fileData.getTeam()){
            Programmer programmerPresent = (Programmer) listSvc.getEmployee(programmer.getId());
            addMember(programmerPresent, programmer.getMemberId());
        }
    }
}
