package com.example.productsapi.product.infrastructure.database.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "tbl_products")
@Getter @Setter
public final class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name="name", nullable=false)
    private String name;
    @Column(name="description", nullable=false)
    private String description;
    @Column(name="stock", nullable=false)
    private Long stock;
    @Column(name="basePrice", nullable=false)
    private Double basePrice;
    @Column(name="costPrice", nullable=false)
    private Double costPrice;

}
