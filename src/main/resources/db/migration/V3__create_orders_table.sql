CREATE TABLE public.orders (
	oid bigserial NOT NULL,
	address varchar(255) NULL,
	customer_id int8 NULL,
	order_date timestamp NULL,
	total_cost float8 NULL,
	CONSTRAINT orders_pkey PRIMARY KEY (oid)
);

CREATE TABLE public.order_item (
	order_item_id bigserial NOT NULL,
	additional_cost float8 NULL,
	cut_off_days int4 NULL,
	initial_cost float8 NULL,
	number_of_days int4 NULL,
	order_id int8 NULL,
	total_cost float8 NULL,
	movie_id int8 NULL,
	CONSTRAINT order_item_pkey PRIMARY KEY (order_item_id)
);

ALTER TABLE public.order_item ADD CONSTRAINT fk1ms29bhgl66ps0hfr33b0o3bo FOREIGN KEY (movie_id) REFERENCES movie(mid);
ALTER TABLE public.order_item ADD CONSTRAINT fkt4dc2r9nbvbujrljv3e23iibt FOREIGN KEY (order_id) REFERENCES orders(oid);