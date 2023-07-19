package com.example.sampleaffablebean.service;


import com.example.sampleaffablebean.dao.CategoryDao;
import com.example.sampleaffablebean.dao.CustomerDao;
import com.example.sampleaffablebean.dao.ProductDao;
import com.example.sampleaffablebean.ds.Cart;
import com.example.sampleaffablebean.ds.CartItem;

import com.example.sampleaffablebean.entity.Customer;
import com.example.sampleaffablebean.entity.Product;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductDao productDao;
    private final Cart cart;
    private final CategoryDao categoryDao;

    private final CustomerDao customerDao;




    public ProductService(ProductDao productDao, Cart cart, CategoryDao categoryDao, CustomerDao customerDao) {
        this.productDao = productDao;
        this.cart = cart;
        this.categoryDao = categoryDao;
        this.customerDao = customerDao;

    }


    public List<Product> listProducts(int categoryId) {
        return productDao.findProductByCategoryId(categoryId);
    }


    public Product findProductsById(int productid) {
        return productDao.findById(productid).orElseThrow(EntityNotFoundException::new);
    }
    public int getCategoryIdByProductId(int productId){
        return categoryDao.findCategoryByProductId(productId);
    }

    public Customer findCustomerById(int customerid){
        return customerDao.findById(customerid).orElseThrow(EntityNotFoundException::new);
    }


    public Set<CartItem> getCartItems() {
        return cart.getCartItems();
    }

    public int cartSize() {
        return cart.cartSize();
    }

    public void addToCart(int id) {

        Product product = findProductsById(id);
        cart.addToCart(new CartItem(
                product.getId(),
                product.getName(),
                product.getPrice(),
                1));
    }

    public Set<CartItem> removeFromCart(int id) {

        Set<CartItem> cartItems = getCartItems()
                .stream()
                .filter(cartItem -> cartItem.getId() != id)
                .collect(Collectors.toSet());

        cart.setCartItems(cartItems);
        return cartItems;
    }

    public void clearCart() {
        cart.clearCart();
    }

    public void saveCustomer(Customer customer) {

            customerDao.save(customer);


    }


    public List<Customer> listCustomers() {

            return customerDao.findAll();
        }


    }

