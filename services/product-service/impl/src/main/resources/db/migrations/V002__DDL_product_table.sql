CREATE TABLE IF NOT EXISTS product(
    id uuid PRIMARY KEY,
    name varchar(50),
    price numeric CONSTRAINT positive_price CHECK (price > 0),
    description text
);