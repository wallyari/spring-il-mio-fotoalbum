package org.generation.italy.demo;

import java.util.ArrayList;

import java.util.List;


import org.generation.italy.demo.pojo.Category;
import org.generation.italy.demo.pojo.Photo;
import org.generation.italy.demo.pojo.Role;
import org.generation.italy.demo.pojo.User;
import org.generation.italy.demo.service.RoleService;
import org.generation.italy.demo.service.UserService;
import org.generation.italy.demo.service.CategoryService;
import org.generation.italy.demo.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

 @SpringBootApplication

 public class SpringIlMioFotoalbumApplication implements CommandLineRunner{

 	@Autowired
 	private PhotoService photoService;
 	
 	@Autowired
 	private CategoryService categoryService;
 	
 	@Autowired
	private RoleService roleService;
	
	@Autowired
	private UserService userService;
	
 	public static void main(String[] args) {
 		SpringApplication.run(SpringIlMioFotoalbumApplication.class, args);
 	}

 	@Override
 	public void run(String... args) throws Exception {
 		
 		
		Role adminRole = new Role("ADMIN");
		roleService.save(adminRole);

		User adminUser = new User("admin", "{noop}adminp", adminRole);
		userService.save(adminUser);

 		

 		
 		//-----------------------Category--------------------//
 		Category category1 = new Category("Universal Music");
 		Category category2 = new Category("Sony Music");
 		Category category3 = new Category("Virgin Records");
 		Category category4 = new Category("Rca Records ");
 		Category category5 = new Category("Capitol Records ");

 		List<Category> categoryList1 = new ArrayList<>();
 		categoryList1.add(category1);
 		categoryList1.add(category3);
 		
 		List<Category> categoryList2 = new ArrayList<>();
 		categoryList2.add(category4);
 		categoryList2.add(category5);

 		categoryService.save(category1);
 		categoryService.save(category2);
 		categoryService.save(category3);
 		categoryService.save(category4);
 		categoryService.save(category5);
 		
 		
 		//-----------------------Photo----------------------//
 		Photo photo1 = new Photo("Pink Floyd", "Band formatosi a Londra nel 1965", "https://www.virginradio.it/resizer/-1/-1/true/pink_floyd_5-1635241371888.png--.png?1635241371980", "rock", true, categoryList1);
 		Photo photo2 = new Photo("The Who", "Band formatosi a Londra nel 1964", "https://www.rollingstone.com/wp-content/uploads/2022/06/the-who-best-songs-1800.jpg?w=1024", "pop-rock", false,categoryList1);
 		Photo photo3 = new Photo("Metallica", "Band formatosi a Los Angeles nel 1981", "https://www.sentireascoltare.com/wp-content/uploads/2012/11/metallica-press-2019-e1594132959152.jpg", "hard-rock", true,categoryList1);
 		Photo photo4 = new Photo("Thw Beatles", "Band formatosi a Liverpool nel 1960", "https://www.rollingstone.it/wp-content/uploads/2017/09/Abbey-Road-beatles.jpg", "pop-rock", false,categoryList1);
 		Photo photo5 = new Photo("CCCP", "Band formatosi a Berlino nel 1981", "https://www.pangea.news/pan/wp-content/uploads/2022/07/E3yQQ40WYAIJVIC-e1656824699888.jpg", "punk-rock", false,categoryList1);

 		photoService.save(photo1);
 		photoService.save(photo2);
 		photoService.save(photo3);
 		photoService.save(photo4);
 		photoService.save(photo5);

 		//System.out.println(photoService.findAllWCategory());
 		
 		
 		
 		
 		
 		
 		
 		
 		
 		
 		
 		
 		
 		
 		
 	}

 }