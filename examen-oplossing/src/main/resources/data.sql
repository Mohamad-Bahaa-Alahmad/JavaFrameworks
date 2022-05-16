INSERT INTO persons (auction_person_number, email_address, name) VALUES ('123-456-789', 'bob.marley@gmail.com', 'Bob Marley');
INSERT INTO persons (auction_person_number, email_address, name) VALUES ('612-759-100', 'frank.dylan@gmail.com', 'Frank Dylan');

INSERT INTO auctions (end_time, product_name, start_price, person_id) VALUES ('2021-06-20 13:38:12', 'Klassieke Vaas', '50', '123-456-789');
INSERT INTO auctions (end_time, product_name, start_price, person_id) VALUES ('2021-06-30 13:38:12', 'Glazen kast 17de eeuw', '1500', '612-759-100');
INSERT INTO auctions (end_time, product_name, start_price, person_id) VALUES ('2021-07-30 13:38:12', 'Geitenwollensok', '5', '612-759-100');

INSERT INTO auction_bids (price, auction_id, person_id) VALUES ('55', '1', '612-759-100');
INSERT INTO auction_bids (price, auction_id, person_id) VALUES ('3000', '2', '123-456-789');