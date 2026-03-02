package com.example.demo.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    private int id;

    @NotBlank(message = "Ten danh muc khong duoc trong!")
    private String name;
}
