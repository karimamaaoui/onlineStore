package com.tekup.onlinestore.web.models.requests;


import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductForm {

    //private Long id;
   
    @NotBlank(message = "Code is requried")
    private String code; 
   
    @Size(min=2, max=30)
    private String name;

    @DecimalMin(value = "0.1")
    private Double price;

    @Min(0)
    private int quantity;
    
    private String image; 
}