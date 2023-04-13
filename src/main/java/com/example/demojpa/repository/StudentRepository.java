package com.example.demojpa.repository;

import com.example.demojpa.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    Student findByAddress(String address);

    List<Student> findByAge(int age);

    @Query(value = "Select s from Student s where s.name like %:name% order by s.age asc")
    List<Student> findAllByName(@Param("name") String name);
}
