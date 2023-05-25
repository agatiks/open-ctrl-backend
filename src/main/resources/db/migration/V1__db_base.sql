create table kno
(
    id   bigserial primary key,
    name text not null
);

create table app_user
(
    id   text primary key,
    role text not null
);

create table business_user_info
(
    id         text primary key,
    first_name text,
    last_name  text,
    email      text,

    foreign key (id) references app_user (id)
);



create table address
(
    id    UUID primary key,
    place text not null
);

create table business
(
    id             UUID primary key,
    user_id        text not null,
    type           text not null,
    kind           text not null,
    object_class   text not null,
    activity_class text not null,
    address_id     UUID not null,

    foreign key (address_id) references address (id),
    foreign key (user_id) references app_user (id)
);

create table inspector_user_info
(
    id     text primary key,
    kno_id bigint not null,
    email  text,

    foreign key (id) references app_user (id),
    foreign key (kno_id) references kno (id)
);

create table measures
(
    id     bigserial primary key,
    name   text   not null,
    kno_id bigint not null,

    foreign key (kno_id) references kno (id)
);

create table appointments
(
    id               UUID primary key,
    appointment_time timestamp not null,
    kno_id           bigint    not null,
    measure_id       bigint,
    business_id      text,
    inspection_id    text,
    status           text      not null,

    unique (appointment_time, kno_id),

    foreign key (business_id) references app_user (id),
    foreign key (inspection_id) references app_user (id),
    foreign key (kno_id) references kno (id),
    foreign key (measure_id) references measures (id)
);