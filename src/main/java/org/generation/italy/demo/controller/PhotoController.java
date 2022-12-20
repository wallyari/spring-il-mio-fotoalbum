package org.generation.italy.demo.controller;

import java.util.List;
import java.util.Optional;
import org.generation.italy.demo.pojo.Category;
import org.generation.italy.demo.pojo.Photo;
import org.generation.italy.demo.service.CategoryService;
import org.generation.italy.demo.service.PhotoService;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;


 @Controller
 @RequestMapping("/photo")
 public class PhotoController {
		
	 @Autowired
	 private PhotoService photoService;
	 
	 @Autowired
	private CategoryService categoryService;


 	@GetMapping("")
 	public String index(Model model, @RequestParam(name = "query", required = false) String query) {
 		
 		List<Photo> photos = query == null 
 				? photoService.findAll() 
 				: photoService.findByTitleOrTag(query);
 		
 		model.addAttribute("photos", photos);
 		return "photo-index";
 	}
 	
 	@GetMapping("/show/{id}")
 	public String show(@PathVariable("id") int id, Model model) {
 		Optional<Photo> optP = photoService.findPhotoById(id);
 		if (optP.isEmpty()) {
 			return "photo-index";
 		}

 		Photo photo = optP.get();
 		List<Category> categories = photo.getCategories();
 		model.addAttribute("photo", photo);
 		model.addAttribute("categories", categories);
 		return "photo-show";
 	}
 	

 	@GetMapping("/create")
 	public String create(Model model) {
 		List<Category> categories = categoryService.findAll();

 		Photo p = new Photo();
 		model.addAttribute("photo", p);
 		model.addAttribute("categories", categories);
 		return "photo-create";
 	}

 	@PostMapping("/create")
 	public String store(@Valid @ModelAttribute("photo") Photo photo, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

 		if(bindingResult.hasErrors()) {
 			redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
 			return "redirect:/photo/create";
 		}
 		photoService.save(photo);
 		return "redirect:/photo";
 	}
 	@GetMapping("/edit/{id}")
 	public String edit(@PathVariable("id") int id, Model model) {
 		Photo photo = photoService.findPhotoById(id).get();

 		List<Category> categories = categoryService.findAll();

 		model.addAttribute("categories", categories);
 		model.addAttribute("photo", photo);
 		return "photo-edit";
 	}

 	@PostMapping("/edit")
 	public String update(@Valid @ModelAttribute("photo") Photo photo, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

 		if(bindingResult.hasErrors()) {
 			redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
 			return "redirect:/photo/edit/" + photo.getId();
 		}

 		photoService.save(photo);
 		return "redirect:/photo";
 	}
 	
 	@GetMapping("/delete/{id}")
	public String deletePhoto(@PathVariable("id") int id) {
		photoService.deletePhotoById(id);
		return "redirect:/photo";
	}
 }
