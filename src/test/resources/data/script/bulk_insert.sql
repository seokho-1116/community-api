COPY community FROM '/tmp/script/community_data.csv' DELIMITER ',' CSV HEADER;
COPY board FROM '/tmp/script/board_data.csv' DELIMITER ',' CSV HEADER;
COPY member FROM '/tmp/script/member_data.csv' DELIMITER ',' CSV HEADER;
COPY post_category FROM '/tmp/script/post_category_data.csv' DELIMITER ',' CSV HEADER;
COPY post FROM '/tmp/script/post_data.csv' DELIMITER ',' CSV HEADER;
COPY comment FROM '/tmp/script/comment_data.csv' DELIMITER ',' CSV HEADER;
COPY image FROM '/tmp/script/image_data.csv' DELIMITER ',' CSV HEADER;
COPY public_chat_room FROM '/tmp/script/public_chat_room_data.csv' DELIMITER ',' CSV HEADER;
COPY chat_message FROM '/tmp/script/chat_message_data.csv' DELIMITER ',' CSV HEADER;
COPY draft_post FROM '/tmp/script/draft_post_data.csv' DELIMITER ',' CSV HEADER;
COPY video FROM '/tmp/script/video_data.csv' DELIMITER ',' CSV HEADER;
COPY message FROM '/tmp/script/message_data.csv' DELIMITER ',' CSV HEADER;
