package com.ninja.lms.entity;

import com.ninja.lms.jpa.LmsBatchRepository;
import com.ninja.lms.jpa.LmsProgramRepository;
import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "tbl_lms_batch")
public class LmsBatch {
    @Id
            @GeneratedValue(strategy = GenerationType.AUTO)
    Integer batch_id;
    String batch_name;
    String batch_description;
    String batch_status;
    Integer batch_program_id;
    Integer batch_no_of_classes;
    Timestamp creationTime;
    Timestamp last_mod_time;
}
