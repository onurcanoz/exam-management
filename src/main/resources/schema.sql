create table if not exists exam_score_items
(
    id bigserial not null,
    score integer,
    valid boolean,
    exam_score_id bigint not null,
    question_id bigint not null,
    answer_id bigint not null,
    primary key (id)
);

create table if not exists exam_scores
(
    id bigserial not null,
    score integer,
    exam_id bigint not null,
    student_id bigint not null,
    created_at timestamp(6),
    primary key (id)
);

create table if not exists exams
(
    id bigserial not null,
    name varchar(255),
    max_score integer,
    valid boolean,
    created_at timestamp(6),
    updated_at timestamp(6),
    primary key (id)
);

create table if not exists question_items
(
    id bigserial not null,
    tag varchar(255),
    text varchar(255),
    valid boolean,
    exam_id bigint not null,
    question_id bigint not null,
    primary key (id)
);

create table if not exists questions
(
    id bigserial not null,
    text varchar(255),
    point integer,
    exam_id bigint not null,
    created_at timestamp(6),
    updated_at timestamp(6),
    primary key (id)
);

create table if not exists student_exam
(
    student_id bigint not null,
    exam_id bigint not null,
    primary key (student_id, exam_id)
);

create table if not exists students
(
    id bigserial not null,
    first_name varchar(255),
    last_name varchar(255),
    identity_number varchar(255),
    primary key (id)
);
