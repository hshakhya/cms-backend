package com.anyessglobal.cms_backend.repository;

import com.anyessglobal.cms_backend.model.Lead;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface LeadRepository extends JpaRepository<Lead, Long> {
    // This ensures new inquiries appear at the top of your dashboard
    List<Lead> findAllByOrderBySubmittedAtDesc();
}
