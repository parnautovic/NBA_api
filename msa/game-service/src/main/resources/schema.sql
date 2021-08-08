drop table if exists play_player cascade;
drop table if exists play cascade;
drop table if exists game cascade;
drop table if exists player cascade;

create table player(
    player_id serial not null primary key,
    external_id int not null unique,
    first_name varchar(20) not null,
    last_name varchar(20) not null
);

create table game (
    id serial not null primary key,
    game_id char(14) not null,
    home_team varchar(255) not null,
    away_team varchar(255) not null,
    status varchar(255) not null,
    game_time int not null
);

create table play (
    play_id serial not null primary key,
    description varchar(255) not null,
    team varchar(255) not null,
    time varchar(10) not null,
    game_id bigint not null,
    quarter_no int not null,
    home_team varchar(255) not null,
    away_team varchar(255) not null,
    home_score int not null,
    away_score int not null,

    foreign key (game_id) references game(id)
);

create table play_player(
    play_player_id serial not null primary key,
    play_id bigint not null,
    player_id bigint not null,

    foreign key (play_id) references play(play_id),
    foreign key (player_id) references player(player_id)
);

