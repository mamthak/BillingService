--
-- PostgreSQL database dump
--

-- Dumped from database version 9.5.3
-- Dumped by pg_dump version 9.5.3

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: bill; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE bill (
    id bigint NOT NULL,
    name character varying(255),
    customerid bigint,
    servicecharge numeric,
    servicetax numeric,
    subtotal numeric,
    total numeric,
    cash numeric,
    card numeric,
    status character varying(255) DEFAULT false NOT NULL,
    createdon timestamp with time zone NOT NULL,
    lastmodifiedon timestamp with time zone NOT NULL,
    discount numeric DEFAULT '0'::numeric,
    deleted boolean DEFAULT false
);


ALTER TABLE bill OWNER TO postgres;

--
-- Name: billitem; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE billitem (
    id bigint NOT NULL,
    billid bigint NOT NULL,
    itemid bigint NOT NULL,
    quantity numeric DEFAULT 0 NOT NULL,
    discount numeric DEFAULT 0,
    total numeric DEFAULT 0 NOT NULL,
    createdon timestamp with time zone NOT NULL,
    lastmodifiedon timestamp with time zone NOT NULL,
    deleted boolean DEFAULT false
);


ALTER TABLE billitem OWNER TO postgres;

--
-- Name: category; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE category (
    id bigint NOT NULL,
    name character varying(255) NOT NULL,
    description character varying(255),
    createdon timestamp with time zone NOT NULL,
    lastmodifiedon timestamp with time zone NOT NULL,
    imagepath character varying(255),
    deleted boolean DEFAULT false
);


ALTER TABLE category OWNER TO postgres;

--
-- Name: category_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE category_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE category_id_seq OWNER TO postgres;

--
-- Name: category_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE category_id_seq OWNED BY category.id;


--
-- Name: category_seq_id; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE category_seq_id
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 1000000
    CACHE 1;


ALTER TABLE category_seq_id OWNER TO postgres;

--
-- Name: config_seq_id; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE config_seq_id
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 1000000
    CACHE 1;


ALTER TABLE config_seq_id OWNER TO postgres;

--
-- Name: configuration; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE configuration (
    id bigint NOT NULL,
    key character varying(255) NOT NULL,
    value character varying(255) NOT NULL,
    createdon timestamp with time zone NOT NULL,
    lastmodifiedon timestamp with time zone NOT NULL,
    deleted boolean DEFAULT false,
    name character varying(255) NOT NULL,
    description character varying(255),
    defaultvalue character varying(255) NOT NULL
);


ALTER TABLE configuration OWNER TO postgres;

--
-- Name: configuration_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE configuration_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE configuration_id_seq OWNER TO postgres;

--
-- Name: configuration_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE configuration_id_seq OWNED BY configuration.id;


--
-- Name: customer; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE customer (
    id bigint NOT NULL,
    name character varying(255) NOT NULL,
    phonenumber character varying(255) NOT NULL,
    address character varying(255) NOT NULL,
    createdon timestamp with time zone NOT NULL,
    lastmodifiedon timestamp with time zone NOT NULL,
    rewardpoints bigint DEFAULT '0'::bigint,
    deleted boolean DEFAULT false
);


ALTER TABLE customer OWNER TO postgres;

--
-- Name: customer_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE customer_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE customer_id_seq OWNER TO postgres;

--
-- Name: customer_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE customer_id_seq OWNED BY customer.id;


--
-- Name: customer_seq_id; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE customer_seq_id
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 1000000
    CACHE 1;


ALTER TABLE customer_seq_id OWNER TO postgres;

--
-- Name: databasechangelog; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE databasechangelog (
    id character varying(255) NOT NULL,
    author character varying(255) NOT NULL,
    filename character varying(255) NOT NULL,
    dateexecuted timestamp without time zone NOT NULL,
    orderexecuted integer NOT NULL,
    exectype character varying(10) NOT NULL,
    md5sum character varying(35),
    description character varying(255),
    comments character varying(255),
    tag character varying(255),
    liquibase character varying(20)
);


ALTER TABLE databasechangelog OWNER TO postgres;

--
-- Name: databasechangeloglock; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE databasechangeloglock (
    id integer NOT NULL,
    locked boolean NOT NULL,
    lockgranted timestamp without time zone,
    lockedby character varying(255)
);


ALTER TABLE databasechangeloglock OWNER TO postgres;

--
-- Name: item; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE item (
    id bigint NOT NULL,
    name character varying(255) NOT NULL,
    description character varying(255),
    categoryid bigint NOT NULL,
    price numeric NOT NULL,
    isinventory boolean DEFAULT false NOT NULL,
    quantity bigint,
    createdon timestamp with time zone NOT NULL,
    lastmodifiedon timestamp with time zone NOT NULL,
    imagepath character varying(255),
    deleted boolean DEFAULT false
);


ALTER TABLE item OWNER TO postgres;

--
-- Name: item_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE item_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE item_id_seq OWNER TO postgres;

--
-- Name: item_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE item_id_seq OWNED BY item.id;


--
-- Name: item_seq_id; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE item_seq_id
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 1000000
    CACHE 1;


ALTER TABLE item_seq_id OWNER TO postgres;

--
-- Name: order_item_seq_id; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE order_item_seq_id
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 1000000
    CACHE 1;


ALTER TABLE order_item_seq_id OWNER TO postgres;

--
-- Name: order_seq_id; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE order_seq_id
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 1000000
    CACHE 1;


ALTER TABLE order_seq_id OWNER TO postgres;

--
-- Name: orderitem_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE orderitem_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE orderitem_id_seq OWNER TO postgres;

--
-- Name: orderitem_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE orderitem_id_seq OWNED BY billitem.id;


--
-- Name: restaurantorder_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE restaurantorder_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE restaurantorder_id_seq OWNER TO postgres;

--
-- Name: restaurantorder_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE restaurantorder_id_seq OWNED BY bill.id;


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY bill ALTER COLUMN id SET DEFAULT nextval('restaurantorder_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY billitem ALTER COLUMN id SET DEFAULT nextval('orderitem_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY category ALTER COLUMN id SET DEFAULT nextval('category_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY configuration ALTER COLUMN id SET DEFAULT nextval('configuration_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY customer ALTER COLUMN id SET DEFAULT nextval('customer_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY item ALTER COLUMN id SET DEFAULT nextval('item_id_seq'::regclass);


--
-- Data for Name: bill; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY bill (id, name, customerid, servicecharge, servicetax, subtotal, total, cash, card, status, createdon, lastmodifiedon, discount, deleted) FROM stdin;
\.


--
-- Data for Name: billitem; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY billitem (id, billid, itemid, quantity, discount, total, createdon, lastmodifiedon, deleted) FROM stdin;
\.


--
-- Data for Name: category; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY category (id, name, description, createdon, lastmodifiedon, imagepath, deleted) FROM stdin;
51	Soft Drinks	\N	2016-09-11 09:40:47.773+05:30	2016-09-11 09:40:47.773+05:30	images/thumbnails/food (3).svg	f
52	Starters	\N	2016-09-11 09:40:54.648+05:30	2016-09-11 09:40:54.648+05:30	images/thumbnails/food (8).svg	f
50	Hot Drinks	\N	2016-09-11 04:10:37+05:30	2016-09-11 09:41:02.556+05:30	images/thumbnails/food (1).svg	f
\.


--
-- Name: category_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('category_id_seq', 1, false);


--
-- Name: category_seq_id; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('category_seq_id', 1, true);


--
-- Name: config_seq_id; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('config_seq_id', 1, false);


--
-- Data for Name: configuration; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY configuration (id, key, value, createdon, lastmodifiedon, deleted, name, description, defaultvalue) FROM stdin;
1	servicecharge	10	2016-09-11 09:38:43.187344+05:30	2016-09-11 09:38:43.187344+05:30	f	Service Charge	\N	0
2	servicetax	0	2016-09-11 09:38:43.187344+05:30	2016-09-11 09:38:43.187344+05:30	f	Service Tax	\N	0
3	vat	0	2016-09-11 09:38:43.187344+05:30	2016-09-11 09:38:43.187344+05:30	f	VAT	\N	0
\.


--
-- Name: configuration_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('configuration_id_seq', 3, true);


--
-- Data for Name: customer; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY customer (id, name, phonenumber, address, createdon, lastmodifiedon, rewardpoints, deleted) FROM stdin;
\.


--
-- Name: customer_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('customer_id_seq', 1, false);


--
-- Name: customer_seq_id; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('customer_seq_id', 1, false);


--
-- Data for Name: databasechangelog; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY databasechangelog (id, author, filename, dateexecuted, orderexecuted, exectype, md5sum, description, comments, tag, liquibase) FROM stdin;
201607170737	Thiru	classpath:liquibase/changesets.xml	2016-09-11 09:38:42.973016	1	EXECUTED	7:02cfc8a3bb636843ee9160baad638830	createTable (x2), addForeignKeyConstraint, createSequence (x2)		\N	3.3.5
201607250641	Thiru	classpath:liquibase/changesets.xml	2016-09-11 09:38:43.00596	2	EXECUTED	7:ccb975f2cf37afaf09ff783e311c3a7f	createTable, createSequence		\N	3.3.5
201607262200	Thiru	classpath:liquibase/changesets.xml	2016-09-11 09:38:43.021966	3	EXECUTED	7:ebd266b1c375486bb806541b2cd0001a	addColumn		\N	3.3.5
201607262234	Thiru	classpath:liquibase/changesets.xml	2016-09-11 09:38:43.033215	4	EXECUTED	7:692efc0a6879d85a5b74de74131d56dc	createTable, createSequence		\N	3.3.5
201608011842	Thiru	classpath:liquibase/changesets.xml	2016-09-11 09:38:43.070868	5	EXECUTED	7:6cf16a4f51439d29dc610e8cf01b6ed0	createTable, createSequence, addForeignKeyConstraint, createTable, createSequence, addForeignKeyConstraint (x2)		\N	3.3.5
201608061232	Thiru	classpath:liquibase/changesets.xml	2016-09-11 09:38:43.08801	6	EXECUTED	7:5353b739beeaa7b0e6d9e2dbae2fdce3	modifyDataType		\N	3.3.5
201608060956	Thiru	classpath:liquibase/changesets.xml	2016-09-11 09:38:43.094032	7	EXECUTED	7:427e93ba69299fc4ee4a9e9684c9d01e	addColumn		\N	3.3.5
201608070632	Thiru	classpath:liquibase/changesets.xml	2016-09-11 09:38:43.102938	8	EXECUTED	7:e68276746b24d661f79349d9c0a30289	renameTable (x2), renameColumn		\N	3.3.5
201608070813	Thiru	classpath:liquibase/changesets.xml	2016-09-11 09:38:43.110326	9	EXECUTED	7:54f885cd6017c4ad94f2fd55b34e7255	addColumn (x2)		\N	3.3.5
201608150113	Thiru	classpath:liquibase/changesets.xml	2016-09-11 09:38:43.156178	10	EXECUTED	7:19774a101a0bdc78365ae7627e545db3	addColumn (x6)		\N	3.3.5
201608210547	Thiru	classpath:liquibase/changesets.xml	2016-09-11 09:38:43.168993	11	EXECUTED	7:9754df0e37d6cd954d2146db464f0d7f	dropForeignKeyConstraint, addForeignKeyConstraint		\N	3.3.5
201609040209	Thiru	classpath:liquibase/changesets.xml	2016-09-11 09:38:43.175003	12	EXECUTED	7:8c9bc78926516d5a785247d1aaad5e16	addDefaultValue		\N	3.3.5
201609050806	Thiru	classpath:liquibase/changesets.xml	2016-09-11 09:38:43.184522	13	EXECUTED	7:f5d9b094ffccc9244616b6bed75c7305	addColumn		\N	3.3.5
201609061148	Thiru	classpath:liquibase/changesets.xml	2016-09-11 09:38:43.19409	14	EXECUTED	7:ba8f5dca4f4b41d487555ff58dfbb810	insert (x3)		\N	3.3.5
\.


--
-- Data for Name: databasechangeloglock; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY databasechangeloglock (id, locked, lockgranted, lockedby) FROM stdin;
1	f	\N	\N
\.


--
-- Data for Name: item; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY item (id, name, description, categoryid, price, isinventory, quantity, createdon, lastmodifiedon, imagepath, deleted) FROM stdin;
50	Pepsi	\N	51	20	f	\N	2016-09-11 09:41:16.835+05:30	2016-09-11 09:41:16.835+05:30	images/thumbnails/food (3).svg	f
51	Chilly chicken	\N	52	120	f	\N	2016-09-11 09:41:33.553+05:30	2016-09-11 09:41:33.553+05:30	images/thumbnails/food (8).svg	f
52	Tea	\N	50	22	f	\N	2016-09-11 09:41:47.208+05:30	2016-09-11 09:41:47.208+05:30	images/thumbnails/food (1).svg	f
\.


--
-- Name: item_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('item_id_seq', 1, false);


--
-- Name: item_seq_id; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('item_seq_id', 1, true);


--
-- Name: order_item_seq_id; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('order_item_seq_id', 1, false);


--
-- Name: order_seq_id; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('order_seq_id', 1, false);


--
-- Name: orderitem_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('orderitem_id_seq', 1, false);


--
-- Name: restaurantorder_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('restaurantorder_id_seq', 1, false);


--
-- Name: pk_category; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY category
    ADD CONSTRAINT pk_category PRIMARY KEY (id);


--
-- Name: pk_configuration; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY configuration
    ADD CONSTRAINT pk_configuration PRIMARY KEY (id);


--
-- Name: pk_customer; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY customer
    ADD CONSTRAINT pk_customer PRIMARY KEY (id);


--
-- Name: pk_databasechangeloglock; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY databasechangeloglock
    ADD CONSTRAINT pk_databasechangeloglock PRIMARY KEY (id);


--
-- Name: pk_item; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY item
    ADD CONSTRAINT pk_item PRIMARY KEY (id);


--
-- Name: pk_orderitem; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY billitem
    ADD CONSTRAINT pk_orderitem PRIMARY KEY (id);


--
-- Name: pk_restaurantorder; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY bill
    ADD CONSTRAINT pk_restaurantorder PRIMARY KEY (id);


--
-- Name: billitem_bill_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY billitem
    ADD CONSTRAINT billitem_bill_id_fk FOREIGN KEY (billid) REFERENCES bill(id) ON DELETE CASCADE;


--
-- Name: item_category_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY item
    ADD CONSTRAINT item_category_id_fk FOREIGN KEY (categoryid) REFERENCES category(id);


--
-- Name: order_customer_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY bill
    ADD CONSTRAINT order_customer_id_fk FOREIGN KEY (customerid) REFERENCES customer(id);


--
-- Name: orderitem_item_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY billitem
    ADD CONSTRAINT orderitem_item_id_fk FOREIGN KEY (itemid) REFERENCES item(id);


--
-- Name: public; Type: ACL; Schema: -; Owner: thirur
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM thirur;
GRANT ALL ON SCHEMA public TO thirur;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--

