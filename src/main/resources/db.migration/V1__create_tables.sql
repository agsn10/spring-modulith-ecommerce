CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Create tables

-- Tabela de clientes
CREATE TABLE clients (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name TEXT NOT NULL,
    email TEXT UNIQUE NOT NULL,
    phone TEXT
);

-- Tabela de endereços
CREATE TABLE addresses (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    client_id UUID NOT NULL,
    rua TEXT NOT NULL,
    numero TEXT NOT NULL,
    cidade TEXT NOT NULL,
    estado TEXT NOT NULL,
    cep TEXT NOT NULL,
    CONSTRAINT fk_client
        FOREIGN KEY (client_id)
        REFERENCES clients(id)
        ON DELETE CASCADE
);


-- Product
CREATE TABLE products (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name TEXT NOT NULL,
    description TEXT,
    price NUMERIC(12, 2) NOT NULL,
    stock_quantity INTEGER NOT NULL,
    category TEXT
);

-- Catalog
CREATE TABLE catalogs (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name TEXT NOT NULL
);

-- Catálogo → Produtos (relação N:N)
CREATE TABLE catalog_products (
    id UUID NOT NULL,
    catalog_id UUID NOT NULL,
    product_id UUID NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (catalog_id) REFERENCES catalogs(id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE
);

-- Order
CREATE TABLE orders (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    client_id UUID NOT NULL,
    total_amount NUMERIC(12, 2) NOT NULL,
    status TEXT NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (client_id) REFERENCES clients(id) ON DELETE CASCADE
);

-- OrderItem
CREATE TABLE order_items (
    order_id UUID NOT NULL,
    product_id UUID NOT NULL,
    product_name TEXT NOT NULL,
    quantity INTEGER NOT NULL,
    unit_price NUMERIC(12, 2) NOT NULL,
    total_price NUMERIC(12, 2) NOT NULL,
    PRIMARY KEY (order_id, product_id),
    FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE
);

-- Payment
CREATE TABLE payments (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    order_id UUID NOT NULL,
    status TEXT NOT NULL,
    method TEXT NOT NULL,
    amount NUMERIC(12, 2) NOT NULL,
    paid_at TIMESTAMP WITHOUT TIME ZONE,
    FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE
);

-- Invoice
CREATE TABLE invoices (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    order_id UUID NOT NULL,
    invoice_number TEXT UNIQUE NOT NULL,
    generated_at TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    total_amount NUMERIC(12, 2) NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE
);

-- (Add other tables similarly)
