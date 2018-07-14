insert into book (id, version, title, author, price, quantity, date_created, date_updated) values (101, 0, 'Mastering åäö', 'Average Swede' , 762.00, 15, now(), now());
insert into book (id, version, title, author, price, quantity, date_created, date_updated) values (103, 0, 'Generic Title', 'First Author' , 62.00, 10 , now(), now());
insert into book (id, version, title, author, price, quantity, date_created, date_updated) values (104, 0, 'Generic Title', 'Second Author' , 63.00, 12 , now(), now());
insert into book (id, version, title, author, price, quantity, date_created, date_updated) values (105, 0, 'Random Sales', 'Cunning Bastard' , 72.00, 19 , now(), now());
insert into book (id, version, title, author, price, quantity, date_created, date_updated) values (102, 0, 'How To Spend Money', 'Rich Bloke' , 1000000.00, 1, now(), now());

insert into basket (id, version, user_id, total_price, date_created, date_updated) values (101, 0, 111, 100.00, now(), now());

insert into basket_books (id, version, book_id, basket_id, quantity, is_added) values (101, 0, 103, 101, 2, true);
insert into basket_books (id, version, book_id, basket_id, quantity, is_added) values (102, 0, 104, 101, 1, true);
insert into basket_books (id, version, book_id, basket_id, quantity, is_added) values (103, 0, 102, 101, 3, false);