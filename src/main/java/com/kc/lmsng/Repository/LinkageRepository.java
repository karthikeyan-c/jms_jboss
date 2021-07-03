package com.kc.lmsng.Repository;

import com.kc.lmsng.Entity.Linkage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LinkageRepository extends JpaRepository<Linkage, String> {
}
