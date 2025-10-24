package demo.local.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import demo.local.demo.model.Product;
import demo.local.demo.service.ProductService;

@Controller
public class ProductController {
    
    @Autowired
    private ProductService productService;
    
    // Trang chủ
    @GetMapping("/")
    public String home(Model model) {
        List<Product> featuredProducts = productService.getAllProducts().subList(0, 6);
        model.addAttribute("featuredProducts", featuredProducts);
        model.addAttribute("categories", productService.getAllCategories());
        return "index";
    }
    
    // Trang sản phẩm
    @GetMapping("/san-pham")
    public String products(@RequestParam(required = false) String category, Model model) {
        List<Product> products;
        String categoryName;
        
        if (category != null && !category.isEmpty()) {
            products = productService.getProductsByCategory(category);
            categoryName = category;
        } else {
            products = productService.getAllProducts();
            categoryName = "Tất cả sản phẩm";
        }
        
        model.addAttribute("products", products);
        model.addAttribute("categoryName", categoryName);
        model.addAttribute("categories", productService.getAllCategories());
        return "san-pham";
    }
    
    // Chi tiết sản phẩm
    @GetMapping("/chi-tiet-san-pham")
    public String productDetail(@RequestParam Long id, Model model) {
        Product product = productService.getProductById(id);
        if (product == null) {
            return "redirect:/san-pham";
        }
        model.addAttribute("product", product);
        return "chi-tiet-san-pham";
    }
    
    // API lấy sản phẩm (cho AJAX)
    @GetMapping("/api/products")
    @ResponseBody
    public List<Product> getProductsApi(@RequestParam(required = false) String category) {
        if (category != null && !category.isEmpty()) {
            return productService.getProductsByCategory(category);
        }
        return productService.getAllProducts();
    }
}