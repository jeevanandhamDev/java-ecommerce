package com.ecommerce.ecom.controller;

import com.ecommerce.ecom.model.Category;
import com.ecommerce.ecom.payload.CategoryDto;
import com.ecommerce.ecom.payload.CategoryResponse;
import com.ecommerce.ecom.service.CategoryService;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CategoryController {

    @Autowired
    private CategoryService categoryService;


    @GetMapping("api/public/catgories")
    public ResponseEntity<CategoryResponse> showALlCategories(){
        CategoryResponse categories = categoryService.showALlCategories();
        return ResponseEntity.ok(categories);
    }

    @PostMapping("api/public/catgories")
    public ResponseEntity<CategoryDto> addCategory(@Valid  @RequestBody CategoryDto categoryDto){
        CategoryDto savedCategoryDto =categoryService.addCategory(categoryDto);
        return new ResponseEntity<>(savedCategoryDto, HttpStatus.CREATED);
    }

    @DeleteMapping("api/admin/catgories/{categoryId}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long categoryId){
         try{
             String status = categoryService.deleteCategory(categoryId);
             return new ResponseEntity<>(status, HttpStatus.OK);
         }
         catch (ResponseStatusException e){
             return new ResponseEntity<>(e.getReason(),e.getStatusCode());
         }
    }

    @PutMapping("api/admin/catgories/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable long categoryId,
                                                 @RequestBody CategoryDto categoryDto){
        CategoryDto existingCategory= categoryService.updateCategory(categoryId, categoryDto);
        return new ResponseEntity<>(existingCategory,HttpStatus.ACCEPTED);

    }

    @GetMapping("api/echo")
    public ResponseEntity<String> getmessage(@RequestParam (name = "message") String greetings){
        return new ResponseEntity<>(greetings, HttpStatus.OK);
    }
}
