package com.ecommerce.ecom.serviceImpl;


import com.ecommerce.ecom.exceptions.APIException;
import com.ecommerce.ecom.exceptions.ResourceNotFoundException;
import com.ecommerce.ecom.model.Category;
import com.ecommerce.ecom.payload.CategoryDto;
import com.ecommerce.ecom.payload.CategoryResponse;
import com.ecommerce.ecom.repository.CategoryRepository;
import com.ecommerce.ecom.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    private List<Category> categories = new ArrayList<>();

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public CategoryResponse showALlCategories(Integer pageNumber, Integer pageSize) {
        Pageable pageDetails = PageRequest.of(pageNumber, pageSize);
        Page<Category> categoryPage = categoryRepository.findAll(pageDetails);

        List<Category> categories =  categoryPage.getContent();
        if (categories.isEmpty()){
            throw new APIException("There is no category!");
        }

        List<CategoryDto> categoryDtos = categories.stream()
                .map((category)->modelMapper.map(category, CategoryDto.class)).toList();

        CategoryResponse categoryResponse = new CategoryResponse();

        categoryResponse.setContent(categoryDtos);
        categoryResponse.setPageSize(categoryPage.getSize   ());
        categoryResponse.setPageNumber(categoryPage.getNumber());
        categoryResponse.setTotalElements(categoryPage.getTotalElements());
        categoryResponse.setTotalPages(categoryPage.getTotalPages());
        categoryResponse.setFirstPage(categoryPage.isFirst());
        categoryResponse.setLastPage(categoryPage.isLast());
        return categoryResponse;
    }


    @Override
    public CategoryDto addCategory(CategoryDto categoryDto) {
        //API Exception for already existed category
        Category existingCategory = categoryRepository.findByCategoryName(categoryDto.getCategoryName());
        if(existingCategory!=null){
            throw new APIException("category of "+ categoryDto.getCategoryName()+ " already existed !!!");
        }

        Category category = modelMapper.map(categoryDto, Category.class);
        Category savedCategory = categoryRepository.save(category);
        return modelMapper.map(savedCategory, CategoryDto.class);
    }

    @Override
    public String deleteCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"category not found: "+id));

        categoryRepository.delete(category);
        return "deleted the id: "+id;
    }


    //uses the custom exception resource not found but in delete not used this exception
    @Override
    public CategoryDto updateCategory(long categoryId, CategoryDto categoryDto) {
        Category existingCategory = categoryRepository.findById(categoryId)
                .orElseThrow(()-> new ResourceNotFoundException("Category", "CategoryId", categoryId));

        //API Exception for already existed category
        Category existingCategoryNotAllowed = categoryRepository.findByCategoryName(categoryDto.getCategoryName());
        if(existingCategoryNotAllowed!=null){
            throw new APIException("category of "+ categoryDto.getCategoryName()+ " already existed !!!");
        }

        Category category = modelMapper.map(categoryDto, Category.class);
        existingCategory.setCategoryName(category.getCategoryName());
        Category updatedCategory = categoryRepository.save(existingCategory);
        return modelMapper.map(updatedCategory, CategoryDto.class);
    }


}
