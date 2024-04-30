INSERT INTO "user" (first_name, last_name, cnp, username, password)
VALUES ('Tavore', 'Paran', '6000209409817', 'tavore', 'parola');

INSERT INTO "user" (first_name, last_name, cnp, username, password)
VALUES ('Anomander', 'Rake', '1851105409760', 'rake', 'parola');

INSERT INTO current_account (balance, iban, user_id)
VALUES (100, 'RO96RZBR6485927915888313', 1);

INSERT INTO current_account (balance, iban, user_id)
VALUES (200, 'RO12PORL6155267818499438', 5);

INSERT INTO card (card_number, expiration_date, current_account_id)
VALUES ('4014677458486157', '2030-05-20', 1);

INSERT INTO card (card_number, expiration_date, current_account_id)
VALUES ('4016934448384135', '2035-03-18', 1);

INSERT INTO card_holder (first_name, last_name, cnp, card_id)
VALUES ('Tavore', 'Paran', '6000209409817', 1);

INSERT INTO card_holder (first_name, last_name, cnp, card_id)
VALUES ('Felisin', 'Paran', '6040817405638', 2);

INSERT INTO card_transaction (amount, date, description, card_id)
VALUES (10, '2023-04-25', 'mancare', 1);

INSERT INTO deposit_type (months_duration, interest_rate)
VALUES (1, 5);

INSERT INTO deposit_type (months_duration, interest_rate)
VALUES (3, 5.5);

INSERT INTO deposit_type (months_duration, interest_rate)
VALUES (6, 6);

INSERT INTO deposit_type (months_duration, interest_rate)
VALUES (12, 6.5);

INSERT INTO deposit_account (balance, iban, user_id, last_renewal_date, deposit_type_id)
VALUES (1000, 'RO52RZBR6917994265774137', 1, '2024-04-26', 2);

INSERT INTO transfer (amount, date, description, payer_account_id, recipient_account_id)
VALUES (10, '2024-04-23', 'transfer', 4, 1);
