package com.kc.lmsng.Repository;

import com.kc.lmsng.Entity.SubGroupMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubGroupMasterRepository extends JpaRepository<SubGroupMaster, Integer> {
}
