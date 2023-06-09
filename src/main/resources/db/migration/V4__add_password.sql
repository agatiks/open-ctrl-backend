alter table app_user add column login text not null,
    add column password text,
    add column lastTimeLoggedIn timestamp;

