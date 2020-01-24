--
-- Table: ip_addresses
-- Descr: Store list of assignable ip addresses
-- L Upd: 2020-01-22
-- -----------------------------------------------

DROP TABLE IF EXISTS ip_addresses;
CREATE TABLE ip_addresses (
    ip_address varchar(15) not null,
    address_status varchar(10) null,
    PRIMARY KEY (ip_address)
);