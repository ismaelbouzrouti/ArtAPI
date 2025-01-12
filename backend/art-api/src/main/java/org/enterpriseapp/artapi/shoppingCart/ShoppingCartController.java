package org.enterpriseapp.artapi.shoppingCart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ShoppingCartController {

    @Autowired
    ShoppingCartService shoppingCartService;

    @GetMapping("/shopping-cart/{userId}")
    public ResponseEntity<Object> getShoppingCart (@PathVariable long userId) {

        try {

           return ResponseEntity.ok().body(shoppingCartService.getShoppingCart(userId));

        } catch (Exception e) {

            return ResponseEntity.internalServerError().body("shopping cart could not be retrieved");
        }
    }


    @PostMapping("/shoppingCart/{userId}/addItem")
    public ResponseEntity<String> addItemToShoppingCart(@RequestBody CartItemDTO cartItemDTO, @PathVariable String userId){

        System.out.println("in shopping cart controller");
        shoppingCartService.addItemToShoppingCart(cartItemDTO,Long.parseLong(userId));
        return ResponseEntity.ok().body("item succesfully added to shopping cart");


    }

    @PutMapping("/shopping-cart/increment/{itemId}")
    public ResponseEntity<String> incrementItemQuantity(@PathVariable Long itemId){
        System.out.println("in controller");
        try{
            shoppingCartService.incrementItem(itemId);
            return ResponseEntity.ok().body("item quantity successfully incremented");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("item quantity could not be incremented");
        }
    }


    @PutMapping("/shopping-cart/decrement/{itemId}")
    public ResponseEntity<String> decrementItemQuantity(@PathVariable Long itemId){
        System.out.println("in controller");
        try{
            shoppingCartService.decrementItem(itemId);
            return ResponseEntity.ok().body("item quantity successfully decremented");
        } catch (Exception e) {
           return ResponseEntity.badRequest().body("item quantity could not be decremented");
        }

    }

    @DeleteMapping("/shopping-cart/remove/{itemId}")
    public ResponseEntity<String> removeCartItem(@PathVariable Long itemId){
        try{
            shoppingCartService.removeItem(itemId);
            return ResponseEntity.ok().body("item removed successfully");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("item could not be removed");
        }

    }

    @PutMapping("/shopping-cart/return-date/{userId}")
    public ResponseEntity<String> changeReturnDate(@RequestBody String date, @PathVariable Long userId){

        try{

            shoppingCartService.changeReturnDate(date,userId);
            return ResponseEntity.ok().body("return date changed successfully");

        }catch (Exception e){
            return ResponseEntity.internalServerError().body("return date could not be changed");
        }

    }

    @DeleteMapping("/shopping-cart/checkout/{userId}")
    public ResponseEntity<String> checkout(@PathVariable Long userId){

        shoppingCartService.checkout(userId);

        return ResponseEntity.ok().body("successfully checked out");
    }



}
