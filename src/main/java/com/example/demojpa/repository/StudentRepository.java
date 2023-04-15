package com.example.demojpa.repository;

import com.example.demojpa.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    Student findByAddress(String address);

    List<Student> findByAge(int age);

    // Custom query
    @Query(value = "Select s from Student s where s.name like %:name% order by s.age asc")
    List<Student> findAllByName(@Param("name") String name);

    // Native query
    @Query(value = "Select * from student where student.id = :id", nativeQuery = true)
    Optional<Student> findStudentById(@Param("id") Long id);

}
