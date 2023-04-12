package com.example.demojpa;

import com.example.demojpa.entity.Student;
import com.example.demojpa.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
public class StudentRepositoryTest {

    private StudentRepository studentRepository;

    @Autowired
    public StudentRepositoryTest(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    private Student getStudent(){
        return new Student(1L,"John",20,"Paris","0120340549","john@gmail.com");
    }

    @Test
    public void testFindById() {
        Student student = getStudent();
        studentRepository.save(student);
        Student result = studentRepository.findById(1L).get();
        assertEquals(student.getId(),result.getId());
    }

    @Test
    public void testFindByAddress() {
        Student student = getStudent();
        studentRepository.save(student);
        Student result = studentRepository.findByAddress("Paris");
        assertEquals(student.getAddress(),result.getAddress());
    }

    @Test
    public void testDeleteStudent(){
        Student student = getStudent();
        studentRepository.save(student);
        studentRepository.deleteById(1L);
        assertEquals(Optional.empty(),studentRepository.findById(1L));
    }
}
