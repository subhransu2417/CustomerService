DROP TABLE IF EXISTS public.customer;

CREATE TABLE IF NOT EXISTS public.customer (
	id varchar NOT NULL,
	first_name varchar NOT NULL,
	last_name varchar NOT NULL,
	dob DATE NOT NULL,
	CONSTRAINT id_pk PRIMARY KEY (id)
);