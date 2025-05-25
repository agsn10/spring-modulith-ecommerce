-- Insert sample data
-- INSERT INTO client (id, name, email, phone, address) VALUES
-- (uuid_generate_v4(), 'John Doe', 'john.doe@example.com', '123456789', '{"rua":"Rua A","numero":"123","cidade":"São Paulo","estado":"SP","CEP":"01000-000"}');

INSERT INTO clients (id, name, email, phone) VALUES
  ('11111111-1111-1111-1111-111111111111', 'João Silva', 'joao@example.com', '48999990001'),
  ('22222222-2222-2222-2222-222222222222', 'Maria Oliveira', 'maria@example.com', '48999990002');

INSERT INTO addresses (id, client_id, rua, numero, cidade, estado, cep) VALUES
  (uuid_generate_v4(), '11111111-1111-1111-1111-111111111111', 'Rua A', '123', 'Florianópolis', 'SC', '88000-000'),
  (uuid_generate_v4(), '22222222-2222-2222-2222-222222222222', 'Rua B', '456', 'São José', 'SC', '88100-000');

INSERT INTO products (id, name, description, price, stock_quantity, category) VALUES
  ('aaaaaaa1-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'Notebook Dell', 'Inspiron 15 com 16GB RAM', 4200.00, 10, 'Informática'),
  ('aaaaaaa2-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'Smartphone Samsung', 'Galaxy S21', 3500.00, 25, 'Telefonia'),
  ('aaaaaaa3-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'Mouse Logitech', 'Wireless M280', 120.00, 100, 'Periféricos');

INSERT INTO catalogs (id, name) VALUES
  ('ccccccc1-cccc-cccc-cccc-cccccccccccc', 'Eletrônicos 2025'),
  ('ccccccc2-cccc-cccc-cccc-cccccccccccc', 'Ofertas de Verão');

INSERT INTO catalog_products (catalog_id, product_id) VALUES
  ('ccccccc1-cccc-cccc-cccc-cccccccccccc', 'aaaaaaa1-aaaa-aaaa-aaaa-aaaaaaaaaaaa'),
  ('ccccccc1-cccc-cccc-cccc-cccccccccccc', 'aaaaaaa2-aaaa-aaaa-aaaa-aaaaaaaaaaaa'),
  ('ccccccc2-cccc-cccc-cccc-cccccccccccc', 'aaaaaaa3-aaaa-aaaa-aaaa-aaaaaaaaaaaa');

INSERT INTO orders (id, client_id, total_amount, status, created_at) VALUES
  ('ddddddd1-dddd-dddd-dddd-dddddddddddd', '11111111-1111-1111-1111-111111111111', 4320.00, 'PAID', NOW()),
  ('ddddddd2-dddd-dddd-dddd-dddddddddddd', '22222222-2222-2222-2222-222222222222', 3620.00, 'PENDING', NOW());

INSERT INTO order_items (order_id, product_id, product_name, quantity, unit_price, total_price) VALUES
  ('ddddddd1-dddd-dddd-dddd-dddddddddddd', 'aaaaaaa1-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'Notebook Dell', 1, 4200.00, 4200.00),
  ('ddddddd1-dddd-dddd-dddd-dddddddddddd', 'aaaaaaa3-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'Mouse Logitech', 1, 120.00, 120.00),

  ('ddddddd2-dddd-dddd-dddd-dddddddddddd', 'aaaaaaa2-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'Smartphone Samsung', 1, 3500.00, 3500.00),
  ('ddddddd2-dddd-dddd-dddd-dddddddddddd', 'aaaaaaa3-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'Mouse Logitech', 1, 120.00, 120.00);

INSERT INTO payments (id, order_id, status, method, amount, paid_at) VALUES
  (uuid_generate_v4(), 'ddddddd1-dddd-dddd-dddd-dddddddddddd', 'APPROVED', 'CARTAO_CREDITO', 4320.00, NOW()),
  (uuid_generate_v4(), 'ddddddd2-dddd-dddd-dddd-dddddddddddd', 'PENDING', 'BOLETO', 3620.00, NULL);

INSERT INTO invoices (id, order_id, invoice_number, generated_at, total_amount) VALUES
  (uuid_generate_v4(), 'ddddddd1-dddd-dddd-dddd-dddddddddddd', 'NF-2025-00001', NOW(), 4320.00),
  (uuid_generate_v4(), 'ddddddd2-dddd-dddd-dddd-dddddddddddd', 'NF-2025-00002', NOW(), 3620.00);
