drop table if exists urban_transport_ride_records;
create table urban_transport_ride_records (
   id bigserial,
   date timestamp not null,
   ticket_type varchar (30) not null,
   user_id bigserial
);

drop table if exists users;
create table users (
    id bigserial,
    username varchar(50),
    password varchar(50),
    email varchar(150),
    created_date timestamp not null,
    zone_id varchar(30)
);


drop table if exists pricelist;
create table pricelist (
    id bigserial,
    pricelist_item_name varchar(30),
    pricelist_item_value numeric
);