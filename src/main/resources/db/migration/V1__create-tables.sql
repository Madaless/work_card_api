create table if not exists employees
(
    id bigserial not null
        constraint employees_pkey
            primary key,
    first_name varchar(255),
    last_name varchar(255),
    phone_number varchar(255),
    total_hours numeric(19,2)
);

create table if not exists work_cards
(
    id bigserial not null
        constraint work_cards_pkey
            primary key,
    name varchar(255),
    total_hours numeric(19,2)
);

create table if not exists inscriptions
(
    id bigserial not null
        constraint inscriptions_pkey
            primary key,
    description varchar(255),
    hours_spend numeric(19,2),
    inscription_date timestamp,
    employee_id bigint
        constraint fkd5tblbjoikhtg2298avvbtqht
            references employees,
    work_card_id bigint
        constraint fkljp6p8osq642r0645ebfq4n4k
            references work_cards
);

create table if not exists projects
(
    id bigserial not null
        constraint projects_pkey
            primary key,
    description varchar(255),
    name varchar(255),
    work_card_id bigint
        constraint fkcmcrjtb5jcotvwrbcm6mx3lvb
            references work_cards
);

create table if not exists employees_projects
(
    employees_assigned_id bigint not null
        constraint fk3vm18xlxyku8ley5pl6woapjj
            references employees,
    projects_id bigint not null
        constraint fkp72i1mp9782wq0tro1m7ldy5t
            references projects,
    constraint employees_projects_pkey
        primary key (employees_assigned_id, projects_id)
);

