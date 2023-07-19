package com.example.sampleaffablebean.controller;

import com.example.sampleaffablebean.ds.Cart;
import com.example.sampleaffablebean.ds.CartItem;
import com.example.sampleaffablebean.entity.Customer;
import com.example.sampleaffablebean.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class ProductController {
    private final ProductService productService;


    public ProductController(ProductService productService) {
        this.productService = productService;

    }

    @GetMapping({"/home", "/"})
    public String home() {
        return "home";

    }


    @GetMapping("/add-cart")
    public String addToCart(@RequestParam("pid") int pid) {
        productService.addToCart(pid);
        int cid=productService.getCategoryIdByProductId(pid);
        return "redirect:/products?cid="+cid;

    }

    @ModelAttribute("cartSize")
    public int cartSize() {
        return productService.cartSize();
    }


    @GetMapping("/products")
    public String listProductsByCategory(@RequestParam("cid") int cid, Model model) {
        model.addAttribute("products", productService.listProducts(cid));
        return "products";
    }

    @GetMapping("/checkout")
    public String Customer(Model model){
        model.addAttribute("customer",new Customer());
        return "checkout";
    }
    @PostMapping("/checkout")
    public String saveCustomer(@Valid Customer customer, BindingResult result){
        if(result.hasErrors()){
            return "checkout";
        }
        productService.saveCustomer(customer);
        return "redirect:/confirm";
    }
    @GetMapping("/confirm")
    public String listAuthor(Model model){
        model.addAttribute("cartItems",productService.getCartItems());
        model.addAttribute("customers",productService.listCustomers());

        return "confirm";
    }



    @GetMapping("/check1")
    public String checkOutV1(Model model) {
        Set<CartItem> cartItems = productService.getCartItems()
                .stream().map(
                        item -> {
                            item.setRender(true);
                            return item;
                        })
                .collect(Collectors.toSet());
        model.addAttribute("cartItem", new CartItem());
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("changeButton", true);
        return "cart";
    }

    @ModelAttribute("totalPrice")
    public double totalPrice() {
        return productService.getCartItems()
                .stream()
                .map(item -> item.getPrice() * item.getQuantity())
                .mapToDouble(i -> i)
                .sum();
    }
    @ModelAttribute("total")
    public double total() {
        return productService.getCartItems()
                .stream()
                .map(item -> item.getPrice()*item.getQuantity())
                .mapToDouble(i -> i)
                .sum()+3.00;
    }

    @PostMapping("/check2")
    public String check(CartItem cartItem, Model model) {
        model.addAttribute("cartItems", productService.getCartItems());
        int i = 0;
        for (CartItem cartItem1 : productService.getCartItems()) {
            cartItem1.setQuantity(cartItem.getQuantityLinkedList().get(i));
            cartItem1.setRender(false);
            i++;
        }
        productService.getCartItems().forEach(System.out::println);
        return "redirect:/shopping";
    }


    @GetMapping("/shopping")
    public String viewCart(Model model) {
        model.addAttribute("cartItems", new CartItem());
        model.addAttribute("changeButton", false);
        model.addAttribute("cartItems", productService.getCartItems());
        return "cart";
    }

    @GetMapping("/clear-cart")
    public String clearCart() {
        productService.clearCart();
        return "redirect:/shopping";
    }
}
