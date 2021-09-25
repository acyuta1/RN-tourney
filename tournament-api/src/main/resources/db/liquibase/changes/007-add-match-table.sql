--liquibase formatted sql

--changeset acyuta1:007-add-match-table

create table tourney.match (
                               id bigint identity not null primary key,
                               round_id bigint not null constraint fk_match_round references tourney.round,
                               match_status varchar(255) not null,
                               player_one varchar(255) null,
                               player_two varchar(255) null,
                               result_of_match varchar(255) null
);