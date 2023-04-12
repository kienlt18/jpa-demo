package com.example.demojpa.service;

import com.example.demojpa.controller.dto.StudentDTO;
import com.example.demojpa.entity.Student;
import com.example.demojpa.repository.StudentRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class StudentService {

    private StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<StudentDTO> getAll(){
        List<Student> students = studentRepository.findAll();
        if(!students.isEmpty()){
            List<StudentDTO> studentDTOS = new ArrayList<>();
            for(Student student: students){
               StudentDTO studentDTO = new StudentDTO();
               BeanUtils.copyProperties(student,studentDTO);
               studentDTOS.add(studentDTO);
            }
            return studentDTOS;
        }
        return Collections.emptyList();
    }

    public Student findByAddress(String address){
        return studentRepository.findByAddress(address);
    }

    public ResponseEntity<String> saveStudent(StudentDTO studentDTO){
        Student student = new Student();
        BeanUtils.copyProperties(studentDTO,student);
        studentRepository.save(student);
        return ResponseEntity.ok("Save successful!");
    }

    public ResponseEntity<String> updateStudent(StudentDTO studentDTO){
        Optional<Student> optionalStudent = studentRepository.findById(studentDTO.getId());
        // check if student exists
        if(!optionalStudent.isPresent()){
            return new ResponseEntity<>("Student not found!", HttpStatus.NOT_FOUND);
        }
        Student student = optionalStudent.get();
        BeanUtils.copyProperties(studentDTO,student);
        studentRepository.save(student);
        return ResponseEntity.ok("Update successful!");
    }

    public ResponseEntity<String> deleteStudent(Long id){
        Optional<Student> optionalStudent = studentRepository.findById(id);
        // check if student exists
        if(!optionalStudent.isPresent()){
            return new ResponseEntity<>("Student not found!", HttpStatus.NOT_FOUND);
        }
        studentRepository.deleteById(id);
        return ResponseEntity.ok("Delete successful!");
    }
}
