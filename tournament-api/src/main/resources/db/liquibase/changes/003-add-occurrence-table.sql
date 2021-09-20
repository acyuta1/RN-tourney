--liquibase formatted sql

--changeset acyuta1:003-add-occurrence-table

create table tourney.occurrence (
                                    id bigint identity not null primary key,
                                    tournament_id bigint not null constraint fk_occurrence_tournament references tourney.tournament,
                                    occurrence_date date null,
                                    winner_id datetime null,
                                    runner_up_id datetime null,
);