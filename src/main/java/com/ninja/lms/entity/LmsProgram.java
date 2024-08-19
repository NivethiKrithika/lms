package com.ninja.lms.entity;

import jakarta.persistence.*;
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
            @GeneratedValue(strategy = GenerationType.AUTO)
    Integer program_id;
    String program_name;
    String program_description;
    String program_status;
    Timestamp creation_time;
    Timestamp last_mod_time;
}
