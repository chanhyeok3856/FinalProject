package org.example.finalproject33.service;

import org.example.finalproject33.model.Wishlist;
import org.example.finalproject33.repository.WishlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishlistService {

    private final WishlistRepository wishlistRepository;

    @Autowired
    public WishlistService(WishlistRepository wishlistRepository) {
        this.wishlistRepository = wishlistRepository;
    }

    public Wishlist addToWishlist(int userId, int productId) {
        Wishlist wishlist = new Wishlist();
        wishlist.setUserId(userId);
        wishlist.setProductId(productId);
        return wishlistRepository.save(wishlist);
    }

    public List<Wishlist> getWishlistByUserId(int userId) {
        return wishlistRepository.findByUserId(userId);
    }
}
