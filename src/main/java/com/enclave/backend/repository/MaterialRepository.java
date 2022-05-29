package com.enclave.backend.repository;

import com.enclave.backend.entity.Material;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface MaterialRepository extends JpaRepository<Material,Short> {

    @Query("SELECT U.unit FROM Material M, UnitConverter U, UnitMaterial as um WHERE um.material.id=M.id AND M.id = :materialId AND um.unitConverter.id = U.id")
    List<Object[]> getUnitByMaterial(@Param("materialId") short materialId);

//    @Query("SELECT U.unit FROM UnitConverter U")
//    List<Object[]> getUnitByMaterial();
}
