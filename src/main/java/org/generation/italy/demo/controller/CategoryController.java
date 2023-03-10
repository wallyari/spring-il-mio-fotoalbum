package org.generation.italy.demo.controller;

import java.util.List;
//import java.util.Optional;
import org.generation.italy.demo.pojo.Category;
import org.generation.italy.demo.pojo.Photo;
import org.generation.italy.demo.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;

 @Controller
 @RequestMapping("/category")
 public class CategoryController {

 	@Autowired
 	private CategoryService categoryService;

 	@GetMapping("")
 	public String index(Model model) {
 		List<Category> categories = categoryService.findAll();
 		model.addAttribute("categories", categories);
 		return "category-index";
 	}
 	@GetMapping("/create")
 	public String create(Model model) {
 		Category c = new Category();
 		model.addAttribute("category", c);
 		return "category-create";
 	}

 	@PostMapping("/create")
 	public String store(@Valid @ModelAttribute("category") Category category, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

 		if(bindingResult.hasErrors()) {
 			redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
 			return "redirect:/category/create";
 		}
 		categoryService.save(category);
 		return "redirect:/category";
 	}
 	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") int id, Model model) {
		
		Category c = categoryService.findCategoryById(id).get();
		model.addAttribute("category", c);
		return "category-edit";
	}
 	
 	@GetMapping("/delete/{id}")
 	public String deleteCategory(@PathVariable("id") int id) {
		
 		Category c = categoryService.findCategoryById(id).get();
 		for (Photo photo : c.getPhotos())
 			photo.removeCategory(c);

 		categoryService.deleteCategoryById(id);
 		return "redirect:/category";
 	}

 }