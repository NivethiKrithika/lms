package com.ninja.lms.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.sql.Timestamp;

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
    @NotNull
    @Size(max = 50,message = "Program name cannot be more than 50 chars long")
    String program_name;
    @Size(max =50, message = "program description cannot be more than 50 chars long")
    String program_description;
    @NotNull
    @Size(max =50, message = "program status cannot be more than 50 chars long")
    String program_status;
    @NotNull
    @PastOrPresent(message = "creation time of batch must be past or present")
    Timestamp creation_time;
    @NotNull
    @PastOrPresent(message = "modified time of batch must be past or present")
    Timestamp last_mod_time;
}
