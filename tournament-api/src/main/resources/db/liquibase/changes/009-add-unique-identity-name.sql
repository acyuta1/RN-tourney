--liquibase formatted sql

--changeset teejihba:009-add-unique-identity-name.sql

alter table tourney.tournament add unique_identity_name varchar(255);
