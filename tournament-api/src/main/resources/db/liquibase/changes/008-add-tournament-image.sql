--liquibase formatted sql

--changeset acyuta1:008-add-tournament-image.sql

alter table tourney.tournament add image_unique_name varchar(255);