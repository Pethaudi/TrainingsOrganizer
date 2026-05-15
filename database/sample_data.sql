-- Sample data for TrainingsOrganizer
-- Run this after schema.sql (or init.sql without inserts).

-- ============================================================
-- Organisations (3)
-- ============================================================
INSERT INTO Organisations (name, address) VALUES
  ('HSS Vöcklabruck',       'Gegenüber vom Hellweg, 4840 Vöcklabruck'),
  ('Hundesportverein Wien',  'Prater Hauptallee 42, 1020 Wien'),
  ('Agility Club Salzburg',  'Alpenstraße 88, 5020 Salzburg');

-- ============================================================
-- Registered users
--   IDs  1–5   trainers of org 1 (HSS Vöcklabruck)
--   IDs  6–10  trainers of org 2 (Hundesportverein Wien)
--   IDs 11–15  trainers of org 3 (Agility Club Salzburg)
--   IDs 16–25  dog-team handlers (members)
-- ============================================================
INSERT INTO Registered (name, password) VALUES
  -- Org 1 trainers
  ('Sarah',    md5('sarah123')),
  ('James',    md5('james123')),
  ('Emma',     md5('emma123')),
  ('Lucas',    md5('lucas123')),
  ('Sophie',   md5('sophie123')),
  -- Org 2 trainers
  ('Daniel',   md5('daniel123')),
  ('Olivia',   md5('olivia123')),
  ('Ryan',     md5('ryan123')),
  ('Isabella', md5('isabella123')),
  ('Noah',     md5('noah123')),
  -- Org 3 trainers
  ('Mia',      md5('mia123')),
  ('Ethan',    md5('ethan123')),
  ('Chloe',    md5('chloe123')),
  ('Liam',     md5('liam123')),
  ('Zoe',      md5('zoe123')),
  -- Handlers
  ('Jack',     md5('jack123')),
  ('Emily',    md5('emily123')),
  ('Sam',      md5('sam123')),
  ('Laura',    md5('laura123')),
  ('Ben',      md5('ben123')),
  ('Amy',      md5('amy123')),
  ('Tom',      md5('tom123')),
  ('Rachel',   md5('rachel123')),
  ('Chris',    md5('chris123')),
  ('Kate',     md5('kate123'));

-- ============================================================
-- MembersOfOrganisation (trainers + handlers with roles)
-- ============================================================
INSERT INTO MembersOfOrganisation (registeredId, organisationId, role) VALUES
  -- Org 1: first trainer is admin, rest are trainers, handlers are members
  (1,  1, 'admin'),
  (2,  1, 'trainer'), (3,  1, 'trainer'), (4,  1, 'trainer'), (5,  1, 'trainer'),
  (16, 1, 'member'),  (17, 1, 'member'),  (23, 1, 'member'),  (25, 1, 'member'),
  -- Org 2
  (6,  2, 'admin'),
  (7,  2, 'trainer'), (8,  2, 'trainer'), (9,  2, 'trainer'), (10, 2, 'trainer'),
  (18, 2, 'member'),  (19, 2, 'member'),  (20, 2, 'member'),  (24, 2, 'member'),
  -- Org 3
  (11, 3, 'admin'),
  (12, 3, 'trainer'), (13, 3, 'trainer'), (14, 3, 'trainer'), (15, 3, 'trainer'),
  (21, 3, 'member'),  (22, 3, 'member');

-- ============================================================
-- Dogs (one per handler, IDs 1–10 matching handler offsets)
-- ============================================================
INSERT INTO Dogs (name) VALUES
  ('Rex'),    -- 1  → Hans Bauer   (16)
  ('Luna'),   -- 2  → Gabi Steiner (17)
  ('Bello'),  -- 3  → Otto Fuchs   (18)
  ('Lassie'), -- 4  → Renate Eder  (19)
  ('Max'),    -- 5  → Franz Huber  (20)
  ('Zara'),   -- 6  → Helga Pauer  (21)
  ('Bruno'),  -- 7  → Inge Wolf    (22)
  ('Nala'),   -- 8  → Rudi Mayer   (23)
  ('Leo'),    -- 9  → Claudia Braun(24)
  ('Coco');   -- 10 → Werner Kern  (25)

-- ============================================================
-- DogTeams
-- ============================================================
INSERT INTO DogTeams (handlerId, dogId) VALUES
  (16, 1),   -- 1  Hans   + Rex
  (17, 2),   -- 2  Gabi   + Luna
  (18, 3),   -- 3  Otto   + Bello
  (19, 4),   -- 4  Renate + Lassie
  (20, 5),   -- 5  Franz  + Max
  (21, 6),   -- 6  Helga  + Zara
  (22, 7),   -- 7  Inge   + Bruno
  (23, 8),   -- 8  Rudi   + Nala
  (24, 9),   -- 9  Claudia+ Leo
  (25, 10);  -- 10 Werner + Coco

-- ============================================================
-- Courses (5 per organisation)
--   IDs  1–5   → org 1
--   IDs  6–10  → org 2
--   IDs 11–15  → org 3
-- ============================================================
INSERT INTO Courses (name) VALUES
  -- Org 1
  ('Grundkurs A'),           -- 1
  ('Grundkurs B'),           -- 2
  ('Social Walk'),           -- 3
  ('Agility Anfänger'),      -- 4
  ('Rally Obedience'),       -- 5
  -- Org 2
  ('Welpengruppe'),          -- 6
  ('Junghundekurs'),         -- 7
  ('Begleithund BH'),        -- 8
  ('Fährte Anfänger'),       -- 9
  ('Schutzdienst Übung'),    -- 10
  -- Org 3
  ('Agility Starter'),       -- 11
  ('Agility Advanced'),      -- 12
  ('Turnierhundsport'),      -- 13
  ('Flyball Basics'),        -- 14
  ('Disc Dog Intro');        -- 15

-- ============================================================
-- CoursesOfOrganisation
-- ============================================================
INSERT INTO CoursesOfOrganisation (courseId, organisationId) VALUES
  -- Org 1
  (1,  1), (2,  1), (3,  1), (4,  1), (5,  1),
  -- Org 2
  (6,  2), (7,  2), (8,  2), (9,  2), (10, 2),
  -- Org 3
  (11, 3), (12, 3), (13, 3), (14, 3), (15, 3);

-- ============================================================
-- CourseTrainers
-- ============================================================
INSERT INTO CourseTrainers (courseId, trainerId) VALUES
  -- Org 1 courses
  (1,  1), (1,  2),   -- Grundkurs A:       Anna, Thomas
  (2,  2), (2,  3),   -- Grundkurs B:       Thomas, Eva
  (3,  3), (3,  5),   -- Social Walk:       Eva, Sandra
  (4,  4),            -- Agility Anfänger:  Markus
  (5,  5), (5,  1),   -- Rally Obedience:  Sandra, Anna
  -- Org 2 courses
  (6,  6), (6,  7),   -- Welpengruppe:      Michael, Lisa
  (7,  7),            -- Junghundekurs:     Lisa
  (8,  8), (8,  9),   -- Begleithund BH:    Stefan, Petra
  (9,  9), (9,  10),  -- Fährte Anfänger:   Petra, Klaus
  (10, 10),(10, 6),   -- Schutzdienst:      Klaus, Michael
  -- Org 3 courses
  (11, 11),(11, 12),  -- Agility Starter:   Julia, Florian
  (12, 12),(12, 13),  -- Agility Advanced:  Florian, Maria
  (13, 14),           -- Turnierhundsport:  Andreas
  (14, 15),(14, 11),  -- Flyball Basics:    Sabine, Julia
  (15, 13),(15, 14);  -- Disc Dog Intro:    Maria, Andreas

-- ============================================================
-- CourseRegisters (each dog team in at most one course)
-- ============================================================
INSERT INTO CourseRegisters (courseId, dogTeamId) VALUES
  (1,  1),   -- Grundkurs A       ← Hans + Rex
  (3,  2),   -- Social Walk       ← Gabi + Luna
  (6,  3),   -- Welpengruppe      ← Otto + Bello
  (7,  4),   -- Junghundekurs     ← Renate + Lassie
  (8,  5),   -- Begleithund BH    ← Franz + Max
  (11, 6),   -- Agility Starter   ← Helga + Zara
  (12, 7),   -- Agility Advanced  ← Inge + Bruno
  (2,  8),   -- Grundkurs B       ← Rudi + Nala
  (13, 9),   -- Turnierhundsport  ← Claudia + Leo
  (5,  10);  -- Rally Obedience   ← Werner + Coco

-- ============================================================
-- Appointments (2 per course)
-- ============================================================
INSERT INTO Appointments (relationId, date, note) VALUES
  -- Org 1
  (1,  '2026-04-20', 'Erste Einheit – Grundkommandos'),
  (1,  '2026-04-27', 'Zweite Einheit – Leinenführigkeit'),
  (2,  '2026-04-21', 'Erste Einheit – Sitz und Platz'),
  (2,  '2026-04-28', 'Zweite Einheit – Abruf'),
  (3,  '2026-04-19', 'Treffpunkt Parkplatz Westeingang'),
  (3,  '2026-05-03', 'Treffpunkt beim Brunnen im Park'),
  (4,  '2026-04-22', 'Bitte Futter statt Leckerlis mitbringen'),
  (4,  '2026-04-29', 'Hindernis: Tunnel und Slalom'),
  (5,  '2026-04-23', 'Erste Übungseinheit Rally'),
  (5,  '2026-04-30', 'Parcours Übung mit Zeitnahme'),
  -- Org 2
  (6,  '2026-04-17', 'Erste Welpengruppe – Sozialisation'),
  (6,  '2026-04-24', 'Zweite Einheit – Spielverhalten'),
  (7,  '2026-04-18', 'Junghundekurs – Grundlgehorsam'),
  (7,  '2026-04-25', 'Junghundekurs – Ablenkungstraining'),
  (8,  '2026-04-20', 'BH-Vorbereitung Unterordnung'),
  (8,  '2026-04-27', 'BH-Vorbereitung Verkehrsteil'),
  (9,  '2026-04-22', 'Fährte legen – Trockenwiese'),
  (9,  '2026-04-29', 'Fährte mit Gegenstand'),
  (10, '2026-04-24', 'Schutzdienst – Reviersuche'),
  (10, '2026-05-01', 'Schutzdienst – Angriffstraining'),
  -- Org 3
  (11, '2026-04-19', 'Agility Starter – Tunneleinführung'),
  (11, '2026-04-26', 'Agility Starter – Sprung und Wippe'),
  (12, '2026-04-20', 'Advanced – Sequenztraining'),
  (12, '2026-04-27', 'Advanced – Wettkampfparcours'),
  (13, '2026-04-21', 'Turnierhundsport – Unterordnung'),
  (13, '2026-04-28', 'Turnierhundsport – Vierkampf'),
  (14, '2026-04-22', 'Flyball – Boxtraining'),
  (14, '2026-04-29', 'Flyball – Staffellauf'),
  (15, '2026-04-23', 'Disc Dog – Einführung Werftechnik'),
  (15, '2026-04-30', 'Disc Dog – Fangübungen auf Distanz');
