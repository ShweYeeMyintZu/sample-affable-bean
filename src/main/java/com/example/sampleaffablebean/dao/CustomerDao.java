package com.example.sampleaffablebean.dao;

import com.example.sampleaffablebean.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerDao extends JpaRepository<Customer,Integer> {
}
