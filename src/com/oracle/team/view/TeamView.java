package com.oracle.team.view;

import com.oracle.team.domain.Architect;
import com.oracle.team.domain.Designer;
import com.oracle.team.domain.Employee;
import com.oracle.team.domain.Programmer;
import com.oracle.team.service.NameListService;
import com.oracle.team.service.TeamException;
import com.oracle.team.service.TeamService;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class TeamView {
    private static NameListService listSvc = null;
    private static TeamService teamSvc = null;
    private static final String savePath = "sav";

    public static void main(String[] args) {
        listSvc = new NameListService();
        teamSvc = new TeamService();
        enterMainMenu();
    }

    private static void enterMainMenu(){
        boolean loop = true;
        boolean showTeam = false;
        char selection, confirm;
        checkExistedFile();
        while(loop){
            if (showTeam) {
                listTeam();
                showTeam = false;
            } else {
                listAllEmployees();
            }
            System.out.print("1-团队列表  2-添加团队成员  3-删除团队成员 4-退出   请选择(1-4)：");
            selection = TSUtility.readMenuSelection();
            switch (selection){
                case '1':
                    showTeam = true;
                    break;
                case '2':
                    addMember();
                    break;
                case '3':
                    deleteMember();
                    break;
                case '4':
                    System.out.print("确认是否退出(Y/N)：");
                    confirm = TSUtility.readConfirmSelection();
                    if(confirm == 'Y'){
                        loop = saveIntoFile();
                    }
            }
        }
    }

    private static void listAllEmployees(){
        Employee[] employees = listSvc.getAllEmployees();
        System.out.println("------------------------------------------开发团队调度软件------------------------------------------");
        System.out.println();
        System.out.println("ID\t姓名\t\t年龄\t工资\t\t职位\t\t状态\t\t奖金\t\t股票\t\t领用设备");
        for (Employee employee : employees) {
            System.out.print(employee.getId() + "\t");
            System.out.print(employee.getName() + " \t");
            System.out.print(employee.getAge() + "\t");
            System.out.print(employee.getSalary() + "\t");
            System.out.print(getPositionName(employee) + "\t");
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
                System.out.print("\t");
                if (employee instanceof Designer) {
                    System.out.print(((Designer) employee).getBonus() + "\t");
                    if (employee instanceof Architect) {
                        System.out.print(((Architect) employee).getStock() + "\t");
                    } else {
                        System.out.print("\t\t");
                    }
                } else {
                    System.out.print("\t\t\t\t");
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

    private static void listTeam(){
        Programmer[] team = teamSvc.getTeam();
        System.out.println("--------------------团队成员列表---------------------");
        System.out.println();
        if(team.length == 0){
            System.out.println("开发团队目前没有成员！");
        }
        else{
            System.out.println("TID/ID\t姓名\t\t年龄\t工资\t\t职位\t\t奖金\t\t股票");
        }
        for(Programmer employee : team){
            System.out.print(employee.getMemberId() + "/" + employee.getId() + " \t");
            System.out.print(employee.getName() + " \t");
            System.out.print(employee.getAge() + "\t");
            System.out.print(employee.getSalary() + "\t");
            System.out.print(getPositionName(employee) + "\t");
            if(employee instanceof Designer){
                System.out.print(((Designer) employee).getBonus() + "\t");
                if(employee instanceof Architect){
                    System.out.print(((Architect) employee).getStock() + "\t");
                }
                else{
                    System.out.print("\t\t");
                }
            }
            else{
                System.out.print("\t\t\t\t");
            }
            System.out.println();
        }
        System.out.println("-----------------------------------------------------");
    }

    private static void addMember(){
        System.out.println();
        System.out.println("---------------------添加成员---------------------");
        System.out.print("请输入要添加员工的ID：");
        int id = TSUtility.readInt();
        try{
            Employee employee = listSvc.getEmployee(id);
            if(employee == null){
                throw new TeamException("找不到该员工！");
            }
            teamSvc.addMember(employee);
        }catch (TeamException e){
            System.out.println("添加失败，原因：" + e.getMessage());
            TSUtility.readReturn();
            return;
        }
        System.out.println("添加成功");
        TSUtility.readReturn();
    }

    private static void deleteMember(){
        System.out.println();
        System.out.println("---------------------删除成员---------------------");
        System.out.print("请输入要删除员工的TID：");
        int tid = TSUtility.readInt();
        System.out.print("确认是否删除(Y/N)：");
        char confirm = TSUtility.readConfirmSelection();
        if(confirm == 'N'){
            return;
        }
        try{
            teamSvc.removeMembers(tid);
        }catch (TeamException e){
            System.out.println("删除失败，原因：" + e.getMessage());
            TSUtility.readReturn();
            return;
        }
        System.out.println("删除成功");
        TSUtility.readReturn();
    }

    private static boolean saveIntoFile(){
        System.out.print("是否要将当前团队数据保存到当前目录下(Y/N)：");
        char confirm = TSUtility.readConfirmSelection();
        if(confirm == 'N'){
            return false;
        }
        String fileName;
        try{
            fileName = teamSvc.saveData(savePath);
        }catch (IOException e){
            System.out.println("文件保存失败！请检查存储空间以及当前目录");
            TSUtility.readReturn();
            return true;  // 避免保存失败意外退出
        }catch (IndexOutOfBoundsException e){
            System.out.println(e.getMessage());  // 文件编号到999最大值
            TSUtility.readReturn();
            return true;
        }
        System.out.println("保存成功！文件名为" + fileName);
        TSUtility.readReturn();
        return false;
    }

    private static void checkExistedFile(){
        File dir = new File(savePath);
        if(!dir.exists() && !dir.mkdir()){
            return;
        }
        ArrayList<String> fileList = teamSvc.fileHelper.getAllAccessibleFile(savePath);
        if(fileList == null || fileList.size() == 0){  // 找不到存档文件
            return;
        }
        String[] fileNames = fileList.toArray(new String[0]);
        Arrays.sort(fileNames);
        int pageNo = 1, pageSize = 9;
        boolean loop = true;
        while(loop){
            System.out.println("---------------------存档文件列表----------------------");
            System.out.println();
            System.out.println(geneFileList(fileNames, pageNo, pageSize));
            System.out.printf("                  [第%d页/共%d页]%n", pageNo, (fileNames.length - 1)/pageSize + 1);
            System.out.println("-----------------------------------------------------");
            System.out.print("输入数字选择文件|q取消加载");
            if(fileNames.length > pageSize){
                if(pageNo != 1){
                    System.out.print("|<上一页");
                }
                if((fileNames.length - 1)/pageSize != pageNo - 1){
                    System.out.print("|>下一页");
                }
            }
            System.out.print("| :");
            char selection = TSUtility.readFileListSelection();
            String fileName = null;
            switch(selection){
                case '<':
                    if(pageNo != 1){
                        pageNo--;
                    }
                    break;
                case '>':
                    if((fileNames.length - 1)/pageSize != pageNo - 1){
                        pageNo++;
                    }
                    break;
                case 'q':
                    System.out.println("未加载任何文件！");
                    loop = false;
                    break;
                case '1': case '2': case '3':
                case '4': case '5': case '6':
                case '7': case '8': case '9':
                    fileName = dealFileSelection(fileNames, pageNo, pageSize, selection);
            }
            if(fileName != null){
                try{
                    teamSvc.readData(savePath, fileName, listSvc);
                }catch (IOException | ClassNotFoundException | TeamException e) {
                    System.out.println(fileName + "加载失败！请检查文件完整性与权限");
                    continue;
                }
                System.out.println(fileName + "加载成功！");
                loop = false;
            }
        }
    }

    private static String geneFileList(String[] fileList, int pageNo, int pageSize){
        int start = (pageNo - 1) * pageSize;
        int end = pageNo * pageSize - 1;
        StringBuilder result = new StringBuilder();
        for(int i = start; i < Math.min(end + 1, fileList.length); i++){
            result.append(i - start + 1).append(" | ").append(fileList[i]);
            if((i - start + 1) % 3 == 0){
                result.append('\n');
            }
            else{
                result.append('\t');
            }
        }
        return result.delete(result.length() - 1, result.length()).toString();
    }

    private static String dealFileSelection(String[] fileList, int pageNo, int pageSize, char num){
        int pos = (pageNo - 1) * pageSize + (num - '1');
        if(pos >= fileList.length){
            return null;
        }
        return fileList[pos];
    }
}
