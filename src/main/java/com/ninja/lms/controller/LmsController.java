package com.ninja.lms.controller;

import com.ninja.lms.entity.LmsBatch;
import com.ninja.lms.entity.LmsProgram;
import com.ninja.lms.jpa.LmsBatchRepository;
import com.ninja.lms.jpa.LmsProgramRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
        public ResponseEntity<String> saveBatch(@RequestBody LmsBatch batch, BindingResult bindingResult)
        {
            if(bindingResult.hasErrors())
                return ResponseEntity.badRequest().body("validation errors found in batch details");
            lmsBatchRepo.save(batch);
            return ResponseEntity.ok("batch created successfully");
        }
        @PutMapping(path="/batch/{id}")
        public ResponseEntity<String> updateBatch(@PathVariable Integer id,@RequestBody LmsBatch batch,BindingResult bindingresult) throws RecordNotFoundException
        {

            if(lmsBatchRepo.findById(id).isPresent()) {
                if(bindingresult.hasErrors())
                    return ResponseEntity.badRequest().body("Validation errors found in batch details");
                lmsBatchRepo.save(batch);
                return ResponseEntity.ok("Batch updated successfully");
            }
            else
                throw new RecordNotFoundException("Batch id " +id+ " not found to update");

        }
        @DeleteMapping("/batch/{id}")
        public void deleteBatchById(@PathVariable Integer id) throws RecordNotFoundException
        {
            Optional<LmsBatch> lmsBatch = lmsBatchRepo.findById(id);
            if(lmsBatch.isPresent())
                lmsBatchRepo.deleteById(id);
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
        public ResponseEntity<String> saveProgram(@RequestBody LmsProgram program,BindingResult bindingResult)
        {
            if(bindingResult.hasErrors())
                return ResponseEntity.badRequest().body("Validation errors found in program details");
            lmsProgramRepo.save(program);
            return ResponseEntity.ok("Program created successfully");
        }
        @PutMapping(path="/program/{id}")
        public ResponseEntity<String> updateProgram(@PathVariable Integer id, @RequestBody LmsProgram program, BindingResult bindingresult) throws RecordNotFoundException
        {
            if(lmsProgramRepo.findById(id).isPresent()) {
                if(bindingresult.hasErrors())
                    return ResponseEntity.badRequest().body("Validation errors found in program details");
                lmsProgramRepo.save(program);
                return ResponseEntity.ok("Program updated successfully");
            }
            else
                throw new RecordNotFoundException("Program id " +id+ " not found to update");
        }
        @DeleteMapping("/program/{id}")
        public void deleteProgramById(@PathVariable Integer id) throws RecordNotFoundException
        {
            Optional<LmsProgram> lmsProgram =  lmsProgramRepo.findById(id);
            if(lmsProgram.isPresent())
                lmsProgramRepo.deleteById(id);
            else
                throw new RecordNotFoundException(("Program id "+id+" not found to delete"));
        }
        @DeleteMapping("/program")
        public void deleteProgram()
        {
            lmsProgramRepo.deleteAll();
        }

    }

