create table cs_order_status
(
    order_status_id int not null AUTO_INCREMENT,
    order_status_value varchar(50) not null,
    primary key (order_status_id)
);
insert into cs_order_status(order_status_value) values ('NEW'), ('PENDING'), 
                                                       ('SHIPPED'), ('DELIVERED'), ('CLOSED');
create table cs_orders
(
    order_id int not null AUTO_INCREMENT, 
    order_status_id int not null, 
    user_id int not null, 
    address_id int not null, 
    created_date DATETIME not null, 
    primary key (order_id), 
    foreign key (user_id) references cs_users(user_id), 
    foreign key (address_id) references cs_address(address_id), 
    foreign key (order_status_id) references cs_order_status(order_status_id)
);

create table cs_order_items
(
    order_item_id int not null AUTO_INCREMENT,
    order_id int not null, 
    clothes_id int not null,
    item_amount int not null,
    item_price double not null, 
    primary key (order_item_id), 
    foreign key (order_id) references cs_orders(order_id), 
    foreign key (clothes_id) references cs_clothes(clothes_id) 
);
