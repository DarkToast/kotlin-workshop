--
-- <<entity>>              <<value>>      <<entity>>
-- [shopping_cart] 1 <-- * [item] 1 --> 1 [product]
--

CREATE TABLE product
(
    sku   VARCHAR(40) NOT NULL,
    name  VARCHAR(40) NOT NULL,
    price INTEGER     NOT NULL,
    CONSTRAINT product_pk PRIMARY KEY (sku)
);

CREATE TABLE shopping_cart
(
    uuid UUID NOT NULL,
    CONSTRAINT shopping_cart_pk PRIMARY KEY (uuid)
);

CREATE TABLE item
(
    shopping_cart_id UUID        NOT NULL,
    sku              VARCHAR(40) NOT NULL,
    effective_price  INTEGER     NOT NULL,
    quantity         INTEGER     NOT NULL,
    CONSTRAINT item_pk PRIMARY KEY (shopping_cart_id, sku),
    CONSTRAINT item_sc_fk FOREIGN KEY (shopping_cart_id) REFERENCES shopping_cart (uuid),
    CONSTRAINT item_product_fk FOREIGN KEY (sku) REFERENCES product (sku)
);