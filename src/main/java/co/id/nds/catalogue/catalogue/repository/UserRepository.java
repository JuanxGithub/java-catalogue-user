package co.id.nds.catalogue.catalogue.repository;

import co.id.nds.catalogue.catalogue.entities.UserEntity;
import co.id.nds.catalogue.catalogue.globals.GlobalConstant;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<UserEntity, Integer>, JpaSpecificationExecutor<UserEntity> {

    @Query(value = "SELECT COUNT(*) FROM ms_user WHERE id = :id ", nativeQuery = true)
    long countById(@Param("id") String id);

    @Query(value = "SELECT COUNT(*) FROM ms_user WHERE rec_status = '"
            + GlobalConstant.REC_STATUS_ACTIVE
            + "' AND LOWER(full_name) = LOWER(:fullName)", nativeQuery = true)
    long countByFullName(@Param("fullName") String fullName);

    @Query(value = "SELECT COUNT(*) FROM ms_user WHERE call_number = :callNumber ", nativeQuery = true)
    long countByCallNumber(@Param("callNumber") String callNumber);

    @Modifying
    @Query(value = "UPDATE ms_user SET rec_status = '"
            + GlobalConstant.REC_STATUS_NON_ACTIVE
            + "', deleter_id = ?2 , deleted_date = NOW() "
            + "WHERE id = ?1", nativeQuery = true)
    Integer doDelete(Integer id, Integer deleterId);

}
