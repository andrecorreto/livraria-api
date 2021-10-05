alter table livros add column autores_id bigint not null;
alter table livros add foreign key(autores_id) references autores(id);