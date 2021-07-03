package com.kc.lmsng.Repository;

import com.kc.lmsng.Entity.SubGroup;
import com.kc.lmsng.Entity.SubGroupKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubGroupRepository extends JpaRepository<SubGroup, SubGroupKey> {
     //List<SubGroup> findAllBySubGroupId(Integer subGroupId);
}
