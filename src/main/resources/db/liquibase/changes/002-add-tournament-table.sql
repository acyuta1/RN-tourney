--liquibase formatted sql

--changeset acyuta1:002-add-tournament-table

create table tourney.tournament (
                       id bigint identity not null primary key,
                       tournament_name varchar(255) not null,
                       atp_type varchar(255) not null,
                       latest_winner varchar(255) null,
                       recent_year date null,
                       updated_at datetime null,
                       constraint tournament_tournament_name_unique unique (tournament_name)
);

INSERT INTO tourney.tournament(tournament_name, atp_type) values ('Rolland Garros - French Open','GRAND_SLAM');
INSERT INTO tourney.tournament(tournament_name, atp_type) values ('Wimbledon Open','GRAND_SLAM');
INSERT INTO tourney.tournament(tournament_name, atp_type) values ('Australian Open','GRAND_SLAM');
INSERT INTO tourney.tournament(tournament_name, atp_type) values ('US Open','GRAND_SLAM');
INSERT INTO tourney.tournament(tournament_name, atp_type) values ('Indian Wells Masters','ATP1000');
INSERT INTO tourney.tournament(tournament_name, atp_type) values ('Miami Open','ATP1000');
INSERT INTO tourney.tournament(tournament_name, atp_type) values ('Monte-Carlo Masters','ATP1000');
INSERT INTO tourney.tournament(tournament_name, atp_type) values ('Madrid Open','ATP1000');
INSERT INTO tourney.tournament(tournament_name, atp_type) values ('Italian Open','ATP1000');
INSERT INTO tourney.tournament(tournament_name, atp_type) values ('Canadian Open','ATP1000');
INSERT INTO tourney.tournament(tournament_name, atp_type) values ('Cincinnati Masters','ATP1000');
INSERT INTO tourney.tournament(tournament_name, atp_type) values ('Shanghai Masters','ATP1000');
INSERT INTO tourney.tournament(tournament_name, atp_type) values ('Paris Masters','ATP1000');
INSERT INTO tourney.tournament(tournament_name, atp_type) values ('Rotterdam Open','ATP500');
INSERT INTO tourney.tournament(tournament_name, atp_type) values ('Rio Open','ATP500');
INSERT INTO tourney.tournament(tournament_name, atp_type) values ('Dubai Tennis Championships','ATP500');
INSERT INTO tourney.tournament(tournament_name, atp_type) values ('Mexican Open','ATP500');
INSERT INTO tourney.tournament(tournament_name, atp_type) values ('Barcelona Open','ATP500');
INSERT INTO tourney.tournament(tournament_name, atp_type) values ('Queen''s Club Championships','ATP500');
INSERT INTO tourney.tournament(tournament_name, atp_type) values ('Halle Open','ATP500');
INSERT INTO tourney.tournament(tournament_name, atp_type) values ('Hamburg European Open','ATP500');
INSERT INTO tourney.tournament(tournament_name, atp_type) values ('Washington Open','ATP500');
INSERT INTO tourney.tournament(tournament_name, atp_type) values ('China Open','ATP500');
INSERT INTO tourney.tournament(tournament_name, atp_type) values ('Japan Open','ATP500');
INSERT INTO tourney.tournament(tournament_name, atp_type) values ('Swiss Open','ATP500');
INSERT INTO tourney.tournament(tournament_name, atp_type) values ('Vienna Open','ATP500');
