package vn.hoidanit.laptopshop.service;

import java.util.List;
import java.util.Optional;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

import vn.hoidanit.laptopshop.domain.Cart;
import vn.hoidanit.laptopshop.domain.CartDetail;
import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.repository.CartDetailRepository;
import vn.hoidanit.laptopshop.repository.CartRepository;
import vn.hoidanit.laptopshop.repository.ProductRepository;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final CartDetailRepository cartDetailRepository;
    private final UserService userService;

    public ProductService(ProductRepository productRepository, CartRepository cartRepository,
                          CartDetailRepository cartDetailRepository, UserService userService) {
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
        this.cartDetailRepository = cartDetailRepository;
        this.userService = userService;
    }

    public Product handleSaveProduct(Product product) {
        return this.productRepository.save(product);
    }

    public List<Product> handleGetAllProduct() {
        return this.productRepository.findAll();
    }

    public Product handleGetProductById(long id) {
        return this.productRepository.findById(id);
    }

    public void handleDeleteById(long id) {
        this.productRepository.deleteById(id);
    }

    public void handleAddProductToCart(String email, long productId, HttpSession session) {
        User user = this.userService.getUserByEmail(email);
        if(user != null) {
            Cart cart = this.cartRepository.findByUser(user);
            if(cart == null) {
                Cart otherCart = new Cart();
                otherCart.setUser(user);
                otherCart.setSum(0);
                cart = this.cartRepository.save(otherCart);
            }
            // save cart_detail
            // tim product by id
            Product product = this.productRepository.findById(productId);
            if(product != null) {
                // check san pham da tung duoc them vao gio hang chua
                CartDetail oldCartDetail = this.cartDetailRepository.findByCartAndProduct(cart, product);
                if(oldCartDetail == null) {
                    CartDetail cd = new CartDetail();
                    cd.setCart(cart);
                    cd.setProduct(product);
                    cd.setPrice(product.getPrice());
                    cd.setQuantity(1);
                    this.cartDetailRepository.save(cd);
                    // update cart
                    int s = cart.getSum() + 1;
                    cart.setSum(cart.getSum() + 1);
                    this.cartRepository.save(cart);
                    session.setAttribute("sum", s);
                }
                else {
                    oldCartDetail.setQuantity(oldCartDetail.getQuantity() + 1);
                    this.cartDetailRepository.save(oldCartDetail);
                }
            }

        }
    }
}
