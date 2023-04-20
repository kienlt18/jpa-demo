package com.example.demojpa.entity.specification;

import com.example.demojpa.entity.Student;
import org.springframework.data.jpa.domain.Specification;

public class StudentSpecification {

    public static Specification<Student> likeKeySearch(String key, String property){
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get(property), "%" + key + "%");
    }

    public static Specification<Student> eqIdAndAge(int value, String property){
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(property), value);
    }

}
