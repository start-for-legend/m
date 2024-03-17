package com.minsta.m.domain.leels.repository;

import com.minsta.m.domain.leels.entity.Leels;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LeelsRepository extends JpaRepository<Leels, Long> {

    @Query(value = "SELECT * FROM leels GROUP BY leels_id ORDER BY RAND() LIMIT 25", nativeQuery = true)
    List<Leels> findDistinctLeelsRandomly();

    List<Leels> findAllByHashtagsContains(String hash);
}
