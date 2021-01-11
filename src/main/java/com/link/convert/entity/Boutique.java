package com.link.convert.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "BOUTIQUE")
@Data
public class Boutique {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "BOUTIQUE_ID")
    private int boutiqueId;

    @Column(name = "BOUTIQUE_NAME")
    private String boutiqueName;

    @ManyToMany(mappedBy = "boutiques")
    private List<Product> products=new ArrayList<>();


}
