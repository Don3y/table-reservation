insert into user (username, password, enabled, role) values ('admin', '21232f297a57a5a743894a0e4a801fc3', true, 'ROLE_ADMIN');-- password: admin
insert into user (username, password, enabled, role) values ('user', 'ee11cbb19052e40b07aac0ca060c23ee', true, 'ROLE_USER');-- password: user

insert into label (id,text, created_at, updated_at) values (1,'Smoking', CURRENT_TIMESTAMP(),  CURRENT_TIMESTAMP());
insert into label (id,text, created_at, updated_at) values (2,'VIP', CURRENT_TIMESTAMP(),  CURRENT_TIMESTAMP());
insert into label (id,text, created_at, updated_at) values (3,'Outdoor', CURRENT_TIMESTAMP(),  CURRENT_TIMESTAMP());

insert into resturant_table(id,status,tablename) values (1,'FREE','Table for 2');
insert into resturant_table(id,status,tablename) values (2,'FREE','Table for 2');
insert into resturant_table(id,status,tablename) values (3,'FREE','Table for 4');
insert into resturant_table(id,status,tablename) values (4,'FREE','Table for 4');
insert into resturant_table(id,status,tablename) values (5,'FREE','Table for 8');

insert into resturant_table_labels (tables_id,labels_id) values (1,1);
insert into resturant_table_labels (tables_id,labels_id) values (1,3);
insert into resturant_table_labels (tables_id,labels_id) values (2,2);
insert into resturant_table_labels (tables_id,labels_id) values (3,2);
insert into resturant_table_labels (tables_id,labels_id) values (3,3);
insert into resturant_table_labels (tables_id,labels_id) values (4,1);
insert into resturant_table_labels (tables_id,labels_id) values (5,1);
insert into resturant_table_labels (tables_id,labels_id) values(5,2);

