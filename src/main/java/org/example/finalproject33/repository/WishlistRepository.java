package org.example.finalproject33.repository;

import org.example.finalproject33.model.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface WishlistRepository extends JpaRepository<Wishlist,Integer> {
    List<Wishlist> findByUserId(int userId);
}
