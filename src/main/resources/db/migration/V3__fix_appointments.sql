alter table appointments add column description text;

create table appointments_files(
    appointment_id UUID not null,
    file_id UUID not null,
    file_status text not null
);