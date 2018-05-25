package ness.service;

import ness.model.Student;

import java.util.List;

public interface StudentService {

    void addStudent(Student student);
    void updateStudent(Student student);
    void removeStudent(Student student);
    void removeStudent(int id);
    Student getStudentById(int id);
    List<Student> getStudents();

}
