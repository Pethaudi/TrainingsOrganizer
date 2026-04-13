DROP TABLE IF EXISTS CourseTrainers;
DROP TABLE IF EXISTS CourseRegisters;
DROP TABLE IF EXISTS Courses;
DROP TABLE IF EXISTS DogTeams;
DROP TABLE IF EXISTS Dogs;
DROP TABLE IF EXISTS Registered;
DROP TABLE IF EXISTS Appointments;

CREATE EXTENSION pgcrypto;

CREATE TABLE Registered (
  id SERIAL PRIMARY KEY,
  name TEXT NOT NULL,
  password TEXT NOT NULL
);

CREATE TABLE Dogs (
  id SERIAL PRIMARY KEY,
  name VARCHAR NOT NULL
);

CREATE TABLE DogTeams (
  handlerId INTEGER NOT NULL REFERENCES Registered(id),
  dogId INTEGER NOT NULL REFERENCES Dogs(id),
  PRIMARY KEY (handlerId, dogId)
);

CREATE TABLE Courses (
  id SERIAL PRIMARY KEY,
  name VARCHAR NOT NULL
);

CREATE TABLE CourseRegisters (
  id SERIAL PRIMARY KEY,
  courseId INTEGER NOT NULL REFERENCES Courses(id),
  handlerId INTEGER NOT NULL REFERENCES Registered(id),
  dogId INTEGER NOT NULL REFERENCES Dogs(id),
  FOREIGN KEY (handlerId, dogId) REFERENCES DogTeams (handlerId, dogId)
);

CREATE TABLE CourseTrainers (
  id SERIAL PRIMARY KEY,
  courseId INTEGER NOT NULL REFERENCES Courses(id),
  trainerId INTEGER NOT NULL REFERENCES Registered(id),
  UNIQUE (courseId, trainerId)
);

CREATE TABLE Appointments (
  id SERIAL PRIMARY KEY,
  relationId INTEGER REFERENCES Courses(id),
  date DATE,
  note VARCHAR
);

INSERT INTO Registered (name, password) VALUES ('Horst', crypt('Horst', 'md5'));
INSERT INTO Registered (name, password) VALUES ('Peter', crypt('Peter', 'md5'));
INSERT INTO Registered (name, password) VALUES ('Brigitte', crypt('Brigitte', 'md5'));
INSERT INTO Registered (name, password) VALUES ('Meli', crypt('Meli', 'md5'));

INSERT INTO Dogs (name) VALUES ('Günni');
INSERT INTO Dogs (name) VALUES ('Alba');
INSERT INTO Dogs (name) VALUES ('Ricco');

INSERT INTO DogTeams (handlerId, dogId)
  SELECT
    (SELECT id FROM Registered WHERE name = 'Horst'),
    (SELECT id FROM Dogs WHERE name = 'Günni');
INSERT INTO DogTeams (handlerId, dogId)
  SELECT
    (SELECT id FROM Registered WHERE name = 'Peter'),
    (SELECT id FROM Dogs WHERE name = 'Alba');
INSERT INTO DogTeams (handlerId, dogId)
  SELECT
    (SELECT id FROM Registered WHERE name = 'Meli'),
    (SELECT id FROM Dogs WHERE name = 'Ricco');

INSERT INTO Courses (name) VALUES ('Social Walk');
INSERT INTO Courses (name) VALUES ('Grundkurs');

INSERT INTO CourseRegisters (courseId, handlerId, dogId)
  SELECT
    (SELECT id FROM Courses WHERE name = 'Social Walk'),
    (SELECT id FROM Registered WHERE name = 'Horst'),
    (SELECT id FROM Dogs WHERE name = 'Günni');
INSERT INTO CourseRegisters (courseId, handlerId, dogId)
  SELECT
    (SELECT id FROM Courses WHERE name = 'Grundkurs'),
    (SELECT id FROM Registered WHERE name = 'Peter'),
    (SELECT id FROM Dogs WHERE name = 'Alba');
INSERT INTO CourseRegisters (courseId, handlerId, dogId)
  SELECT
    (SELECT id FROM Courses WHERE name = 'Grundkurs'),
    (SELECT id FROM Registered WHERE name = 'Meli'),
    (SELECT id FROM Dogs WHERE name = 'Ricco');

INSERT INTO CourseTrainers (courseId, trainerId)
  SELECT
    (SELECT id FROM Courses WHERE name = 'Social Walk'),
    (SELECT id FROM Registered WHERE name = 'Peter');
INSERT INTO CourseTrainers (courseId, trainerId)
  SELECT
    (SELECT id FROM Courses WHERE name = 'Grundkurs'),
    (SELECT id FROM Registered WHERE name = 'Peter');
INSERT INTO CourseTrainers (courseId, trainerId)
  SELECT
    (SELECT id FROM Courses WHERE name = 'Grundkurs'),
    (SELECT id FROM Registered WHERE name = 'Brigitte');

INSERT INTO Appointments (relationId, date, note)
  SELECT
    (SELECT id FROM Courses WHERE name = 'Social Walk'),
    '2026-04-12',
    'Treffpunkt Parkplatz Westeingang';
INSERT INTO Appointments (relationId, date, note)
  SELECT
    (SELECT id FROM Courses WHERE name = 'Grundkurs'),
    '2026-04-15',
    'Bitte Leckerlis mitbringen';
INSERT INTO Appointments (relationId, date, note)
  SELECT
    (SELECT id FROM Courses WHERE name = 'Grundkurs'),
    '2026-04-22',
    'Zweite Einheit - Leinenführigkeit';
