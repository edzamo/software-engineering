package com.ezamora.coffeeshop.infrastructure.adapter.out.persistence.order;

import com.ezamora.coffeeshop.infrastructure.adapter.out.persistence.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
    // Aquí puedes agregar métodos de consulta personalizados si los necesitas
}