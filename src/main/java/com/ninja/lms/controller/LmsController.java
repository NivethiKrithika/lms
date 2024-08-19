package com.ninja.lms.controller;

import com.ninja.lms.entity.LmsBatch;
import com.ninja.lms.entity.LmsProgram;
import com.ninja.lms.jpa.LmsBatchRepository;
import com.ninja.lms.jpa.LmsProgramRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
                throw new RecordNotFoundException("Batch "+id+" not found");
        }
        @PostMapping(path="/batch")
        public LmsBatch saveBatch(@RequestBody LmsBatch batch)
        {
            return lmsBatchRepo.save(batch);
        }
        @PutMapping(path="/batch/{id}")
        public LmsBatch updateBatch(@PathVariable Integer id,@RequestBody LmsBatch batch)
        {
            return lmsBatchRepo.save(batch);
        }
        @DeleteMapping("/batch/{id}")
        public void deleteBatchById(@PathVariable Integer id) throws RecordNotFoundException
        {
            Optional<LmsBatch> lmsBatch = lmsBatchRepo.findById(id);
            if(lmsBatch.isPresent())
                lmsBatchRepo.deleteById(id);
            else
                throw new RecordNotFoundException("Batch "+id+  " not found");
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
        public Optional<LmsProgram> getProgramById(@PathVariable Integer id)
        {
            Optional<LmsProgram> lmsProgram =  lmsProgramRepo.findById(id);
            if(lmsProgram.isPresent())
                return lmsProgram;
            else
                throw new RecordNotFoundException("Program "+id+" not found");
        }
        @PostMapping(path="/program")
        public LmsProgram saveProgram(@RequestBody LmsProgram program)
        {
            return lmsProgramRepo.save(program);
        }
        @PutMapping(path="/program/{id}")
        public LmsProgram updateProgram(@PathVariable Integer id,@RequestBody LmsProgram program)
        {
            return lmsProgramRepo.save(program);
        }
        @DeleteMapping("/program/{id}")
        public void deleteProgramById(@PathVariable Integer id) throws RecordNotFoundException
        {
            Optional<LmsProgram> lmsProgram =  lmsProgramRepo.findById(id);
            if(lmsProgram.isPresent())
                lmsProgramRepo.deleteById(id);
            else
                throw new RecordNotFoundException(("Program "+id+" not found"));
        }
        @DeleteMapping("/program")
        public void deleteProgram()
        {
            lmsProgramRepo.deleteAll();
        }

    }

