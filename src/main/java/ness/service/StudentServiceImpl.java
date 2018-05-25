package ness.service;

import ness.model.Student;
import ness.model.Student;
import org.apache.commons.lang3.text.StrSubstitutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Service
public class StudentServiceImpl implements StudentService {

    @Value("${rest.hosturl}")
    private static String restHostUrl;

    private static final String getStudent = "${host}/students?id=${id}";
    private static final String getStudents = "${host}/students";
    private static final String addStudent = "${host}/students";
    private static final String editStudent = "${host}/students";
    private static final String removeStudent = "${host}/students?id=${id}";

    private RestTemplate restTemplate;

    private Logger logger = Logger.getLogger(getClass().getName());

    @Autowired
    public StudentServiceImpl() {

        this.restTemplate = new RestTemplate();
    }

    @Override
    public void addStudent(Student Student) {
        logger.info("Executing PUT method to add Student " + Student);

        Map valuesMap = new HashMap();
        valuesMap.put("host", restHostUrl);

        StrSubstitutor sub = new StrSubstitutor(valuesMap);
        String req = sub.replace(addStudent);

        restTemplate.put(req, Student);
    }

    @Override
    public void updateStudent(Student Student) {
        logger.info("Executing POST method to update Student " + Student);

        Map valuesMap = new HashMap();
        valuesMap.put("host", restHostUrl);

        StrSubstitutor sub = new StrSubstitutor(valuesMap);
        String req = sub.replace(editStudent);

        restTemplate.postForEntity(req, Student, Student.class);
    }


    @Override
    public void removeStudent(Student Student) {
        logger.info("Executing DELETE method to delete Student " + Student);

        Map valuesMap = new HashMap();
        valuesMap.put("host", restHostUrl);
        valuesMap.put("id", Student.getId());

        StrSubstitutor sub = new StrSubstitutor(valuesMap);
        String req = sub.replace(removeStudent);

        restTemplate.delete(req);
    }

    @Override
    public void removeStudent(int id) {
        logger.info("Executing DELETE method to delete Student by id = " + id);

        Map valuesMap = new HashMap();
        valuesMap.put("host", restHostUrl);
        valuesMap.put("id", id);

        StrSubstitutor sub = new StrSubstitutor(valuesMap);
        String req = sub.replace(removeStudent);

        restTemplate.delete(req);
    }

    @Override
    public Student getStudentById(int id) {
        Map valuesMap = new HashMap();

        valuesMap.put("host", restHostUrl);
        valuesMap.put("id", id);

        StrSubstitutor sub = new StrSubstitutor(valuesMap);
        String req = sub.replace(getStudent);

        return restTemplate.getForObject(req, Student.class);
    }

    @Override
    public List<Student> getStudents() {
        Map valuesMap = new HashMap();

        valuesMap.put("host", restHostUrl);

        StrSubstitutor sub = new StrSubstitutor(valuesMap);
        String req = sub.replace(getStudents);

        return restTemplate.getForObject(req, ArrayList.class);
    }
}
