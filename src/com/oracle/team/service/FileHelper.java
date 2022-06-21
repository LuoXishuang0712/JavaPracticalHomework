package com.oracle.team.service;

import java.io.*;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileHelper {
    private String extensionName = ".tsf"; // TeamServiceFile
    // fileName format: S<numbers%3d>.<extensionName>

    FileHelper(){}

    FileHelper(String extensionName){
        if(extensionName.startsWith(".")){
            this.extensionName = extensionName;
        }
        else{
            this.extensionName = "." + extensionName;
        }
    }

    public HashSet<String> getAllAccessibleFile(String path){
        if(path == null){
            return null;
        }
        File folder = new File(path);
        if(!folder.exists() || !folder.isDirectory()){
            return null;
        }
        HashSet<String> result = new HashSet<>();
        String[] fileList = folder.list();
        if(fileList == null){
            return null;
        }
        for(String fileName : fileList){
            File file = new File(folder.getPath() + "/" + fileName);
            if(file.isFile() && fileName.endsWith(extensionName)){
                result.add(fileName);
            }
        }
        return result;
    }

    private String getAvailableName(String path){ // 找到文件名格式的最大数字
        HashSet<String> fileList = getAllAccessibleFile(path);
        int Max = 1;
        Pattern patternFullFilename = Pattern.compile("^S(\\d+)\\" + extensionName + "$");
        Pattern patternGetNumber = Pattern.compile("(\\d+)");
        for(String fileName : fileList){
            Matcher fullNameMatcher = patternFullFilename.matcher(fileName);
            if(!fullNameMatcher.find()){
                continue;
            }
            Matcher numberMatcher = patternGetNumber.matcher(fileName);
            if(!numberMatcher.find()){
                continue;
            }
            if(Integer.parseInt(numberMatcher.group()) >= Max){
                Max = Integer.parseInt(numberMatcher.group()) + 1;
            }
        }
        if(Max > 999){
            throw new IndexOutOfBoundsException("文件编号已达最大值！请清理文件后重试");
        }
        return String.format("S%03d" + extensionName, Max);
    }

    public String objectWriter(String path, Object toWrite) throws IOException{
        ObjectOutputStream oos;
        String fileName = getAvailableName(path);
        oos = new ObjectOutputStream(
                new FileOutputStream(
                        (new File(path)).getPath() +
                                "/" +
                                fileName
                )
        );
        oos.writeObject(toWrite);
        oos.close();
        return fileName;
    }

    public Object objectReader(String path, String fileName) throws IOException, ClassNotFoundException{
        ObjectInputStream ois;
        ois = new ObjectInputStream(new FileInputStream(
                (new File(path)).getPath() +
                        "/" +
                        fileName
        ));
        Object retData = ois.readObject();
        ois.close();
        return retData;
    }
}
