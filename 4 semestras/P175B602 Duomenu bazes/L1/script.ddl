#@(#) script.ddl


DROP TABLE IF EXISTS order_details;
DROP TABLE IF EXISTS refunds;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS employees;
DROP TABLE IF EXISTS clothes;
DROP TABLE IF EXISTS styles;
DROP TABLE IF EXISTS shops;
DROP TABLE IF EXISTS providers;
DROP TABLE IF EXISTS designers;
DROP TABLE IF EXISTS customers;
CREATE TABLE customers
(
	name varchar (255) NOT NULL,
	surname varchar (255) NOT NULL,
	email varchar (255) NOT NULL,
	age int NOT NULL,
	phone int NOT NULL,
	country varchar (255) NOT NULL,
	city varchar (255) NOT NULL,
	address varchar (255) NOT NULL,
	card_number int NOT NULL,
	id_customer int NOT NULL AUTO_INCREMENT,
	PRIMARY KEY(id_customer)
);

CREATE TABLE designers
(
	name varchar (255) NOT NULL,
	surname varchar (255) NOT NULL,
	country varchar (255) NOT NULL,
	awards_won int NOT NULL,
	age int NOT NULL,
	id_designer int NOT NULL AUTO_INCREMENT,
	PRIMARY KEY(id_designer)
);

CREATE TABLE providers
(
	company_id int NOT NULL AUTO_INCREMENT,
	name varchar (255) NOT NULL,
	country varchar (255) NOT NULL,
	PRIMARY KEY(company_id)
);

CREATE TABLE shops
(
	opening_time time NOT NULL,
	closing_time time NOT NULL,
	phone int NOT NULL,
	city varchar (255) NOT NULL,
	country varchar (255) NOT NULL,
	address varchar (255) NOT NULL,
	id_shop int NOT NULL AUTO_INCREMENT,
	PRIMARY KEY(id_shop)
);

CREATE TABLE styles
(
	name varchar (255) NOT NULL,
	year int NOT NULL,
	description text NULL,
	id_style int NOT NULL AUTO_INCREMENT,
	PRIMARY KEY(id_style)
);

CREATE TABLE clothes
(
	name varchar (255) NOT NULL,
	description text NOT NULL,
	msrp int NOT NULL,
	buy_price int NOT NULL,
	quantity_in_stock int NULL,
	type ENUM('pants', 't-shirt', 'hat', 'skirt', 'underwear', 'jacket', 'shoes') NOT NULL,
	id_clothes int NOT NULL AUTO_INCREMENT,
	fk_designerid_designer int NOT NULL,
	fk_styleid_style int NOT NULL,
	fk_providercompany_id int NOT NULL,
	PRIMARY KEY(id_clothes),
	CONSTRAINT designs FOREIGN KEY(fk_designerid_designer) REFERENCES designers (id_designer),
	CONSTRAINT has FOREIGN KEY(fk_styleid_style) REFERENCES styles (id_style),
	CONSTRAINT makes FOREIGN KEY(fk_providercompany_id) REFERENCES providers (company_id)
);

CREATE TABLE employees
(
	name varchar (255) NOT NULL,
	surname varchar (255) NOT NULL,
	phone int NOT NULL,
	age int NOT NULL,
	address varchar (255) NOT NULL,
	bank_account varchar (255) NOT NULL,
	id_employee int NOT NULL AUTO_INCREMENT,
	fk_employeeid_employee int NULL,
	fk_shop_id int NULL,
	PRIMARY KEY(id_employee),
	CONSTRAINT manages FOREIGN KEY(fk_employeeid_employee) REFERENCES employees (id_employee),
	CONSTRAINT works_in FOREIGN KEY(fk_shop_id) REFERENCES shops (id_shop)
);

CREATE TABLE orders
(
	date_ordered datetime NOT NULL,
	date_shipped datetime NULL,
	ship_to text NOT NULL,
	status varchar (255) NOT NULL,
	comments text NULL,
	id_order int NOT NULL AUTO_INCREMENT,
	fk_customerid_customer int NOT NULL,
	fk_shop_id int NOT NULL,
	PRIMARY KEY(id_order),
	CONSTRAINT makes2 FOREIGN KEY(fk_customerid_customer) REFERENCES customers (id_customer),
	CONSTRAINT made_in FOREIGN KEY(fk_shop_id) REFERENCES shops (id_shop)
);

CREATE TABLE refunds
(
	quantity int NOT NULL,
	comment text NULL,
	id_refund int NOT NULL AUTO_INCREMENT,
	fk_customerid_customer int NOT NULL,
	PRIMARY KEY(id_refund),
	CONSTRAINT has2 FOREIGN KEY(fk_customerid_customer) REFERENCES customers (id_customer)
);

CREATE TABLE order_details
(
	order_id int NOT NULL,
	clothes_id int NOT NULL,
	price_each int NOT NULL,
	quantity int NOT NULL,
	amount_refunded int NULL,
	refund_reason text NULL,
	PRIMARY KEY(order_id, clothes_id),
	CONSTRAINT has3 FOREIGN KEY(order_id) REFERENCES orders (id) ON DELETE CASCADE,
	CONSTRAINT has4 FOREIGN KEY(clothes_id) REFERENCES clothes (id) ON DELETE CASCADE
);

ALTER TABLE order_details ADD FOREIGN KEY (fk_orderid_order) REFERENCES orders (id);
ALTER TABLE order_details ADD FOREIGN KEY (fk_refundid_refund) REFERENCES refunds (id);