ALTER TABLE IF EXISTS ONLY public.match_details DROP CONSTRAINT IF EXISTS fk_country_id CASCADE;
ALTER TABLE IF EXISTS ONLY public.match_details DROP CONSTRAINT IF EXISTS fk_sport_type_id CASCADE;
ALTER TABLE IF EXISTS ONLY public.cart_item DROP CONSTRAINT IF EXISTS fk_match_id CASCADE;
ALTER TABLE IF EXISTS ONLY public.cart_item DROP CONSTRAINT IF EXISTS fk_cart_id CASCADE;


DROP TABLE IF EXISTS public.country;
CREATE TABLE public.country (
    id serial NOT NULL PRIMARY KEY,
    name text NOT NULL,
    description text NOT NULL
);

DROP TABLE IF EXISTS public.sport_type;
CREATE TABLE public.sport_type (
    id serial NOT NULL PRIMARY KEY,
    name text NOT NULL
);

DROP TABLE IF EXISTS public.match_details;
CREATE TABLE public.match_details (
    id serial NOT NULL PRIMARY KEY,
    home_team text NOT NULL,
    away_team text NOT NULL,
    league_name text NOT NULL,
    home_odds float NOT NULL,
    draw_odds float NOT NULL,
    away_odds float NOT NULL,
    country_id int NOT NULL,
    sport_type_id int NOT NULL
);

DROP TABLE IF EXISTS public.cart_item;
CREATE TABLE public.cart_item (
    id serial NOT NULL PRIMARY KEY,
    chosen_team text NOT NULL,
    odds float NOT NULL,
    match_id integer NOT NULL,
    cart_id integer NOT NULL
);

DROP TABLE IF EXISTS public.cart;
CREATE TABLE public.cart (
    id serial NOT NULL PRIMARY KEY,
    actual_time date NOT NULL,
    bet float,
    possible_win integer,
    total_odds float
);

DROP TABLE IF EXISTS public.order;
CREATE TABLE public.order (
    id serial NOT NULL PRIMARY KEY,
    first_name text NOT NULL,
    last_name text NOT NULL,
    phone_number text NOT NULL,
    email_address text NOT NULL,
    country text NOT NULL,
    zip int NOT NULL,
    address text NOT NULL
);




ALTER TABLE ONLY public.match_details
    ADD CONSTRAINT fk_country_id FOREIGN KEY (country_id) REFERENCES public.country(id) ON DELETE CASCADE;

ALTER TABLE ONLY public.match_details
    ADD CONSTRAINT fk_sport_type_id FOREIGN KEY (sport_type_id) REFERENCES public.sport_type(id) ON DELETE CASCADE;

ALTER TABLE ONLY public.cart_item
    ADD CONSTRAINT fk_match_id FOREIGN KEY (match_id) REFERENCES public.match_details(id);

ALTER TABLE ONLY public.cart_item
    ADD CONSTRAINT fk_cart_id FOREIGN KEY (cart_id) REFERENCES public.cart(id) ON DELETE CASCADE;
