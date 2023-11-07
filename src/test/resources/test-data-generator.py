from concurrent.futures import ThreadPoolExecutor, as_completed
from faker import Faker
from datetime import datetime, timezone
from uuid import UUID
import uuid
import bcrypt
import random
import datetime
import subprocess
import os
import csv
import json
import jwt
import yaml

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

def generate_board_data(community_id, community_public_id):
    return {
        "id": uuid.uuid4(),
        "public_id": uuid.uuid4(),
        "name": fake.word(),
        "created_date": to_offsetdatetime_format(fake.date_time_this_decade(tzinfo=None)),
        "description": fake.text(),
        "community_id": community_id,
        "community_public_id": community_public_id
    }

def generate_member_data():
    return {
        "id": uuid.uuid4(),
        "public_id": uuid.uuid4(),
        "nickname": fake.user_name(),
        "created_date": to_offsetdatetime_format(fake.date_time_this_decade(tzinfo=None)),
        "modified_date": to_offsetdatetime_format(fake.date_time_this_decade(tzinfo=None)),
        "signup_id": fake.email(),
        "signup_password": '{bcrypt}'+bcrypt.hashpw(fake.password().encode('utf-8'), bcrypt.gensalt(rounds=10, prefix=b"2a")).decode(),
        "email": fake.email(),
        "expiration_date": to_offsetdatetime_format(fake.date_time_this_decade(tzinfo=None)),
        "account_locked": random.choice([True, False]),
        "role": random.choice(["USER"])
    }

def generate_member_data_password(password):
    return {
        "id": uuid.uuid4(),
        "public_id": uuid.uuid4(),
        "nickname": fake.user_name(),
        "created_date": to_offsetdatetime_format(fake.date_time_this_decade(tzinfo=None)),
        "modified_date": to_offsetdatetime_format(fake.date_time_this_decade(tzinfo=None)),
        "signup_id": fake.email(),
        "signup_password": '{bcrypt}'+bcrypt.hashpw(password.encode('utf-8'), bcrypt.gensalt(rounds=10, prefix=b"2a")).decode(),
        "email": fake.email(),
        "expiration_date": to_offsetdatetime_format(fake.date_time_this_decade(tzinfo=None)),
        "account_locked": random.choice([True, False]),
        "role": random.choice(["USER"])
    }

def generate_post_category_data(board_id, board_public_id):
    return {
        "id": uuid.uuid4(),
        "public_id": uuid.uuid4(),
        "name": fake.word(),
        "description": fake.text(),
        "board_id": board_id,
        "board_public_id": board_public_id
    }

def generate_post_data(board_id, post_category_id, member_id, board_public_id, post_category_public_id, member_public_id):
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
        "member_id": member_id,
        "board_public_id": board_public_id,
        "member_public_id": member_public_id,
        "post_category_public_id": post_category_public_id
    }

def generate_comment_data(board_id, post_id, member_id, board_public_id, post_public_id, member_public_id):
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
        "member_id": member_id,
        "board_id": board_id,
        "board_public_id": board_public_id,
        "post_public_id": post_public_id,
        "member_public_id": member_public_id
    }

def generate_image_data(post_id, member_id, post_public_id, member_public_id):
    return {
        "id": uuid.uuid4(),
        "public_id": uuid.uuid4(),
        "url": fake.image_url(),
        "size": random.randint(1000, 5000),
        "uploaded_date": to_offsetdatetime_format(fake.date_time_this_decade(tzinfo=None)),
        "extension": fake.file_extension(),
        "post_id": post_id,
        "member_id": member_id,
        "post_public_id": post_public_id,
        "member_public_id": member_public_id
    }

def generate_chat_message_data(chat_room_id, member_id, chat_room_public_id, member_public_id):
    return {
        "id": uuid.uuid4(),
        "public_id": uuid.uuid4(),
        "content": fake.text(),
        "created_date": to_offsetdatetime_format(fake.date_time_this_decade(tzinfo=None)),
        "reports_count": random.randint(0, 100),
        "chat_room_id": chat_room_id,
        "member_id": member_id,
        "chat_room_public_id": chat_room_public_id,
        "member_public_id": member_public_id
    }

def generate_draft_post_data(board_id, member_id, post_category_id, board_public_id, member_public_id, post_category_public_id):
    return {
        "id": uuid.uuid4(),
        "public_id": uuid.uuid4(),
        "title": fake.sentence(),
        "content": fake.text(),
        "created_date": to_offsetdatetime_format(fake.date_time_this_decade(tzinfo=None)),
        "board_id": board_id,
        "member_id": member_id,
        "post_category_id": post_category_id,
        "board_public_id": board_public_id,
        "member_public_id": member_public_id,
        "post_category_public_id": post_category_public_id,
    }

def generate_video_data(post_id, member_id, post_public_id, member_public_id):
    return {
        "id": uuid.uuid4(),
        "public_id": uuid.uuid4(),
        "url": fake.url(),
        "size": random.randint(1000, 5000),
        "uploaded_date": to_offsetdatetime_format(fake.date_time_this_decade(tzinfo=None)),
        "extension": fake.file_extension(),
        "post_id": post_id,
        "member_id": member_id,
        "post_public_id": post_public_id,
        "member_public_id": member_public_id
    }

def generate_message_data(sender_id, receiver_id, sender_public_id, receiver_public_id):
    return {
        "id": uuid.uuid4(),
        "public_id": uuid.uuid4(),
        "content": fake.text(),
        "created_date": to_offsetdatetime_format(fake.date_time_this_decade(tzinfo=None)),
        "sender_id": sender_id,
        "receiver_id": receiver_id,
        "sender_public_id": sender_public_id,
        "receiver_public_id": receiver_public_id
    }

def generate_public_chat_room_data(board_id, board_public_id):
    return {
        "id": uuid.uuid4(),
        "public_id": uuid.uuid4(),
        "name": fake.word(),
        "participants_count": random.randint(1, 100),
        "description": fake.text(),
        "created_date": to_offsetdatetime_format(fake.date_time_this_decade(tzinfo=None)),
        "board_id": board_id,
        "board_public_id": board_public_id
    }

def getKey():
    with open('../../main/resources/config/application-test.yml', 'r') as file:
        config = yaml.safe_load(file)
    return config['jwt']['secretKey']


def generate_token(member_public_id):
    return {
        "id": uuid.uuid4(),
        "public_id": uuid.uuid4(),
        "member_public_id": member_public_id,
        "role": "USER",
        "refresh_token": jwt.encode({"memberId": str(member_public_id),
                                     "role": "USER",
                                     "exp": datetime.datetime.utcnow() + datetime.timedelta(days=3)},
                                    getKey(),algorithm="HS256").decode()
    }

def data_to_sql(executor, table_name, data_list, file, batch_size=1000):
# INSERT 문을 만드는 함수
    def generate_insert_statements(table, batch):
        base_statement = "INSERT INTO {} ({}) VALUES ".format(table, ', '.join(batch[0].keys()))
        value_clauses = []
        for data in batch:
            values = ', '.join(["'{}'".format(str(v).replace("'", "''")) for v in data.values()])
            value_clauses.append("({})".format(values))
        return base_statement + ', '.join(value_clauses) + ";\n"

    # 파일에 기록하는 작업
    for i in range(0, len(data_list), batch_size):
      batch = data_list[i:i+batch_size]
      statement = generate_insert_statements(table_name, batch)
      file.write(statement)

    return executor.submit(lambda: None)

class UUIDEncoder(json.JSONEncoder):
    def default(self, obj):
        if isinstance(obj, UUID):
            # if the obj is uuid, we simply return the value of uuid
            return obj.hex
        return json.JSONEncoder.default(self, obj)

def save_data_to_json(file_name, data):
    with open(file_name, 'w', encoding='utf-8') as f:
        json.dump(data, f, ensure_ascii=False, indent=4, default=str)

def bulk_data_generation(executor):
    with open("./data.sql", 'w') as file:
        futures = []

        community_data_list = [generate_community_data()]
        futures.append(data_to_sql(executor, "community", community_data_list, file))

        board_data_list = [generate_board_data(community_data["id"], community_data["public_id"]) for _ in range(20) for community_data in community_data_list]
        futures.append(data_to_sql(executor, "board", board_data_list, file))

        member_data_list = [generate_member_data() for _ in range(100)]
        member_data_list.append(generate_member_data_password('1234'))
        futures.append(data_to_sql(executor, "member", member_data_list, file))

        post_category_data_list = []
        for _ in range(200):
            board_data = random.choice(board_data_list)
            post_category_data_list.append(generate_post_category_data(board_data["id"], board_data["public_id"]))
        futures.append(data_to_sql(executor, "post_category", post_category_data_list, file))

        post_data_list = []
        for _ in range(5000):
            board_data = random.choice(board_data_list)
            post_category_data = random.choice(post_category_data_list)
            member_data = random.choice(member_data_list)
            post_data_list.append(generate_post_data(board_data["id"], post_category_data["id"], member_data["id"], board_data["public_id"], post_category_data["public_id"], member_data["public_id"]))

        board_random = random.choice(board_data_list)
        post_category_random = random.choice(post_category_data_list)
        post_random = random.choice(post_data_list)

        post_data_list.append(generate_post_data(board_random["id"], post_category_random["id"], member_data_list[-1]["id"],
                                                board_random["public_id"], post_category_random["public_id"], member_data_list[-1]["public_id"]))
        post_data_list.append(generate_post_data(board_random["id"], post_category_random["id"], member_data_list[-1]["id"],
                                                 board_random["public_id"], post_category_random["public_id"], member_data_list[-1]["public_id"]))
        post_data_list.append(generate_post_data(board_random["id"], post_category_random["id"], member_data_list[-1]["id"],
                                                 board_random["public_id"], post_category_random["public_id"], member_data_list[-1]["public_id"]))
        futures.append(data_to_sql(executor, "post", post_data_list, file))

        comment_data_list = []
        for _ in range(10000):
            board_data = random.choice(board_data_list)
            member_data = random.choice(member_data_list)
            post_data = random.choice(post_data_list)
            comment_data_list.append(generate_comment_data(board_data["id"], post_data["id"], member_data["id"], board_data["public_id"], post_data["public_id"], member_data["public_id"]))

        comment_data_list.append(generate_comment_data(post_random["board_id"], post_random["id"], member_data_list[-1]["id"],
                                                    post_random["board_public_id"], post_random["public_id"], member_data_list[-1]["public_id"]))
        comment_data_list.append(generate_comment_data(post_random["board_id"], post_random["id"], member_data_list[-1]["id"],
                                                   post_random["board_public_id"], post_random["public_id"], member_data_list[-1]["public_id"]))
        comment_data_list.append(generate_comment_data(post_random["board_id"], post_random["id"], member_data_list[-1]["id"],
                                                       post_random["board_public_id"], post_random["public_id"], member_data_list[-1]["public_id"]))
        comment_data_list.append(generate_comment_data(post_random["board_id"], post_random["id"], member_data_list[-1]["id"],
                                                       post_random["board_public_id"], post_random["public_id"], member_data_list[-1]["public_id"]))
        futures.append(data_to_sql(executor, "comment", comment_data_list, file))

        image_data_list = []
        for _ in range(0):
            member_data = random.choice(member_data_list)
            post_data = random.choice(post_data_list)
            image_data_list.append(generate_image_data(post_data["id"], member_data["id"], post_data["public_id"], member_data["public_id"]))
        futures.append(data_to_sql(executor, "image", image_data_list, file))

        public_chat_room_data_list = []
        for _ in range(0):
            board_data = random.choice(board_data_list)
            public_chat_room_data_list.append(generate_public_chat_room_data(board_data["id"], board_data["public_id"]))
        futures.append(data_to_sql(executor, "public_chat_room", public_chat_room_data_list, file))

        chat_message_data_list = []
        for _ in range(0):
            public_chat_room_data = random.choice(public_chat_room_data_list)
            member_data = random.choice(member_data_list)
            chat_message_data_list.append(generate_chat_message_data(public_chat_room_data["id"], member_data["id"], public_chat_room_data["public_id"], member_data["public_id"]))
        futures.append(data_to_sql(executor, "chat_message", chat_message_data_list, file))

        draft_post_data_list = []
        for _ in range(0):
            board_data = random.choice(board_data_list)
            post_category_data = random.choice(post_category_data_list)
            member_data = random.choice(member_data_list)
            draft_post_data_list.append(generate_draft_post_data(board_data["id"], member_data["id"], post_category_data["id"], board_data["public_id"], member_data["public_id"], post_category_data["public_id"]))
        futures.append(data_to_sql(executor,"draft_post", draft_post_data_list, file))

        video_data_list = []
        for _ in range(0):
            member_data = random.choice(member_data_list)
            post_data = random.choice(post_data_list)
            video_data_list.append(generate_video_data(post_data["id"], member_data["id"], post_data["public_id"], member_data["public_id"]))
        futures.append(data_to_sql(executor, "video", video_data_list, file))

        message_data_list = []
        for _ in range(0):
            member_data1 = random.choice(member_data_list)
            member_data2 = random.choice(member_data_list)
            message_data_list.append(generate_message_data(member_data1["id"], member_data2["id"], member_data1["public_id"], member_data2["public_id"]))
        futures.append(data_to_sql(executor, "message", message_data_list, file))

        token_data_list = []
        token_data_list.append(generate_token(member_data_list[-1]["public_id"]))
        futures.append(data_to_sql(executor, "token", token_data_list, file))

        member_data_list[-1]["signup_password"] = '1234'
        save_data_to_json("data/member_data.json", member_data_list[-1])
        save_data_to_json("data/community_data.json", community_data_list[0])
        save_data_to_json("data/board_data.json", board_data_list[0])
        save_data_to_json("data/post_category_data.json", post_category_data_list[0])
        save_data_to_json("data/post_data.json", [post_data_list[0], post_data_list[-3], post_data_list[-2], post_data_list[-1]])
        save_data_to_json("data/comment_data.json", [comment_data_list[-4], comment_data_list[-3], comment_data_list[-2], comment_data_list[-1]])
        save_data_to_json("data/token_data.json", token_data_list[0])

        return futures

def main():
    with ThreadPoolExecutor(max_workers=20) as executor:
        bulk_data_generation(executor)
if __name__ == "__main__":
    main()
