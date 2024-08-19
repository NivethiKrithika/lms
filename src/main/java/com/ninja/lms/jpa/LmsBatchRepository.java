package com.ninja.lms.jpa;

import com.ninja.lms.entity.LmsBatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LmsBatchRepository extends JpaRepository<LmsBatch,Integer>
{

}