package com.example.demojpa;

import com.example.demojpa.controller.dto.StudentDTO;
import com.example.demojpa.entity.Student;
import com.example.demojpa.entity.Student_;
import com.example.demojpa.entity.specification.StudentSpecification;
import com.example.demojpa.projection.StudentView;
import com.example.demojpa.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = DemoJpaApplication.class)
@Transactional
public class StudentRepositoryTest {

    private StudentRepository studentRepository;

    @Autowired
    public StudentRepositoryTest(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    private Student getStudent(){
        return new Student(1L,"John",25,"Paris","0120340549","john@gmail.com");
    }

    @Test
    public void testFindById() {
        Student student = getStudent();
        studentRepository.save(student);
        Student result = studentRepository.findById(1L).get();
        System.out.println(result);
        assertEquals(1L, result.getId());
    }

    @Test
    public void testFindByAddress() {
        Student student = getStudent();
        studentRepository.save(student);
        Student result = studentRepository.findByAddress("Paris");
        assertEquals(student.getAddress(),result.getAddress());
    }

    @Test
    public void testFindByAge() {
        Student student = getStudent();
        studentRepository.save(student);
        List<Student> results = studentRepository.findByAge(25);
        assertEquals(1,results.size());
    }

    @Test
    public void testSaveStudent(){
        Student student = getStudent();
        studentRepository.save(student);
        Student found = studentRepository.findById(1L).get();
        assertEquals(student.getId(),found.getId());
    }

    @Test
    public void testDeleteStudent(){
        Student student = getStudent();
        studentRepository.save(student);
        studentRepository.deleteById(1L);
        assertEquals(Optional.empty(),studentRepository.findById(1L));
    }

    @Test
    public void testProjection(){
        StudentView studentView = studentRepository.findByEmail("namphuquoc@gmail.com");
        System.out.println(studentView.getAgeAndEmail());
        assertEquals("Nam",studentView.getName());
        assertEquals("3454346745",studentView.getPhone());
        assertEquals("Age: 28, Email: namphuquoc@gmail.com",studentView.getAgeAndEmail());
    }

    @Test
    public void testClassBasedProjection(){
        StudentDTO studentDTO = studentRepository.findByPhone("023435405");
        assertEquals(1,studentDTO.getId());
        assertEquals("Edward",studentDTO.getName());
        assertEquals(18,studentDTO.getAge());
        assertEquals("London",studentDTO.getAddress());
        assertEquals("023435405",studentDTO.getPhone());
        assertEquals("edw@gmail.com",studentDTO.getEmail());
    }

    @Test
    public void testSpecification(){
        Specification<Student> studentSpecification = getStudentSpecification("oc");
        List<Student> students = studentRepository.findAll(studentSpecification);
        System.out.println(students.get(0));
        assertEquals(1,students.size());
    }

    private Specification<Student> getStudentSpecification(String keyword){
        return Specification.where(
                StudentSpecification.likeKeySearch(keyword, Student_.NAME)
                        .or(StudentSpecification.likeKeySearch(keyword, Student_.EMAIL))
        );
    }
}
