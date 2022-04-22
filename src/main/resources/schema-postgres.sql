DROP TABLE IF EXISTS booking;
DROP TABLE IF EXISTS seat;
DROP TABLE IF EXISTS "show";

CREATE TABLE "show" (
	id serial4 NOT NULL,
	"name" varchar NOT NULL,
	CONSTRAINT show_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS seat (
	show_id int4 NOT NULL,
	seat_price numeric NOT NULL DEFAULT 0,
	id serial4 NOT NULL,
	"row" varchar NOT NULL DEFAULT 'A',
	seat_numbers varchar NOT NULL,
	show_date timestamp NOT NULL,
	CONSTRAINT seat_pk PRIMARY KEY (id, show_id)
);

CREATE TABLE IF NOT EXISTS booking (
	id serial4 NOT NULL,
	"name" varchar NOT NULL,
	"document" varchar NOT NULL,
	seat_row varchar NOT NULL,
	seat_numbers varchar NOT NULL,
	seat_price numeric NOT NULL,
	show_id int4 NOT NULL,
	date timestamp NOT NULL
);


ALTER TABLE seat DROP CONSTRAINT IF EXISTS seat_fk;
ALTER TABLE seat ADD CONSTRAINT seat_fk FOREIGN KEY (show_id) REFERENCES "show"(id);