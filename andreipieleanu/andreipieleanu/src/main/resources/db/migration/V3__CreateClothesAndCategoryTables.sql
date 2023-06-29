CREATE TABLE cs_category
(
    category_id int NOT NULL AUTO_INCREMENT,
    category_name   varchar(30),
    category_parent_id int,
    primary key (category_id),
    foreign key (category_parent_id) references cs_category(category_id)
);

CREATE TABLE cs_clothes
(
    clothes_id int NOT NULL AUTO_INCREMENT,
    clothes_name   varchar(30) NOT NULL,
    clothes_description   varchar(200) NOT NULL,
    clothes_price double precision NOT NULL,
    clothes_amount_in_stock int NOT NULL,
    clothes_is_active bool,
    clothes_size int not null,
    clothes_meas_amount int not null,
    clothes_meas_unit varchar(20) not null,
    clothes_subcategory_id int NOT NULL,
    PRIMARY KEY (clothes_id),
    FOREIGN KEY (clothes_subcategory_id) REFERENCES cs_category(category_id)
);