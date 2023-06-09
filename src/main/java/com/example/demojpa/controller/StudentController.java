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

    @GetMapping("/find-by-name")
    public ResponseEntity<List<StudentDTO>> findAllByName(@RequestParam(name = "name") String name){
        return studentService.findAllByName(name);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentDTO> getById(@PathVariable(name = "id") Long id){
        return studentService.findById(id);
    }

    @GetMapping("/native/{id}")
    public ResponseEntity<StudentDTO> getByIdNative(@PathVariable(name = "id") Long id){
        return studentService.findStudentUsingNative(id);
    }

    @GetMapping("/find-by-address")
    public ResponseEntity<Student> findByAddress(@RequestParam(name = "address") String address){
        return ResponseEntity.ok(studentService.findByAddress(address));
    }

    @GetMapping("/find-by-age")
    public ResponseEntity<List<StudentDTO>> findByAge(@RequestParam(name = "age") int age){
        return studentService.findByAge(age);
    }

    @GetMapping("/find-by-phone")
    public ResponseEntity<StudentDTO> findByPhone(@RequestParam(name = "phone") String phone){
        return studentService.findStudentByPhone(phone);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<StudentDTO>> filterStudent(@RequestParam(name = "keyword") String keyword,
                                                          @RequestParam(name = "sortField", required = false) String sortField,
                                                          @RequestParam(name = "offset") Integer offset,
                                                          @RequestParam(name = "limit") Integer limit){
        return studentService.filter(keyword, sortField, offset, limit);
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
