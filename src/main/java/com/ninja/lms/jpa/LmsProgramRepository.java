package com.ninja.lms.jpa;

import com.ninja.lms.entity.LmsProgram;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LmsProgramRepository extends JpaRepository<LmsProgram,Integer> {

}
