package com.ezamora.coffeeshop.infrastructure.adapter.in.mapper;

import com.ezamora.coffeeshop.domain.enums.Drink;
import com.ezamora.coffeeshop.domain.enums.Milk;
import com.ezamora.coffeeshop.domain.enums.Size;
import com.ezamora.coffeeshop.domain.model.LineItem;

public record LineItemRequest(Drink drink, Milk milk, Size size, Integer quantity) {

    public LineItem toDomain() {
        return new LineItem(drink, milk, size, quantity);
    }
}
