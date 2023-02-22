INSERT INTO user (user_id, birthday, email, name, nickname, password)
VALUES (1, '1999-12-18', 'test1@gamil.com', '테스터1', '테스터1별명', 12345678);

INSERT INTO user (user_id, birthday, email, name, nickname, password)
VALUES (2, '1998-12-18', 'test2@gamil.com', '테스터2', '테스터2별명', 12345678);

INSERT INTO user (user_id, birthday, email, name, nickname, password)
VALUES (3, '1997-12-18', 'test3@gamil.com', '테스터3', '테스터3별명', 12345678);

INSERT INTO owner (owner_id, birthday, email, name, nickname, password)
VALUES (1, '1980-01-01', 'owner1@gmail.com', '테스터오너1', '테스터오너1별명', '12345678');

INSERT INTO owner (owner_id, birthday, email, name, nickname, password)
VALUES (2, '1990-01-01', 'owner2@gmail.com', '테스터오너2', '테스터오너2별명', '12345678');

INSERT INTO study_cafe (study_cafe_id, address, close_time, comment, min_using_time, name, open_time, owner_id)
VALUES (1, '경기도 남양주시 진접읍 금강로 1530-12', '22:00:00', '상세 설명', 1, '테스트 스터디카페1', '10:00:00', 1);

INSERT INTO study_cafe (study_cafe_id, address, close_time, comment, min_using_time, name, open_time, owner_id)
VALUES (2, '경기도 남양주시 진접읍 금강로 1530-13', '20:00:00', '상세 설명', 2, '테스트 스터디카페2', '08:00:00', 2);

INSERT INTO room (room_id, head_count, price, study_cafe_id)
VALUES (4, 4, 5000, 2);

INSERT INTO room (room_id, head_count, price, study_cafe_id)
VALUES (5, 4, 5000, 2);

INSERT INTO room (room_id, head_count, price, study_cafe_id)
VALUES (6, 6, 8000, 2);

INSERT INTO room (room_id, head_count, price, study_cafe_id)
VALUES (1, 4, 4000, 1);

INSERT INTO room (room_id, head_count, price, study_cafe_id)
VALUES (2, 4, 4000, 1);

INSERT INTO room (room_id, head_count, price, study_cafe_id)
VALUES (3, 6, 6000, 1);

INSERT INTO reservation (reservation_id, end_time, reservation_status, start_time, room_id, study_cafe_id, user_id)
VALUES (1, '2023-02-28 16:00:00', 'RESERVED', '2023-02-28 14:00:00', 1, 1, 1);

INSERT INTO reservation (reservation_id, end_time, reservation_status, start_time, room_id, study_cafe_id, user_id)
VALUES (2, '2023-03-01 18:00:00', 'RESERVED', '2023-03-01 15:00:00', 5, 2, 2);