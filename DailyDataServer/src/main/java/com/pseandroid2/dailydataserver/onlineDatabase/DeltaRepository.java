package com.pseandroid2.dailydataserver.onlineDatabase;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeltaRepository extends JpaRepository<Delta, DeltaID> {
}
