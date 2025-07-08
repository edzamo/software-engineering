package com.ezamora.coffeeshop.infrastructure.adapter.in.mapper;

import com.ezamora.coffeeshop.domain.enums.Drink;
import com.ezamora.coffeeshop.domain.enums.Milk;
import com.ezamora.coffeeshop.domain.model.LineItem;
import com.ezamora.coffeeshop.domain.enums.Size;

public record LineItemResponse(Drink drink, Milk milk, Size size, Integer quantity) {

    public static LineItemResponse fromDomain(LineItem lineItem) {

        return new LineItemResponse(
                lineItem.drink(),
                lineItem.milk(),
                lineItem.size(),
                lineItem.quantity()
        );
    }
}
