create table cs_shopping_cart
(
    shopping_cart_id int not null AUTO_INCREMENT,
    user_id int not null,
    primary key (shopping_cart_id),
    foreign key (user_id) references cs_users (user_id)
);

create table cs_cart_item
(
    cart_item_id int not null AUTO_INCREMENT,
    item_amount int not null,
    clothes_id int not null,
    shopping_cart_id int not null,
    primary key (cart_item_id),
    foreign key (clothes_id) references cs_clothes(clothes_id),
    foreign key (shopping_cart_id) references cs_shopping_cart(shopping_cart_id)
);