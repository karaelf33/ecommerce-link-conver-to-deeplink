package com.link.convert.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "PRODUCT")
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "PRODUCT_ID")
    private int productId;

    @Column(name = "NAME")
    private String name;

    @Column(name = "CONTENT_ID")
    private String contentId;

    @ManyToOne
    private Brand brand;

    @ManyToMany
    private List<Category> categories = new ArrayList<>();

    @ManyToMany
    private List<Merchant> merchants = new ArrayList<>();

    @ManyToMany
    private List<Boutique> boutiques = new ArrayList<>();


}
