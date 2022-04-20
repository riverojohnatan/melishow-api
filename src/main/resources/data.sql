INSERT INTO "show" ("name") VALUES ('TEST 1');
INSERT INTO "show" ("name") VALUES ('TEST 2');
INSERT INTO "show" ("name") VALUES ('TEST 3');

INSERT INTO seat (show_id,seat_price,"row",seat_numbers) VALUES
    (1,100,'A','1,2,3,4,5,6'),
    (1,150,'B','1,3,4,6'),
    (1,300,'C','3,7,8,9'),
    (2,10,'A','3,4,5,6'),
    (2,15,'B','1,2,3,4,6'),
    (2,30,'C','7,8,9'),
    (3,200,'A','1,2,3,4,5,6'),
    (3,350,'B','1,3,4,6'),
    (3,400,'C','3,7,8,9');