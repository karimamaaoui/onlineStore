package com.tekup.onlinestore.web.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tekup.onlinestore.web.models.Product;
import com.tekup.onlinestore.web.models.requests.ProductForm;

import jakarta.validation.Valid;
@RequestMapping("/products")
@Controller
public class ProductController {
	// pour avoir partage cette liste a toute instance qui utilise ce controller

	private static List<Product> products = new ArrayList<>();
	private static Long idCount = 0L;
	//houni static pour avoir creer just une fois
	
	static {
	products.add(new Product(++idCount, "SS-S9", "Samsung Galaxy S9", 500D, 50, "samsung-s9.png"));
	products.add(new Product(++idCount, "NK-5P", "Nokia 5.1 Plus", 60D, 60, null));
	products.add(new Product(++idCount, "IP-7", "iPhone 7", 600D, 30, "iphone-7.png"));
	}
	
	
    @RequestMapping("/")
    public String getAllProduct(Model model){
        model.addAttribute("products",products);
        return "list";
    
    }
    @RequestMapping("")
    public String listOfProducts(Model model){
        model.addAttribute("products",products);
        return "list";
    
    }
    
  /*  @GetMapping("/create")
    public String showCreateProductForm(Model model) {
        model.addAttribute("productForm", new ProductForm());
        return "create"; 
    }
    
    @PostMapping("/create")
    public String createProduct(
             @ModelAttribute("productForm") ProductForm productForm,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "create";
        }

        Product newProduct = new Product();
        newProduct.setName(productForm.getName());
        newProduct.setCode(productForm.getCode());
        newProduct.setPrice(productForm.getPrice());
        newProduct.setQuantity(productForm.getQuantity());
        newProduct.setImage(productForm.getImage());

        products.add(newProduct);
        redirectAttributes.addFlashAttribute("successMessage", "Product created successfully");
        return "redirect:/products"; 
    }
*/
    @GetMapping("/create")
    public String showAddProduct(Model model){
        model.addAttribute("productForm", new ProductForm());
        return "create";
    }

    @PostMapping("/create")
    public String addProuct(@Valid @ModelAttribute("productForm") ProductForm productForm, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return "create";
        }
        products.add(new Product(++idCount, productForm.getCode(), productForm.getName(), productForm.getPrice(), productForm.getQuantity(), null));
        
        return "redirect:/products";
    }

    @PostMapping("/{id}/delete")
    public String deleteProduct(@PathVariable("id")Long id){
         Product productRemove=this.findProductById(id);
         if(productRemove!=null){
            this.products.remove(productRemove);
         }
        return "redirect:/products";
    }

    @GetMapping("/{id}/edit")
    public String showEditProduct(@PathVariable("id")Long id, Model model){
        Product productEdit=this.findProductById(id);
        if(productEdit!=null){
           model.addAttribute("productForm", new ProductForm(productEdit.getCode(),productEdit.getName(),productEdit.getPrice(),productEdit.getQuantity(),productEdit.getImage()));
           model.addAttribute("idP", id); 
       }
       return "edit";
   }
    
   @PostMapping("/{id}/edit")
   public String editProduct(@PathVariable("id")String id,@Valid @ModelAttribute("productForm")ProductForm productForm, BindingResult bindingResult){
       if(bindingResult.hasErrors()) {
           return "edit";
       }
     
           for (Product p: products){
               if(p.getId()==Integer.parseInt(id)){
                 p.setCode(productForm.getCode());  
                 p.setName(productForm.getName());
                 p.setPrice(productForm.getPrice());
                 p.setQuantity(productForm.getQuantity());
                 p.setImage(productForm.getImage());
               }  
       }
       return "redirect:/products";
   }

   private Product findProductById(Long id){
       for(Product product: products){
           if(product.getId().equals(id)) return product;
       }
       return null;
   }

}
