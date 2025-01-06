package org.enterpriseapp.artapi.shoppingCart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ShoppingCartController {

    @Autowired
    ShoppingCartService shoppingCartService;

    @GetMapping("/shopping-cart/{userId}")
    public ShoppingCartDTO getShoppingCart (@PathVariable long userId){

        return shoppingCartService.getShoppingCart(userId);
    }
}
