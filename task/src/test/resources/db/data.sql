-- data.sql

TRUNCATE TABLE cpu_usage;

INSERT INTO cpu_usage (timestamp, cpu_percentage)
VALUES ('2024-07-06 00:00:00', 10.0),
       ('2024-07-06 00:01:00', 30.0),
       ('2024-07-06 00:02:00', 80.0),
       ('2024-07-07 00:03:00', 15.0),
       ('2024-07-07 00:04:00', 30.0),
       ('2024-07-07 00:05:00', 35.0),
       ('2024-07-08 00:06:00', 45.0),
       ('2024-07-08 00:07:00', 15.0),
       ('2024-07-08 00:08:00', 20.0)
;
