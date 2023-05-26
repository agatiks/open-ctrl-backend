drop table if exists passport cascade;

create table passport (
    id UUID primary key,
    number bigint not null,
    receipt_date timestamp not null,
    receipt_place_id UUID not null,
    registered_address_id UUID not null,
    foreign key (registered_address_id) references address (id),
    foreign key (receipt_place_id) references address (id)
);