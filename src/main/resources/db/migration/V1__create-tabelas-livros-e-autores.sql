create table livros (
	id bigint not null auto_increment,
	titulo varchar(100) not null,
	data_de_lancamento date not null,
	numero_de_paginas int not null,
	primary key(id)
);

create table autores (
	id bigint not null auto_increment,
	nome varchar(100) not null,
	email varchar(100) not null,
	data_de_nascimento date not null,
	curriculo varchar(150) not null,
	primary key(id)
);