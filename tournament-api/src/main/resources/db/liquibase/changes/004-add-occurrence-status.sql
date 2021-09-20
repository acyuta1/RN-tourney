--liquibase formatted sql

--changeset acyuta1:004-add-occurrence-status

alter table tourney.occurrence add occurrence_status varchar(90);