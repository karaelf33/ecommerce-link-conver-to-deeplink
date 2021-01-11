package com.link.convert.entity;


import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "MERCHANT")
@Data
public class Merchant {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "MERCHANT_ID")
    private int merchantId;

    @Column(name = "MERCHANT_NAME")
    private String merchantName;

    @ManyToMany(mappedBy = "merchants")
    private List<Product> products=new ArrayList<>();

}
