insert into employees(first_name, last_name, phone_number, total_hours) values ('Adam','Nowak','508088807',80.00);
insert into employees(first_name, last_name, phone_number, total_hours) values ('Janusz','Kowalski','908020801',40.00);
insert into employees(first_name, last_name, phone_number, total_hours) values ('Grzegorz','Brzęczyszczykiewicz','908045801',15.00);
insert into employees(first_name, last_name, phone_number, total_hours) values ('Amadeusz','Mozard','655920801',00.00);
insert into employees(first_name, last_name, phone_number, total_hours) values ('Gal','Anonim','000000000',00.00);

insert into work_cards(name, total_hours) values ('karta pracy',80);
insert into work_cards(name, total_hours) values ('work card',15);
insert into work_cards(name, total_hours) values ('karta pracy dla 2 projektów',40);

insert into inscriptions(description, hours_spend, inscription_date, employee_id, work_card_id) VALUES ('zrobiono bazę danych i migracje',15,'2019-06-12 23:43:07',3,2);
insert into inscriptions(description, hours_spend, inscription_date, employee_id, work_card_id) VALUES ('zrobiono testy',40,'2019-06-12 23:43:07',1,1);
insert into inscriptions(description, hours_spend, inscription_date, employee_id, work_card_id) VALUES ('zrobiono mapowanie',20,'2019-06-12 23:43:07',1,1);
insert into inscriptions(description, hours_spend, inscription_date, employee_id, work_card_id) VALUES ('zrobiono wyjątki',20,'2019-06-12 23:43:07',2,1);
insert into inscriptions(description, hours_spend, inscription_date, employee_id, work_card_id) VALUES ('zrobiono podział na repo i servisy',20,'2019-06-12 23:43:07',1,3);
insert into inscriptions(description, hours_spend, inscription_date, employee_id, work_card_id) VALUES ('zrobiono dokumentacje',20,'2019-06-12 23:43:07',2,3);

insert into  projects(description, name, work_card_id) VALUES ('Rest api project','Communication',1);
insert into  projects(description, name, work_card_id) VALUES ('MVC project.','User experience project',2);
insert into  projects(description, name, work_card_id) VALUES ('SOAP project explaining...','SoapUI',3);
insert into  projects(description, name, work_card_id) VALUES ('JUNIT project development for...','JUNIT',null);

insert into employees_projects(employees_assigned_id, projects_id) VALUES (1,1);
insert into employees_projects(employees_assigned_id, projects_id) VALUES (1,3);
insert into employees_projects(employees_assigned_id, projects_id) VALUES (2,1);
insert into employees_projects(employees_assigned_id, projects_id) VALUES (2,3);
insert into employees_projects(employees_assigned_id, projects_id) VALUES (3,2);
