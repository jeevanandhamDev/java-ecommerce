package com.ecommerce.ecom.model;

import jakarta.persistence.*;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.UniqueElements;

@Entity (name="categories")
@Getter
@Setter

public class Category {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private Long categoryId;

    @NotBlank(message = "Name cannot be empty")
    @Size (min=5, message = "atleast 5 character")
    @Size(max = 20, message = "mximum 20 characters are allowed")
    private String categoryName;

    public Category(Long categoryId, String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }


    public Category() {
    }


}
