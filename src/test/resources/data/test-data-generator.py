from faker import Faker
import uuid
import random
import os
import csv
import subprocess
from datetime import datetime, timezone

fake = Faker()

def to_offsetdatetime_format(dt):
    formatted_date = dt.astimezone(timezone.utc).strftime('%Y-%m-%dT%H:%M:%S.%f')
    offset = dt.astimezone(timezone.utc).strftime('%z')
    formatted_offset = offset[:3] + ':' + offset[3:]
    return formatted_date + formatted_offset

def generate_community_data():
    return {
        "id": uuid.uuid4(),
        "public_id": uuid.uuid4(),
        "introduction": fake.text(),
        "posts_count": random.randint(0, 10000),
        "members_count": random.randint(0, 1000),
        "contact_info": fake.phone_number(),
        "privacy_policy": fake.text(),
        "terms": fake.text(),
        "ads_info": fake.text(),
        "company_info": fake.company()
    }

def generate_board_data(community_id):
    return {
        "id": uuid.uuid4(),
        "public_id": uuid.uuid4(),
        "name": fake.word(),
        "created_date": to_offsetdatetime_format(fake.date_time_this_decade(tzinfo=None)),
        "description": fake.text(),
        "community_id": community_id
    }

def generate_member_data():
    return {
        "id": uuid.uuid4(),
        "public_id": uuid.uuid4(),
        "nickname": fake.user_name(),
        "created_date": to_offsetdatetime_format(fake.date_time_this_decade(tzinfo=None)),
        "modified_date": to_offsetdatetime_format(fake.date_time_this_decade(tzinfo=None)),
        "signup_id": fake.uuid4(),
        "signup_password": fake.password(),
        "email": fake.email(),
        "expiration_date": to_offsetdatetime_format(fake.date_time_this_decade(tzinfo=None)),
        "account_locked": random.choice([True, False]),
        "role": random.choice(["USER", "ADMIN"])
    }

def generate_post_category_data(board_id):
    return {
        "id": uuid.uuid4(),
        "public_id": uuid.uuid4(),
        "name": fake.word(),
        "description": fake.text(),
        "board_id": board_id
    }

def generate_post_data(board_id, post_category_id, member_id):
    return {
        "id": uuid.uuid4(),
        "public_id": uuid.uuid4(),
        "title": fake.sentence(),
        "content": fake.text(),
        "created_date": to_offsetdatetime_format(fake.date_time_this_decade(tzinfo=None)),
        "modified_date": to_offsetdatetime_format(fake.date_time_this_decade(tzinfo=None)),
        "views_count": random.randint(0, 10000),
        "up_votes_count": random.randint(0, 1000),
        "down_votes_count": random.randint(0, 100),
        "is_featured": random.choice([True, False]),
        "post_url": fake.url(),
        "board_id": board_id,
        "post_category_id": post_category_id,
        "member_id": member_id
    }

def generate_comment_data(post_id, member_id):
    return {
        "id": uuid.uuid4(),
        "public_id": uuid.uuid4(),
        "title": fake.sentence(),
        "content": fake.text(),
        "created_date": to_offsetdatetime_format(fake.date_time_this_decade(tzinfo=None)),
        "modified_date": to_offsetdatetime_format(fake.date_time_this_decade(tzinfo=None)),
        "up_votes_count": random.randint(0, 1000),
        "down_votes_count": random.randint(0, 100),
        "post_id": post_id,
        "member_id": member_id
    }

def generate_image_data(post_id, member_id):
    return {
        "id": uuid.uuid4(),
        "public_id": uuid.uuid4(),
        "url": fake.image_url(),
        "size": random.randint(1000, 5000),
        "uploaded_date": to_offsetdatetime_format(fake.date_time_this_decade(tzinfo=None)),
        "extension": fake.file_extension(),
        "post_id": post_id,
        "member_id": member_id
    }

def generate_chat_message_data(chat_room_id, member_id):
    return {
        "id": uuid.uuid4(),
        "public_id": uuid.uuid4(),
        "content": fake.text(),
        "created_date": to_offsetdatetime_format(fake.date_time_this_decade(tzinfo=None)),
        "reports_count": random.randint(0, 100),
        "char_room_id": chat_room_id,
        "member_id": member_id
    }

def generate_draft_post_data(board_id, member_id, category_id):
    return {
        "id": uuid.uuid4(),
        "public_id": uuid.uuid4(),
        "title": fake.sentence(),
        "content": fake.text(),
        "created_date": to_offsetdatetime_format(fake.date_time_this_decade(tzinfo=None)),
        "board_id": board_id,
        "member_id": member_id,
        "category_id": category_id
    }

def generate_video_data(post_id, member_id):
    return {
        "id": uuid.uuid4(),
        "public_id": uuid.uuid4(),
        "url": fake.url(),
        "size": random.randint(1000, 5000),
        "uploaded_date": to_offsetdatetime_format(fake.date_time_this_decade(tzinfo=None)),
        "extension": fake.file_extension(),
        "post_id": post_id,
        "member_id": member_id
    }

def generate_message_data(sender_id, receiver_id):
    return {
        "id": uuid.uuid4(),
        "public_id": uuid.uuid4(),
        "content": fake.text(),
        "created_date": to_offsetdatetime_format(fake.date_time_this_decade(tzinfo=None)),
        "sender_id": sender_id,
        "receiver_id": receiver_id
    }

def generate_public_chat_room_data(board_id):
    return {
        "id": uuid.uuid4(),
        "public_id": uuid.uuid4(),
        "name": fake.word(),
        "participants_count": random.randint(1, 100),
        "description": fake.text(),
        "created_date": to_offsetdatetime_format(fake.date_time_this_decade(tzinfo=None)),
        "board_id": board_id
    }

def data_to_csv(table_name, data_list, filename):
    with open(filename, 'w', newline='') as csvfile:
        writer = csv.writer(csvfile)
        headers = data_list[0].keys()
        writer.writerow(headers)
        for data in data_list:
            writer.writerow(data.values())

# 각 테이블에 대한 데이터 생성 및 CSV로 저장
community_data_list = [generate_community_data() for _ in range(10)]
data_to_csv("community", community_data_list, "script/community_data.csv")

board_data_list = [generate_board_data(community_data["id"]) for community_data in community_data_list]
data_to_csv("board", board_data_list, "script/board_data.csv")

member_data_list = [generate_member_data() for _ in range(10)]
data_to_csv("member", member_data_list, "script/member_data.csv")

post_category_data_list = [generate_post_category_data(board_data["id"]) for board_data in board_data_list]
data_to_csv("post_category", post_category_data_list,
            "script/post_category_data.csv")

post_data_list = [generate_post_data(board_data["id"], post_category_data["id"], member_data["id"]) for board_data in board_data_list for post_category_data in post_category_data_list for member_data in member_data_list]
data_to_csv("post", post_data_list, "script/post_data.csv")

comment_data_list = [generate_comment_data(post_data["id"], member_data["id"]) for post_data in post_data_list for member_data in member_data_list]
data_to_csv("comment", comment_data_list, "script/comment_data.csv")

image_data_list = [generate_image_data(post_data["id"], member_data["id"]) for post_data in post_data_list for member_data in member_data_list]
data_to_csv("image", image_data_list, "script/image_data.csv")

public_chat_room_data_list = [generate_public_chat_room_data(board_data["id"]) for board_data in board_data_list]
data_to_csv("public_chat_room", public_chat_room_data_list,
            "script/public_chat_room_data.csv")

chat_message_data_list = [generate_chat_message_data(public_chat_room_data["id"], member_data["id"]) for public_chat_room_data in public_chat_room_data_list for member_data in member_data_list]
data_to_csv("chat_message", chat_message_data_list,
            "script/chat_message_data.csv")

draft_post_data_list = [generate_draft_post_data(board_data["id"], member_data["id"], post_category_data["id"]) for board_data in board_data_list for member_data in member_data_list for post_category_data in post_category_data_list]
data_to_csv("draft_post", draft_post_data_list, "script/draft_post_data.csv")

video_data_list = [generate_video_data(post_data["id"], member_data["id"]) for post_data in post_data_list for member_data in member_data_list]
data_to_csv("video", video_data_list, "script/video_data.csv")

message_data_list = [generate_message_data(member_data["id"], member_data["id"]) for member_data in member_data_list]
data_to_csv("message", message_data_list, "script/message_data.csv")

def generate_copy_sql(table_name, csv_filename):
    base_name = os.path.basename(csv_filename)
    return f"COPY {table_name} FROM '/tmp/script/{base_name}' DELIMITER ',' CSV HEADER;"

# SQL 파일 생성
with open("script/bulk_insert.sql", "w") as sqlfile:
    tables = ["community", "board", "member", "post_category", "post", "comment", "image", "public_chat_room", "chat_message", "draft_post", "video", "message"]
    for table in tables:
        sqlfile.write(generate_copy_sql(table, f"{table}_data.csv"))
        sqlfile.write("\n")

# Docker 컨테이너 이름
container_name = "postgre"

# CS와 SQL 파일을 Docker 컨테이너로 복사
subprocess.run(["docker", "cp", "script/", f"{container_name}:/tmp/"])

# Docker 컨테이너 내의 PostgreSQL에서 SQL 파일 실행
subprocess.run(["docker", "exec", "-it", container_name, "psql", "-U", "postgres", "-d", "postgres", "-a", "-f", "/tmp/script/bulk_insert.sql"])
