-- TABLES
-- player, game

-- SCHEMA
CREATE SCHEMA IF NOT EXISTS imdb;
SET SCHEMA imdb;

-- DROPS
DROP TABLE IF EXISTS "imdb"."players";
DROP TABLE IF EXISTS "imdb"."movies";

-- player
CREATE CACHED TABLE "imdb"."players"(
    "id_player" BIGINT NOT NULL,
    "name" VARCHAR(30) NOT NULL,
    "username" VARCHAR(12) NOT NULL,
    "email" VARCHAR(30) NOT NULL,
    "date_of_birth" timestamp
);
ALTER TABLE "imdb"."players" ADD CONSTRAINT "imdb"."constraint_id_player" PRIMARY KEY("id_player");

-- movies
CREATE CACHED TABLE "imdb"."movies"(
    "id_movie" BIGINT NOT NULL,
    "name" VARCHAR(30) NOT NULL
);