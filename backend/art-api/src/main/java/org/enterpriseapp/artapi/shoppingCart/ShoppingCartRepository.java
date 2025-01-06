package org.enterpriseapp.artapi.shoppingCart;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {

    ShoppingCart getShoppingCartByUserId(Long UserId);
}
