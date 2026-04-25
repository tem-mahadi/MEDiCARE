package com.temmahadi.healthcare.RoomDB;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CartDao {
    @Insert
    void insert(CartItem cartItem);

    @Query("SELECT * FROM cart_items WHERE username = :username ORDER BY id DESC")
    List<CartItem> getByUser(String username);

    @Query("SELECT COUNT(*) FROM cart_items WHERE username = :username AND package_name = :packageName")
    int checkExists(String username, String packageName);

    @Query("DELETE FROM cart_items WHERE id = :id")
    void deleteById(int id);
    
    @Query("DELETE FROM cart_items WHERE username = :username")
    void clearCart(String username);
}
