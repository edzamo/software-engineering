package com.solid.principle.lsp.example2.good;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@ToString
public class Bird {

    private String name;
    private int age;
    private String color;

   
}
