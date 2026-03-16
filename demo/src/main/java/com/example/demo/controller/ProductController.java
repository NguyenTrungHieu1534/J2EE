package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.Category;
import com.example.demo.model.Product;
import com.example.demo.service.CategoryService;
import com.example.demo.service.ProductService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    // ── List ──────────────────────────────────────────────────────────────
    @GetMapping()
    public String index(Model model) {
        model.addAttribute("listproduct", productService.getAll());
        return "product/list";
    }

    // ── Add (form) ────────────────────────────────────────────────────────
    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryService.getAll());
        return "product/add";
    }

    // ── Add (save) ────────────────────────────────────────────────────────
    @PostMapping("/add")
    public String save(
            @Valid @ModelAttribute("product") Product newProduct,
            BindingResult result,
            @RequestParam("category-id") Long categoryId,
            @RequestParam("imageProduct") MultipartFile imageProduct,
            Model model) {

        if (result.hasErrors()) {
            model.addAttribute("categories", categoryService.getAll());
            return "product/add";
        }

        if (imageProduct != null && !imageProduct.isEmpty()) {
            productService.updateImage(newProduct, imageProduct);
        }

        Category selectedCategory = categoryService.get(categoryId);
        newProduct.setCategory(selectedCategory);

        productService.add(newProduct);
        return "redirect:/products";
    }

    // ── Edit (form) ───────────────────────────────────────────────────────
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        Product find = productService.get(id);
        if (find == null) {
            return "redirect:/products";
        }
        model.addAttribute("product", find);
        model.addAttribute("categories", categoryService.getAll());
        return "edit";
    }

    // ── Edit (save) ── ĐÃ FIX LỖI KHÔNG UPDATE ──────────────────────────
    @PostMapping("/edit")
    public String edit(
            @Valid @ModelAttribute("product") Product editProduct,
            BindingResult result,
            @RequestParam(value = "category-id", required = false) Long categoryId,
            @RequestParam(value = "imageProduct", required = false) MultipartFile imageProduct,
            Model model) {

        // 1. In lỗi ra Console nếu có
        if (result.hasErrors()) {
            System.out.println(">>> LỖI VALIDATION KHI EDIT:");
            result.getAllErrors().forEach(err -> System.out.println("- " + err.getDefaultMessage()));

            model.addAttribute("categories", categoryService.getAll());
            return "edit";
        }

        // 2. Xử lý Category
        if (categoryId != null) {
            Category selectedCategory = categoryService.get(categoryId);
            editProduct.setCategory(selectedCategory);
        }

        // 3. Xử lý Image: Nếu không chọn ảnh mới, phải lấy lại tên ảnh cũ
        if (imageProduct != null && !imageProduct.isEmpty()) {
            productService.updateImage(editProduct, imageProduct);
        } else {
            // Lấy lại ảnh cũ từ database dựa trên ID gửi từ form lên
            Product existingProduct = productService.get(editProduct.getId());
            if (existingProduct != null) {
                editProduct.setImage(existingProduct.getImage());
            }
        }

        // 4. Thực hiện Update
        System.out.println(">>> ĐANG THỰC HIỆN UPDATE CHO ID: " + editProduct.getId());
        productService.update(editProduct);

        return "redirect:/products";
    }

    // ── Delete ────────────────────────────────────────────────────────────
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        productService.delete(id);
        return "redirect:/products";
    }

    @GetMapping("/create")
    public String createRedirect() {
        return "redirect:/products/add";
    }
}
