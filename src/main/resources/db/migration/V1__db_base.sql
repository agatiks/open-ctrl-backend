create table if not exists app_user (
    id bigserial primary key,
    user_id text not null unique,
    role text not null
);

create table if not exists user_info (
    id bigserial primary key,
    user_id text not null,
    first_name text,
    last_name text,
    email text,
    payload jsonb,

    foreign key(user_id) references app_user(user_id)
);

create table if not exists kno (
    id bigserial primary key,
    name text not null
);

create table if not exists measures (
    id bigserial primary key,
    name text not null,
    kno_id bigint not null,

    foreign key(kno_id) references kno(id)
);

create table if not exists appointments (
    id bigserial primary key,
    appointment_time timestamp not null,
    kno_id bigint not null,
    measure_id bigint,
    business_id text,
    inspection_id text,
    status text not null,

    foreign key(business_id) references app_user(user_id),
    foreign key(inspection_id) references app_user(user_id),
    foreign key(kno_id) references kno(id),
    foreign key(measure_id) references measures(id)
);