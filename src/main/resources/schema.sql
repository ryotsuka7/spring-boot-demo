drop table if exists item;

create table item (
  id integer,
  item_name varchar(20),
  primary key(id)
);

insert into item (id, item_name) values (1, '大豆');
insert into item (id, item_name) values (2, '小豆');
