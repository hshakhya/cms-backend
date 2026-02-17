//package com.anyessglobal.cms_backend.model;
//
//import jakarta.persistence.*;
//import lombok.*;
//import java.time.LocalDateTime;
//
//@Entity
//@Table(name = "leads")
//@Data
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
//public class Lead {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private String name;
//    private String email;
//    private String subject;
//
//    @Column(columnDefinition = "TEXT")
//    private String message;
//
//    @Column(name = "is_read")
//    private boolean read = false;
//
//    @Column(name = "is_replied")
//    private boolean replied = false;
//
//    @Column(name = "submitted_at")
//    private LocalDateTime submittedAt;
//
//    @PrePersist
//    protected void onCreate() {
//        this.submittedAt = LocalDateTime.now();
//    }
//}
package com.anyessglobal.cms_backend.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "leads")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Lead {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String subject;

    @Column(columnDefinition = "TEXT")
    private String message;

    // 🔥 CHANGE: Use Boolean (wrapper) instead of boolean (primitive)
    // This prevents the "IllegalArgumentException: Can not set boolean to null"
    @Column(name = "is_read", nullable = false)
    private Boolean read = false;

    @Column(name = "is_replied", nullable = false)
    private Boolean replied = false;

    @Column(name = "submitted_at")
    private LocalDateTime submittedAt;
    private String referenceId;

    @PrePersist
    protected void onCreate() {
        this.submittedAt = java.time.LocalDateTime.now();
        if (this.read == null) this.read = false;
        if (this.replied == null) this.replied = false;

        // Sequential Logic: AG + YYYYMMDD + Padded ID
        if (this.referenceId == null) {
            String datePart = java.time.LocalDate.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyyMMdd"));
            // Using ID if exists, otherwise a 3-digit hash of the timestamp for new entries
            String sequencePart = String.format("%03d", (this.id != null ? this.id : System.currentTimeMillis() % 1000));
            this.referenceId = "AG-" + datePart + "-" + sequencePart;
        }
    }
    
}