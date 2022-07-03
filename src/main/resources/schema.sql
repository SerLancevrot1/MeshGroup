create table if not exists public.users(
    id bigserial not null,
    name varchar not null,
    age bigint not null ,
    email varchar unique not null,
    constraint users_pk primary key(id)
);

create table if not exists  public.profiles(
    id bigserial not null,
    cash numeric not null default 0,
    user_id bigserial constraint profiles_pk references users
);

create table if not exists  public.phones(
    id bigserial not null,
    value varchar unique not null,
    user_id bigserial constraint profiles_pk references users
);