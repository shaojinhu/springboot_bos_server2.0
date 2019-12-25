package com.bos.basic.repository;

import com.bos.pojo.basic.SubArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SubAreaRepository extends JpaRepository<SubArea,String>, JpaSpecificationExecutor<SubArea> {
}
