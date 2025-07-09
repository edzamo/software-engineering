-- =================================================================
-- Script de datos iniciales para la base de datos 'coffee-shop'
-- Modelo de datos unificado.
-- =================================================================

-- Pedido 1: Un pedido para tomar en tienda, que ya ha sido pagado.
INSERT INTO orders (id, order_date, total_amount, status, location)
VALUES (1, '2024-05-21 10:30:00', 9.50, 'COMPLETED', 'IN_STORE');

-- Items para el pedido 1
INSERT INTO order_items (order_id, item) VALUES (1, 'Large Cappuccino');
INSERT INTO order_items (order_id, item) VALUES (1, 'Chocolate Chip Cookie');

-- Pago para el pedido 1
INSERT INTO payments (id, amount, payment_date, payment_method, status, order_id)
VALUES (1, 9.50, '2024-05-21 10:31:00', 'CREDIT_CARD', 'COMPLETED', 1);

-- Pedido 2: Un pedido para llevar, pendiente de pago.
INSERT INTO orders (id, order_date, total_amount, status, location)
VALUES (2, '2024-05-21 11:00:00', 4.75, 'PENDING_PAYMENT', 'TAKE_AWAY');
INSERT INTO order_items (order_id, item) VALUES (2, 'Americano');