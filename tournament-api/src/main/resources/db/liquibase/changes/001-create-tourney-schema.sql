--liquibase formatted sql

--changeset acyuta1:001-create-tourney-schema

if (schema_id('tourney') is null)
    begin
    exec ('create schema [tourney] authorization [dbo]')
    end