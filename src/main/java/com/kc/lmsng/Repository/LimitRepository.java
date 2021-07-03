package com.kc.lmsng.Repository;

import com.kc.lmsng.Entity.Limits;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LimitRepository extends JpaRepository<Limits, Integer> {
}
