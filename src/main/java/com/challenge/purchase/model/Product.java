package com.challenge.purchase.model;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Product {
	
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String name;
	private String description;
	@OneToOne
    @JoinTable(name = "product_category", 
      joinColumns = 
        { @JoinColumn(name = "product_id", referencedColumnName = "id") },
      inverseJoinColumns = 
        { @JoinColumn(name = "category_id", referencedColumnName = "id") })
	private Category category;
	@OneToOne(mappedBy = "product")
	private Sale sale;
	@ManyToOne()
    @JoinColumn(name = "seller_id", referencedColumnName = "id")
	private User seller;
	private LocalDateTime dateCreation = LocalDateTime.now();
	
	public Product() {
		
	}
	
	public Product(String name, String description, Category category, User seller) {
		super();
		this.name = name;
		this.description = description;
		this.category = category;
		this.seller = seller;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	
	public User getSeller() {
		return seller;
	}
	public void setSeller(User seller) {
		this.seller = seller;
	}
	
	public LocalDateTime getDateCreation() {
		return dateCreation;
	}
	public void setDateCreation(LocalDateTime dateCreation) {
		this.dateCreation = dateCreation;
	}
	public Sale getSale() {
		return sale;
	}
	public void setSale(Sale sale) {
		this.sale = sale;
	}

}