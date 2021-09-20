--liquibase formatted sql

--changeset acyuta1:005-add-round-table

create table tourney.round (
                                    id bigint identity not null primary key,
                                    occurrence_id bigint not null constraint fk_occurrence_round references tourney.occurrence,
                                    round_type varchar(255) not null,
                                    round_status varchar(255) not null
);