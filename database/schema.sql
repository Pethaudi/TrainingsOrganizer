DROP TABLE IF EXISTS CourseTrainers;
DROP TABLE IF EXISTS CourseRegisters;
DROP TABLE IF EXISTS Courses;
DROP TABLE IF EXISTS DogTeams;
DROP TABLE IF EXISTS Dogs;
DROP TABLE IF EXISTS Registered;
DROP TABLE IF EXISTS Appointments;

DROP TABLE IF EXISTS Organisations;
DROP TABLE IF EXISTS MembersOfOrganisation;
DROP TABLE IF EXISTS CoursesOfOrganisation;
DROP TYPE IF EXISTS OrganisationRole;

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
  id SERIAL PRIMARY KEY,
  handlerId INTEGER NOT NULL REFERENCES Registered(id),
  dogId INTEGER NOT NULL REFERENCES Dogs(id),
  UNIQUE (handlerId, dogId)
);

CREATE TABLE Courses (
  id SERIAL PRIMARY KEY,
  name VARCHAR NOT NULL
);

CREATE TABLE CourseRegisters (
  id SERIAL PRIMARY KEY,
  courseId INTEGER NOT NULL REFERENCES Courses(id),
  dogTeamId INTEGER NOT NULL REFERENCES DogTeams(id)
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

CREATE TABLE ORGANISATIONS (
  id SERIAL PRIMARY KEY,
  name VARCHAR,
  address VARCHAR
);

CREATE TYPE OrganisationRole AS ENUM ('admin', 'trainer', 'member');

CREATE TABLE MembersOfOrganisation (
  id SERIAL PRIMARY KEY,
  registeredId INTEGER REFERENCES Registered(id),
  organisationId INTEGER REFERENCES Organisations(id),
  role OrganisationRole NOT NULL DEFAULT 'member',
  UNIQUE (registeredId, organisationId)
);

CREATE TABLE CoursesOfOrganisation (
  id SERIAL PRIMARY KEY,
  courseId INTEGER REFERENCES Courses(id),
  organisationId INTEGER REFERENCES Organisations(id),
  UNIQUE (courseId, organisationId)
);
