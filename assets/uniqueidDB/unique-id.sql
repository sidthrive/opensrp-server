

-- Sequence: report.unique_id_id_seq

-- DROP SEQUENCE report.unique_id_id_seq;

CREATE SEQUENCE report.unique_id_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 3
  CACHE 1;
ALTER TABLE report.unique_id_id_seq
  OWNER TO postgres;

  -- Table: report.unique_id

-- DROP TABLE report.unique_id;

CREATE TABLE report.unique_id
(
  id integer NOT NULL DEFAULT nextval('report.unique_id_id_seq'::regclass),
  anm_id integer,
  last_value bigint,
  CONSTRAINT pk_unique_id PRIMARY KEY (id),
  CONSTRAINT fk_id_anm_dim_anm FOREIGN KEY (anm_id)
      REFERENCES report.dim_anm (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE report.unique_id
  OWNER TO postgres;


-- Sequence: report.last_id_id_seq

-- DROP SEQUENCE report.last_id_id_seq;

CREATE SEQUENCE report.last_id_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 3
  CACHE 1;
ALTER TABLE report.last_id_id_seq
  OWNER TO postgres;

  -- Table: report.last_id

-- DROP TABLE report.last_id;

CREATE TABLE report.last_id
(
  id integer NOT NULL DEFAULT nextval('report.last_id_id_seq'::regclass),
  anm_id integer,
  last_id bigint,
  CONSTRAINT pk_last_id PRIMARY KEY (id),
  CONSTRAINT fk_id_anm_dim_anm FOREIGN KEY (anm_id)
      REFERENCES report.dim_anm (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE report.last_id
  OWNER TO postgres;