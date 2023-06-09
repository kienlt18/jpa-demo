package com.example.demojpa.service;

import com.example.demojpa.controller.dto.StudentDTO;
import com.example.demojpa.entity.Student;
import com.example.demojpa.entity.Student_;
import com.example.demojpa.entity.specification.StudentSpecification;
import com.example.demojpa.repository.StudentRepository;
import com.example.demojpa.utils.PageUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
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

    // Test custom query @Query
    public ResponseEntity<List<StudentDTO>> findAllByName(String name){
        System.out.println(name);
        List<Student> students = studentRepository.findAllByName(name);
        System.out.println(students.size());
        if(!students.isEmpty()){
            List<StudentDTO> studentDTOS = new ArrayList<>();
            for(Student student: students){
                StudentDTO studentDTO = new StudentDTO();
                BeanUtils.copyProperties(student,studentDTO);
                studentDTOS.add(studentDTO);
            }
            return ResponseEntity.ok(studentDTOS);
        }
        return ResponseEntity.ok(Collections.emptyList());
    }

    public ResponseEntity<StudentDTO> findById(Long id){
        Optional<Student> optionalStudent = studentRepository.findById(id);
        // check if student exists
        if(!optionalStudent.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Student student = optionalStudent.get();
        StudentDTO studentDTO = new StudentDTO();
        BeanUtils.copyProperties(student,studentDTO);
        return ResponseEntity.ok(studentDTO);
    }

    public Student findByAddress(String address){
        return studentRepository.findByAddress(address);
    }

    public ResponseEntity<List<StudentDTO>> findByAge(int age){
        List<Student> students = studentRepository.findByAge(age);
        if(!students.isEmpty()){
            List<StudentDTO> studentDTOS = new ArrayList<>();
            for(Student student: students){
                StudentDTO studentDTO = new StudentDTO();
                BeanUtils.copyProperties(student,studentDTO);
                studentDTOS.add(studentDTO);
            }
            return ResponseEntity.ok(studentDTOS);
        }
        return ResponseEntity.ok(Collections.emptyList());
    }

    // Native query
    public ResponseEntity<StudentDTO> findStudentUsingNative(Long id){
        Optional<Student> optionalStudent = studentRepository.findStudentById(id);
        if(!optionalStudent.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Student student = optionalStudent.get();
        StudentDTO studentDTO = new StudentDTO();
        BeanUtils.copyProperties(student,studentDTO);
        return ResponseEntity.ok(studentDTO);
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

    // Class-based projection
    public ResponseEntity<StudentDTO> findStudentByPhone(String phone){
        StudentDTO studentDTO = studentRepository.findByPhone(phone);
        return ResponseEntity.ok(studentDTO);
    }

    public ResponseEntity<List<StudentDTO>> filter(String keyword, String sortField, Integer offset, Integer limit){
        Specification<Student> studentSpecification = getStudentSpecification(keyword);
        PageRequest pageRequest = PageUtil.getPageRequest(sortField,offset,limit);
        Page<Student> students = studentRepository.findAll(studentSpecification, pageRequest);
        List<StudentDTO> studentDTOS = new ArrayList<>();
        for(Student student: students.getContent()){
            StudentDTO studentDTO = new StudentDTO();
            BeanUtils.copyProperties(student,studentDTO);
            studentDTOS.add(studentDTO);
        }
        return ResponseEntity.ok(studentDTOS);
    }

    private Specification<Student> getStudentSpecification(String keyword){
        return Specification.where(
                StudentSpecification.likeKeySearch(keyword, Student_.NAME)
                        .or(StudentSpecification.likeKeySearch(keyword, Student_.EMAIL))
                        .or(StudentSpecification.likeKeySearch(keyword, Student_.ADDRESS))
                        .or(StudentSpecification.likeKeySearch(keyword, Student_.PHONE))
        );
    }

}
