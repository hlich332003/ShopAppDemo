package demo.local.demo.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.local.demo.model.Product;
import demo.local.demo.repository.ProductRepository;
import jakarta.annotation.PostConstruct;

@Service
public class ProductService {
    
    @Autowired
    private ProductRepository productRepository;
    
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
    
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategory(category);
    }
    
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }
    
    public List<String> getAllCategories() {
        return Arrays.asList("Màn Hình", "Bàn Phím", "Chuột");
    }
    
    // Khởi tạo dữ liệu mẫu
    @PostConstruct
    public void initSampleData() {
        if (productRepository.count() == 0) {
            // Màn hình
            productRepository.save(new Product("MSI G274QPF", 6990000.0, 15, 
                "/images/123.jpg", "Màn Hình"));
            productRepository.save(new Product("LG 27GN800-B", 7500000.0, 8, 
                "/images/OIP.webp", "Màn Hình"));
            productRepository.save(new Product("ASUS TUF Gaming", 8200000.0, 12, 
                "/images/OIP.webp", "Màn Hình"));
            productRepository.save(new Product("Dell UltraSharp", 9500000.0, 6, 
                "/images/345.jpg", "Màn Hình"));
                
            // Bàn phím
            productRepository.save(new Product("Akko 3068B Plus", 1290000.0, 20, 
                "/images/123.jpg", "Bàn Phím"));
            productRepository.save(new Product("Keychron K8 Pro", 1650000.0, 15, 
                "/images/blue.webp", "Bàn Phím"));
            productRepository.save(new Product("Razer BlackWidow", 2800000.0, 10, 
                "/images/345.jpg", "Bàn Phím"));
            productRepository.save(new Product("Logitech MX Keys", 2200000.0, 18, 
                "/images/blue.webp", "Bàn Phím"));
                
            System.out.println("✅ Sample data initialized with " + productRepository.count() + " products");
        }
    }
}
