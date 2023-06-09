alter table app_user add column login text not null,
    add column password text not null,
    add column lastTimeLoggedIn timestamp;

