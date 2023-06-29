CREATE TABLE cs_user_roles
(
    role_id        int         NOT NULL AUTO_INCREMENT,
    role_name varchar(50) NOT NULL,
    PRIMARY KEY (role_id),
    UNIQUE (role_name)
);
insert into cs_user_roles (role_name) values ('CLIENT'), ('WORKER');

CREATE TABLE cs_users
(
    user_id         int         NOT NULL AUTO_INCREMENT,
    user_email   varchar(50) NOT NULL,
    user_password   varchar(100) NOT NULL,
    user_firstname   varchar(50) NOT NULL,
    user_lastname   varchar(50) NOT NULL,
    role_id int not null ,
    PRIMARY KEY (user_id),
    foreign key(role_id) references cs_user_roles(role_id)
);

insert into cs_users (user_email, user_password, user_firstname,user_lastname, role_id) values 
('admin1@gmail.com', '$2a$10$ICNLCswyFW3CYAATAH9TxOgROstkHYvG326WaxGFERKwsJVOPPogq', 'Admin', 'One', 2), 
('admin2@gmail.com', '$2a$10$mphTqRvvQT23jGPn7uvzPOe.V4mv2aBDajjshWkhhIJl1hN1poO46', 'Admin', 'Two', 2);
