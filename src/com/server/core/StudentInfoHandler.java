package com.server.core;

import com.server.core.pojo.Session;
import com.server.core.pojo.Student;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Use the singleton pattern to create a global unique object
 * handle every student information
 */
public class StudentInfoHandler{

    private static StudentInfoHandler studentInfoHandler;
    private Map<Integer, Student> studentMap;

    private Map<String, Session> sessionMap;


    public static void loadInfo(String filePath) throws Exception {
        studentInfoHandler = new StudentInfoHandler(filePath);

    }

    public static void loadInfo( Map<Integer,Student> studentMap,Map<String,Session> sessionMap) throws Exception {
        StudentInfoHandler studentInfoHandler = new StudentInfoHandler(studentMap, sessionMap);
    }

    public static StudentInfoHandler getInstance() {
        return studentInfoHandler;
    }

    private StudentInfoHandler(String filePath) throws IOException {
        this.studentMap = new HashMap<>();
        this.sessionMap = new HashMap<>();
        readStudentInfo(filePath);
    }

    private StudentInfoHandler(Map<Integer, Student> studentMap, Map<String,Session>sessionMap) {

        this.studentMap = studentMap;
        this.sessionMap = sessionMap;
    }

    public void addStudent(Student student){
        int sid = student.getSid();
        studentMap.put(sid,student);
        System.out.println("add student sid: " + sid);
    }

    public void deleteStudentBySid(int sid){
        studentMap.remove(sid);
        System.out.println("remove student sid: " + sid);
    }
    public Student getStudentBySid(int sid){
        return studentMap.get(sid);
    }

    /**
     * read student information in .txt file into the map
     * @param filePath
     */

    private void readStudentInfo(String filePath) throws IOException {

        BufferedReader bufferedReader =null;
        try {
            bufferedReader = new BufferedReader(new FileReader(filePath));
            String line = bufferedReader.readLine();
            while ( line!= null){
                String[] strings = line.split(",");
                int sid = Integer.parseInt(strings[0]);
                String pwd = strings[1];
                studentMap.put(sid,new Student(sid,pwd));
                line = bufferedReader.readLine();
            }
        } catch (Exception e) {
            System.out.println("failed to open " + filePath);
            throw new RuntimeException(e);
        }finally {
            bufferedReader.close();
        }
    }

    public void writeStudentInfo(String filePath) throws Exception {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(filePath));
        System.out.println(filePath);
        objectOutputStream.writeObject(studentMap);
        objectOutputStream.close();
    }
    
    public void addSession(Session session){
        sessionMap.put(session.getSessionId(), session);
    }

    public Session removeSessionById(String sessionId){

        return sessionMap.remove(sessionId);
    }

    public Session getSessionById(String sessionId){
        return sessionMap.get(sessionId);
    }

    public Student getStudentBySessionId(String sessionId){

        Session session = sessionMap.get(sessionId);

        return studentMap.get(session.getSid());
    }

}
