CREATE TABLE cs_address
(
    address_id    int         NOT NULL AUTO_INCREMENT,
    street_name   varchar(50) NOT NULL,
    street_number int         NOT NULL,
    zipcode       varchar(10) NOT NULL,
    city          varchar(30) NOT NULL,
    country       varchar(30) NOT NULL,
    phone         varchar(20) NOT NULL,
    user_id       int         NOT NULL,
    PRIMARY KEY (address_id),
    foreign key (user_id) references cs_users (user_id)
);
