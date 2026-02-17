package com.anyessglobal.cms_backend.controller;

import com.anyessglobal.cms_backend.model.Lead; // Ensure this matches your package
import com.anyessglobal.cms_backend.repository.LeadRepository;
import com.anyessglobal.cms_backend.service.EmailService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LeadController {

    private final LeadRepository leadRepository;
    private final EmailService emailService;

    // --- 1. THE MISSING PUBLIC ENDPOINT ---
    // This allows the website contact form to save leads without being logged in
    @PostMapping("/public/contact")
    public ResponseEntity<Lead> submitInquiry(@RequestBody Lead lead) {
        return ResponseEntity.ok(leadRepository.save(lead));
    }

    // --- 2. PRIVATE ENDPOINTS (Admin Dashboard Only) ---

    @GetMapping("/leads")
    public List<Lead> getAllLeads() {
        return leadRepository.findAllByOrderBySubmittedAtDesc();
    }

//    @PostMapping("/leads/{id}/reply")
//    public ResponseEntity<String> replyToLead(@PathVariable Long id, @RequestBody ReplyRequest request) {
//        Lead lead = leadRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Inquiry not found"));
//
//        emailService.sendReply(
//                lead.getEmail(),
//                lead.getSubject(),
//                request.getMessage()
//        );
//
//        lead.setRead(true);
//        lead.setReplied(true);
//        leadRepository.save(lead);
//
//        return ResponseEntity.ok("Reply sent successfully");
//    }
@PostMapping("/leads/{id}/reply")
public ResponseEntity<String> replyToLead(@PathVariable Long id, @RequestBody ReplyRequest request) {
    Lead lead = leadRepository.findById(id).orElseThrow();

    // Send using the persistent Ref ID from the database
    emailService.sendReplyWithAttachment(
            lead.getEmail(),
            lead.getSubject(),
            request.getMessage(),
            lead.getReferenceId()
    );

    lead.setRead(true);
    lead.setReplied(true);
    leadRepository.save(lead);

    return ResponseEntity.ok("Reply sent successfully");
}

    @PutMapping("/leads/{id}/read")
    public ResponseEntity<Void> markAsRead(@PathVariable Long id) {
        Lead lead = leadRepository.findById(id).orElseThrow();
        lead.setRead(true);
        leadRepository.save(lead);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/leads/{id}")
    public ResponseEntity<Void> deleteLead(@PathVariable Long id) {
        leadRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @Data
    public static class ReplyRequest {
        private String message;
    }
}