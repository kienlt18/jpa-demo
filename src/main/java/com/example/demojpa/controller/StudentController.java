package com.example.demojpa.controller;

import com.example.demojpa.controller.dto.StudentDTO;
import com.example.demojpa.entity.Student;
import com.example.demojpa.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    private StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<StudentDTO>> getAllStudents(){
        return ResponseEntity.ok(studentService.getAll());
    }

    @GetMapping("/find-by-address")
    public ResponseEntity<Student> findByAddress(@RequestParam(name = "address") String address){
        return ResponseEntity.ok(studentService.findByAddress(address));
    }

    @PostMapping("/save")
    public ResponseEntity<String> saveStudent(@RequestBody StudentDTO studentDTO){
        return studentService.saveStudent(studentDTO);
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateStudent(@RequestBody StudentDTO studentDTO){
        return studentService.updateStudent(studentDTO);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable(name = "id") Long id){
        return studentService.deleteStudent(id);
    }
}
