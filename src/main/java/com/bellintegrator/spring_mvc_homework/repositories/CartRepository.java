package com.bellintegrator.spring_mvc_homework.repositories;

import com.bellintegrator.spring_mvc_homework.entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    @Query("Select c from Cart c join c.bill b join b.user u where u.id = :userId")
    List<Cart> getAllCartsByUserId(@Param("userId") Long userId);
}
