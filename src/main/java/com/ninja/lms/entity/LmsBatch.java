package com.ninja.lms.entity;

import com.ninja.lms.jpa.LmsBatchRepository;
import com.ninja.lms.jpa.LmsProgramRepository;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
//import jakarta.validation.constraints.NotNull;
//import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    Integer batch_id;
    @NotNull(message = "Batch name cannot be null")
    @Size(max =50, message = "batch name cannot be more than 50 chars long")
    String batch_name;
    @Size(max =50, message = "batch description cannot be more than 50 chars long")
    String batch_description;
    @NotNull(message = "Batch status cannot be null")
    @Size(max =50, message = "batch status cannot be more than 50 chars long")
    String batch_status;
    @NotNull(message = "program id cannot be null")
    //Column(name = "batch_program_id")
    Integer batch_program_id;
    @NotNull(message = "number of classes cannot be null")
    @Max(value = 10,message = "number of classes in batch has to less than or equal to 10")
    Integer batch_no_of_classes;
    @NotNull(message ="creation time cannot be null")
    @PastOrPresent(message = "creation time of batch must be past or present")
    Timestamp creationTime;
    @NotNull(message = "Creation time cannot be null")
    @PastOrPresent(message = "creation time of batch must be past or present")
    Timestamp last_mod_time;
    @ManyToOne()
    @JoinColumn(nullable = false,name = "batch_program_id", insertable=false, updatable=false)
    private LmsProgram program;
}
