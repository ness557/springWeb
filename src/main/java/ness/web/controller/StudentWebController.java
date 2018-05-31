package ness.web.controller;

import ness.web.model.Student;
import ness.web.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/students")
public class StudentWebController {

    private StudentService service;

    @Autowired
    public StudentWebController(StudentService studentService) {
        this.service = studentService;
    }

    @RequestMapping
    public String index() {
        return "redirect:students/list";
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String getStudents(Model model) {
        model.addAttribute("studentsList", service.getStudents());
        return "show_student";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addStudent(Model model) {

        Student student = new Student();

        model.addAttribute("student", student);
        return "form_student";
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String updateStudent(@RequestParam(value = "id") int id,
                                Model model) {

        model.addAttribute("student", service.getStudentById(id));
        return "form_student";
    }

    @RequestMapping(value = "/remove", method = RequestMethod.GET)
    public String deleteStudent(@RequestParam(value = "id") int id) {

        service.removeStudent(id);
        return "redirect:list";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveUser(@ModelAttribute("student") Student student, BindingResult result1){

        if(student.getId() == 0)
            service.addStudent(student);
        else
            service.updateStudent(student);

        return "redirect:list";
    }

}
