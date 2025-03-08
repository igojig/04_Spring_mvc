package com.bellintegrator.spring_mvc_homework.repositories;

import com.bellintegrator.spring_mvc_homework.entities.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BillRepository extends JpaRepository<Bill, Long> {

    @Query("Select b from Bill b join b.user u where u.id= :userId")
    List<Bill> getAllByUserId(@Param("userId") Long userId);

    @Query("Select b from Bill b join b.user u where u.id= :userId and b.billType= :billType")
    Optional<Bill> getBillByUserIdAndType(@Param("userId") Long id, @Param("billType") Bill.BillType billType);
}
