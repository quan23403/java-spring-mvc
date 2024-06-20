package vn.hoidanit.laptopshop.controller.admin;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.service.ProductService;
import vn.hoidanit.laptopshop.service.UpLoadService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;

@Controller
public class ProductController {

    private final ProductService productService;
    private final UpLoadService upLoadService;

    public ProductController(ProductService productService, UpLoadService upLoadService) {
        this.productService = productService;
        this.upLoadService = upLoadService;
    }

    @GetMapping("/admin/product")
    public String getProductPage(Model model) {
        List<Product> products = this.productService.handleGetAllProduct();
        model.addAttribute("products", products);
        return "admin/product/show";
    }

    @GetMapping("/admin/product/create")
    public String getCreateProductPage(Model model) {
        model.addAttribute("newProduct", new Product());
        return "admin/product/create";
    }

    @PostMapping("/admin/product/create")
    public String handleCreateProduct(
            @ModelAttribute("newProduct") @Valid Product newProduct,
            BindingResult newProductBindingResult,
            @RequestParam("hoidanitFile") MultipartFile file) {
        // TODO: process POST request
        if (newProductBindingResult.hasErrors()) {
            return "admin/product/create";
        }
        String image = this.upLoadService.handleSaveUpLoadFile(file, "product");
        newProduct.setImage(image);
        // upload image

        this.productService.handleSaveProduct(newProduct);
        return "redirect:/admin/product";
    }

    @GetMapping("/admin/product/{id}")
    public String getProductDetail(Model model, @PathVariable long id) {
        Product product = this.productService.handleGetProductById(id);
        model.addAttribute("id", id);
        model.addAttribute("product", product);
        return "/admin/product/detail";
    }

    @GetMapping("/admin/product/update/{id}")
    public String getProductUpdatePage(Model model, @PathVariable long id) {
        Product product = this.productService.handleGetProductById(id);
        model.addAttribute("product", product);
        System.out.println(product.getId());

        return "/admin/product/update";
    }

    @PostMapping("/admin/product/update")
    public String postUpdateProduct(@ModelAttribute("product") @Valid Product product,
            BindingResult productBindingResult,
            @RequestParam("hoidanitFile") MultipartFile file) {
        // TODO: process POST request
        System.out.println(product.getId());
        if (productBindingResult.hasErrors()) {
            return "admin/product/update";
        }
        Product currentProduct = this.productService.handleGetProductById(product.getId());
        if (currentProduct != null) {
            // System.out.println("hello ");
            if (!file.isEmpty()) {
                String img = this.upLoadService.handleSaveUpLoadFile(file, "product");
                currentProduct.setImage(img);
            }
            currentProduct.setName(product.getName());
            currentProduct.setPrice(product.getPrice());
            currentProduct.setQuantity(product.getQuantity());
            currentProduct.setDetailDesc(product.getDetailDesc());
            currentProduct.setShortDesc(product.getShortDesc());
            currentProduct.setFactory(product.getFactory());
            currentProduct.setTarget(product.getTarget());

            this.productService.handleSaveProduct(currentProduct);
        }
        return "redirect:/admin/product";
    }

    @GetMapping("/admin/product/delete/{id}")
    public String getProductDeletePage(Model model, @PathVariable long id) {
        Product product = this.productService.handleGetProductById(id);
        model.addAttribute("id", id);
        model.addAttribute("product", product);
        return "/admin/product/delete";
    }

    @PostMapping("/admin/product/delete")
    public String postDeleteProduct(Model model, @ModelAttribute("product") Product product) {
        // TODO: process POST request
        this.productService.handleDeleteById(product.getId());
        System.out.println(product.getId());
        return "redirect:/admin/product";
    }

}
