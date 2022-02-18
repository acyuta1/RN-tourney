--liquibase formatted sql

--changeset teejihba:010-add-player-table.sql

create table tourney.player (
                                    id bigint identity not null primary key,
                                    full_name varchar(255) not null,
                                    country varchar(255) not null,
                                    rank int null,
                                    client_id bigint not null,
                                    updated_at datetime null
);
