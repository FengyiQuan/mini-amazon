package org.mini_amazon.models;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.CascadeType;

@Entity
@Table(name = "items")
public class Item {
  @Id
  private long id;
  private String description;
  // this may not in use
  private double unitPrice;
  private String imgPath;
//  @ManyToMany(mappedBy = "items", cascade = {CascadeType.PERSIST})
  @OneToMany(mappedBy = "item", cascade = {CascadeType.ALL})
  private Set<Order> orders;
  @ManyToMany(cascade = {CascadeType.ALL})
  private Set<Category> categories;
      @OneToOne(cascade = {CascadeType.ALL})
  private User seller;
}