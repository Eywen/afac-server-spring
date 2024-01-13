package com.tfm.afac.data.daos;

import com.tfm.afac.data.model.GuideEntity;
import org.springframework.beans.PropertyValues;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface GuideRepository extends JpaRepository<GuideEntity, Long >, JpaSpecificationExecutor<GuideEntity> {

    Optional<GuideEntity> findByIdGuide(long idGuide);

    List<GuideEntity> findByEmployeeId(Integer employeeId);

    List<GuideEntity> findByCustomerId(Integer customerId);

    List<GuideEntity> findByStatus(String status);

    List<GuideEntity> findByEntryDate(Date entryDate);

    List<GuideEntity> findByDeliveryDate(Date deliveryDate);

    List<GuideEntity> findByAssignmentDate(Date assignmentDate);
}
