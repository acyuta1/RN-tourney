--liquibase formatted sql

--changeset acyuta1:006-add-round-value-column-to-round-table.sql

alter table tourney.round add round_value int;