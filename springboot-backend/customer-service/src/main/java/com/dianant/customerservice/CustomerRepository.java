package com.dianant.customerservice;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    public List<Customer> findByFirstNameIgnoreCaseContaining(String keyword);
    public List<Customer> findByLastNameIgnoreCaseContaining(String keyword);
    public List<Customer> findByPhoneNumberContaining(String keyword);
    public List<Customer> findByEmailIdIgnoreCaseContaining(String keyword);
    public boolean existsByEmailId(String emailId);
    public boolean existsByPhoneNumber(String phoneNumber);
    public Optional<Customer> findByEmailId(String email);
}
