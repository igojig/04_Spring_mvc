
 insert into users (first_name, last_name, email, date_of_birth, passport, password)
 values ('Ivan', 'Petrov', 'ivan@mail.ru','1966-02-23', '12-2345', '$2a$12$kU7IZYuQMPkClQ9/3bpWoeTN362n7MEjT.MmBNWxeYgmJEqOr7QSe'), -- 1
 ('Oleg', 'Sidorov','oleg@mail.ru' ,'1977-03-24', '12-2345', '$2a$12$Y/vtSHOfsZoynFmOPBmgG.d7UCAJxyK/h.uPfgRiagf5UMmsg2Vdi'), -- 1
 ('Ben', 'Ivanov', 'ben@mail.ru','1987-04-14', '12-2345', '$2a$12$Ebl3OB22F5A02R6ZrAXHmeNO4z/BF/X0YVtVWgzSNftlKodUqIMR6'); -- 1


 insert into bills (user_id,  type, balance ) values
 (1,  'DEPOSIT', 1000),
 (1,  'DEBIT_CART', 2000),
 (1,  'CREDIT_CART',3000),
 (2,  'DEPOSIT',4000),
 (2,  'DEBIT_CART',5000),
 (2,  'CREDIT_CART',6000);

 insert into carts ( balance, cart_status, cart_type, bill_id, payment_system) values
 ( 10000, 'ACTIVE','DEBIT', 2, 'VISA'),
 ( 20000, 'BLOCKED','CREDIT', 6, 'MASTERCARD');

