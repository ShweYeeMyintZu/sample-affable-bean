package com.example.sampleaffablebean.ds;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SessionScope
@Component
public class Cart {
    private Set<CartItem> cartItems = new HashSet<>();

    public Set<CartItem> getCartItems() {
        return cartItems;
    }

    public void addToCart(CartItem cartItem) {
        this.cartItems.add(cartItem);
    }

    public void removeFromCart(CartItem cartItem) {
        this.cartItems.remove(cartItem);
    }

    public void clearCart() {
        this.cartItems.clear();
    }

    public int cartSize() {
        return this.cartItems.size();
    }

    public void setCartItems(Set<CartItem> cartItems) {
        this.cartItems = cartItems;
    }


}
