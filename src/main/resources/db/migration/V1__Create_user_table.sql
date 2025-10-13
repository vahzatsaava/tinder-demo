CREATE TABLE users
(
    id             VARCHAR(50) PRIMARY KEY,
    email          VARCHAR(255),
    password       VARCHAR(255),
    name           VARCHAR(255),
    age            BIGINT       NOT NULL,
    gender         VARCHAR(255),
    city           VARCHAR(255),
    bio            VARCHAR(255),
    status         VARCHAR(20)  not null,
    role           varchar(20)  not null,
    main_photo_url VARCHAR(255) not null
);