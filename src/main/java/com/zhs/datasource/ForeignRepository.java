package com.zhs.datasource;

import com.zhs.entities.Foreign;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ForeignRepository extends JpaRepository<Foreign,Integer> {
}
