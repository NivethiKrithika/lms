package com.ninja.lms.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.sql.Timestamp;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tbl_lms_program")
public class LmsProgram {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    Integer program_id;
    @NotNull(message = "program name cannot be null")
    @Size(max = 50,message = "Program name cannot be more than 50 chars long")
    String program_name;
    @Size(max =50, message = "program description cannot be more than 50 chars long")
    String program_description;
    @NotNull(message = "Program status cannot be null")
    @Size(max =50, message = "program status cannot be more than 50 chars long")
    String program_status;
    @NotNull(message = "Creation time cannot be null")
    @PastOrPresent(message = "creation time of batch must be past or present")
    Timestamp creation_time;
    @NotNull(message = "Modified time cannot be null")
    @PastOrPresent(message = "modified time of batch must be past or present")
    Timestamp last_mod_time;
    //@OneToMany(mappedBy = "program",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    //private Set<LmsBatch> batch;
}
