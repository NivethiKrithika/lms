package com.ninja.lms.controller;

import com.ninja.lms.entity.LmsBatch;
import com.ninja.lms.entity.LmsProgram;
import com.ninja.lms.jpa.LmsBatchRepository;
import com.ninja.lms.jpa.LmsProgramRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@RestController
    @RequestMapping(path="/")
    public class LmsController
    {
        @Autowired
        public LmsBatchRepository lmsBatchRepo;
        @Autowired
        public LmsProgramRepository lmsProgramRepo;

        @GetMapping(path="/batch")
        public ResponseEntity<List<LmsBatch>> getLmsBatch()
        {
            List<LmsBatch> batches = lmsBatchRepo.findAll();
            return ResponseEntity.ok(batches);
        }

        @GetMapping(path="/batch/{id}")
        public Optional<LmsBatch> getBatchById(@PathVariable Integer id) throws RecordNotFoundException
        {
            Optional<LmsBatch> batch = lmsBatchRepo.findById(id);
            if(batch.isPresent())
                return batch;
            else
                throw new RecordNotFoundException("Batch id "+id+" not found");
        }

        @PostMapping(path="/batch")
        public ResponseEntity<String> saveBatch(@RequestBody @Valid LmsBatch batch, BindingResult bindingResult) throws RuntimeException{
            try {
            // Optional<LmsBatch> lmsBatch = lmsBatchRepo.findById(batch.getBatch_id());
            //if(lmsBatch.isPresent())
            //  return ResponseEntity.badRequest().body("Batch record with id "+batch.getBatch_id()+ " already present, cannot allow duplicate entry");
            if (bindingResult.hasErrors()) {
                List<ObjectError> allErrors = bindingResult.getAllErrors();
                StringBuilder error_message = new StringBuilder();
                for ( ObjectError o : allErrors) {
                    error_message.append(o.getDefaultMessage());
                    error_message.append("\n");
                }
                return ResponseEntity.badRequest().body(error_message.toString());
            }
                lmsBatchRepo.save(batch);
                return ResponseEntity.ok("batch created successfully");
            }
            catch(RuntimeException e ) {

                    System.out.println(e.getMessage());
                    if(e.getMessage().contains("foreign key constraint"))
                        throw new RuntimeException("This record violates the foreign key constraint. There is no matching record with program id "+batch.getBatch_program_id()+" in program table");
                    else if(e.getMessage().contains("violates unique constraint"))
                        throw new RuntimeException("Duplicate record. This record violates the unique constraint as batch name " + batch.getBatch_name() + " and batch program id " + batch.getBatch_program_id() + " is already mapped to a different batch id");
                    else
                        throw new RuntimeException(e.getMessage());
                 }
            }

        @PutMapping(path="/batch/{id}")
        public ResponseEntity<String> updateBatch(@PathVariable Integer id,@RequestBody @Valid LmsBatch batch,BindingResult bindingResult) throws RecordNotFoundException
        {
            try {
                if (lmsBatchRepo.findById(id).isPresent()) {
                    // Optional<LmsBatch> lmsBatch = lmsBatchRepo.findById(batch.getBatch_id());
                    //if(lmsBatch.isPresent())
                    //  return ResponseEntity.badRequest().body("Batch record with id "+batch.getBatch_id()+ " already present, cannot allow duplicate entry");
                    if (bindingResult.hasErrors()) {
                        List<ObjectError> allErrors = bindingResult.getAllErrors();
                        StringBuilder error_message = new StringBuilder();
                        for (ObjectError o : allErrors) {
                            error_message.append(o.getDefaultMessage());
                            error_message.append("\n");
                        }
                        return ResponseEntity.badRequest().body(error_message.toString());
                    }
                    batch.setBatch_id(id);
                    lmsBatchRepo.save(batch);
                    return ResponseEntity.ok("batch updated successfully");
                } else {
                    throw new RecordNotFoundException("Batch id " + id + " not found to update");
                }
            }
            catch(RuntimeException e ) {

                System.out.println(e.getMessage());
                if(e.getMessage().contains("foreign key constraint"))
                    throw new RuntimeException("This record violates the foreign key constraint. There is no matching record with program id "+batch.getBatch_program_id()+" in program table");
                else if(e.getMessage().contains("violates unique constraint"))
                    throw new RuntimeException("Duplicate record. This record violates the unique constraint as batch name " + batch.getBatch_name() + " and batch program id " + batch.getBatch_program_id() + " is already mapped to a different batch id");
                else
                    throw new RuntimeException(e.getMessage());
            }
        }
        @DeleteMapping("/batch/{id}")
        public ResponseEntity<String> deleteBatchById(@PathVariable Integer id) throws RecordNotFoundException
        {
            Optional<LmsBatch> lmsBatch = lmsBatchRepo.findById(id);
            if(lmsBatch.isPresent()) {
                lmsBatchRepo.deleteById(id);
                return ResponseEntity.ok("Batch deleted successfully");
            }
            else
                throw new RecordNotFoundException("Batch id "+id+  " not found to delete");
        }
        @DeleteMapping("/batch")
        public void deleteBatch()
        {
            lmsBatchRepo.deleteAll();
        }
        @GetMapping(path="/program")
        public List<LmsProgram> getProgramBatch()
        {
            return lmsProgramRepo.findAll();
        }
        @GetMapping(path="/program/{id}")
        public Optional<LmsProgram> getProgramById(@PathVariable Integer id) throws RecordNotFoundException
        {
            Optional<LmsProgram> lmsProgram =  lmsProgramRepo.findById(id);
            if(lmsProgram.isPresent())
                return lmsProgram;
            else
                throw new RecordNotFoundException("Program id "+id+" not found");
        }
        @PostMapping(path="/program")
        public ResponseEntity<String> saveProgram(@RequestBody @Valid LmsProgram program, BindingResult bindingResult)
        {
            try {
                if (bindingResult.hasErrors()) {
                    List<ObjectError> allErrors = bindingResult.getAllErrors();
                    StringBuilder error_message = new StringBuilder();
                    for (ObjectError o : allErrors) {
                        FieldError f = (FieldError)o;
                        error_message.append(f.getDefaultMessage());
                        error_message.append("\n");
                    }
                    return ResponseEntity.badRequest().body(error_message.toString());
                }
                lmsProgramRepo.save(program);
                return ResponseEntity.ok("Program created successfully");
            }
            catch(RuntimeException e)
            {
                if(e.getMessage().contains("violates unique constraint"))
                    throw new RuntimeException("Duplicate record. This record violates the unique constraint as program name " + program.getProgram_name()  + " is already present");
                else
                    throw new RuntimeException(e.getMessage());
            }
        }
        @PutMapping(path="/program/{id}")
        public ResponseEntity<String> updateProgram(@PathVariable Integer id, @RequestBody @Valid LmsProgram program, BindingResult bindingResult) throws RuntimeException
        {
            try {
                if (lmsProgramRepo.findById(id).isPresent()) {
                    if (bindingResult.hasErrors()) {
                        List<ObjectError> allErrors = bindingResult.getAllErrors();
                        StringBuilder error_message = new StringBuilder();
                        for (ObjectError o : allErrors) {
                            error_message.append(o.getDefaultMessage());
                            error_message.append("\n");
                        }
                        return ResponseEntity.badRequest().body(error_message.toString());
                    }
                    program.setProgram_id(id);
                    lmsProgramRepo.save(program);
                    return ResponseEntity.ok("Program updated successfully");
                } else
                    throw new RecordNotFoundException("Program id " + id + " not found to update");
            }
            catch(RuntimeException e)
            {
                if(e.getMessage().contains("violates unique constraint"))
                    throw new RuntimeException("Duplicate record. This record violates the unique constraint as program name " + program.getProgram_name()  + " is already present");
                else
                    throw new RuntimeException(e.getMessage());
            }

        }
        @DeleteMapping("/program/{id}")
        public ResponseEntity<String> deleteProgramById(@PathVariable Integer id) throws RecordNotFoundException
        {
            Optional<LmsProgram> lmsProgram =  lmsProgramRepo.findById(id);
            if(lmsProgram.isPresent()) {
                lmsProgramRepo.deleteById(id);
                return ResponseEntity.ok("Program deleted successfully");
            }
            else
                throw new RecordNotFoundException(("Program id "+id+" not found to delete"));
        }
        @DeleteMapping("/program")
        public void deleteProgram()
        {
            lmsProgramRepo.deleteAll();
        }
       @GetMapping("/programName/{batchId}")
        public ResponseEntity<String> getProgramName(@PathVariable Integer batchId) throws RecordNotFoundException
        {
              Optional<LmsBatch> lmsBatch = lmsBatchRepo.findById(batchId);
              if(lmsBatch.isPresent())
            {
                return ResponseEntity.ok(lmsBatch.get().getProgram().getProgram_name());
          //      Integer id = lmsBatch.get().getBatch_program_id();
            //    Optional<LmsProgram> lmsProgram = lmsProgramRepo.findById(id);
              //  return ResponseEntity.ok().body(lmsProgram.get().getProgram_name());


            }
            else
                throw new RecordNotFoundException("batch "+batchId+" not found");

        }

    }

