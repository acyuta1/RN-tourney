--liquibase formatted sql

--changeset teejihba:011-change-data-type-of-players.sql

ALTER TABLE tourney.match
    ADD playerOne int;

ALTER TABLE tourney.match
    ADD playerTwo int;