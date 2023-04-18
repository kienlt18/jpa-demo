package com.example.demojpa.projection;

import org.springframework.beans.factory.annotation.Value;

public interface StudentView {
    // closed projections
    String getName();
    String getPhone();
    // open projections
    @Value("#{'Age: ' + target.age + ', ' + 'Email: ' + target.email}")
    String getAgeAndEmail();
}
