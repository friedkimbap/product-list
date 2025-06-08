package kr.ac.hansung.cse.hellospringdatajpa.controller;

import kr.ac.hansung.cse.hellospringdatajpa.entity.Product;
import kr.ac.hansung.cse.hellospringdatajpa.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService service;

    @GetMapping({"", "/"})
    public String viewHomePage(
        Model model,
        @RequestParam(value = "success", required = false) String success
    ) {
        if (success != null) {
            model.addAttribute("param.success", true);
        }

        List<Product> listProducts = service.listAll();
        model.addAttribute("listProducts", listProducts);

        return "index";
    }

    @GetMapping("/new")
    public String showNewProductPage(Model model) {
        Product product = new Product();
        model.addAttribute("product", product);

        model.addAttribute("checkProductName", false);
        model.addAttribute("checkBrand", false);
        model.addAttribute("checkMadeIn", false);
        model.addAttribute("checkPrice", false);
        model.addAttribute("isExistedProduct", false);

        return "new_product";
    }

    @GetMapping("/edit/{id}")
    public String showEditProductPage(
        @PathVariable(name = "id") Long id,
        Model model) {

        Product product = service.get(id);
        model.addAttribute("product", product);

        model.addAttribute("checkProductName", false);
        model.addAttribute("checkBrand", false);
        model.addAttribute("checkMadeIn", false);
        model.addAttribute("checkPrice", false);

        return "edit_product";
    }

    @PostMapping("/save")
    public String saveProduct(@ModelAttribute("product") Product product, Model model) {
        boolean success = true;

        if(!service.checkProductName(product)){
            success = false;
            model.addAttribute("checkProductName", true);
        }
        if(!service.checkBrand(product)){
            success = false;
            model.addAttribute("checkBrand", true);
        }
        if(!service.checkMadeIn(product)){
            success = false;
            model.addAttribute("checkMadeIn", true);
        }
        if(service.checkPrice(product)){
            success = false;
            model.addAttribute("checkPrice", true);
        }
        if(!service.isExistedProduct(product)){
            success = false;
            model.addAttribute("isExistedProduct", true);
        }

        if(success){
            service.save(product);
            return "redirect:/products";
        } else {
            model.addAttribute("product", product);
            if(product.getId() == null){
                return "new_product";
            } else {
                return "edit_product";
            }
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable(name = "id") Long id) {
        service.delete(id);
        return "redirect:/products";
    }
}