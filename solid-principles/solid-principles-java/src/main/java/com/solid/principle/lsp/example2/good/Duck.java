package com.solid.principle.lsp.example2.good;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
public class Duck extends Bird implements Flying{



    @Override
    public void fly() {
        System.out.println("A duck is flying!");
    }

}
