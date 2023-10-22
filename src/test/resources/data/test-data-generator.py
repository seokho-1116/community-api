from concurrent.futures import ThreadPoolExecutor, as_completed
from faker import Faker
import uuid
import random
import subprocess
import os
import csv
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
        "signup_password": fake.password(),
        "email": fake.email(),
        "expiration_date": to_offsetdatetime_format(fake.date_time_this_decade(tzinfo=None)),
        "account_locked": random.choice([True, False]),
        "role": random.choice(["USER", "ADMIN"])
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
        "post_category_public_id": post_category_public_id,
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
        "char_room_id": chat_room_id,
        "member_id": member_id,
        "char_room_public_id": chat_room_public_id,
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

def data_to_csv(table_name, data_list, filename):
    with open(filename, 'w', newline='') as csvfile:
        writer = csv.writer(csvfile)
        headers = data_list[0].keys()
        writer.writerow(headers)
        for data in data_list:
            writer.writerow(data.values())

def data_to_csv_parallel(executor, table_name, data_list, filename):
    # 비동기적으로 파일 쓰기 작업 수행
    future = executor.submit(data_to_csv, table_name, data_list, filename)
    return future  # Future 객체 반환

def generate_copy_sql(table_name, csv_filename):
    base_name = os.path.basename(csv_filename)
    return f"COPY {table_name} FROM '/tmp/script/{base_name}' DELIMITER ',' CSV HEADER;"

def bulk_data_generation(executor):
    futures = []

    community_data_list = [generate_community_data()]
    futures.append(data_to_csv_parallel(executor, "community", community_data_list, "script/community_data.csv"))

    board_data_list = [generate_board_data(community_data["id"], community_data["public_id"]) for _ in range(20) for community_data in community_data_list]
    futures.append(data_to_csv_parallel(executor, "board", board_data_list, "script/board_data.csv"))

    member_data_list = [generate_member_data() for _ in range(100)]
    futures.append(data_to_csv_parallel(executor, "member", member_data_list, "script/member_data.csv"))

    post_category_data_list = []
    for _ in range(200):
        board_data = random.choice(board_data_list)
        post_category_data_list.append(generate_post_category_data(board_data["id"], board_data["public_id"]))
    futures.append(data_to_csv_parallel(executor, "post_category", post_category_data_list,
            "script/post_category_data.csv"))

    post_data_list = []
    for _ in range(5000):
        board_data = random.choice(board_data_list)
        post_category_data = random.choice(post_category_data_list)
        member_data = random.choice(member_data_list)
        post_data_list.append(generate_post_data(board_data["id"], post_category_data["id"], member_data["id"], board_data["public_id"], post_category_data["public_id"], member_data["public_id"]))
    futures.append(data_to_csv_parallel(executor, "post", post_data_list, "script/post_data.csv"))

    comment_data_list = []
    for _ in range(10000):
        board_data = random.choice(board_data_list)
        member_data = random.choice(member_data_list)
        post_data = random.choice(post_data_list)
        comment_data_list.append(generate_comment_data(board_data["id"], post_data["id"], member_data["id"], board_data["public_id"], post_data["public_id"], member_data["public_id"]))
    futures.append(data_to_csv_parallel(executor, "comment", comment_data_list, "script/comment_data.csv"))

    image_data_list = []
    for _ in range(5000):
        member_data = random.choice(member_data_list)
        post_data = random.choice(post_data_list)
        image_data_list.append(generate_image_data(post_data["id"], member_data["id"], post_data["public_id"], member_data["public_id"]))
    futures.append(data_to_csv_parallel(executor, "image", image_data_list, "script/image_data.csv"))

    public_chat_room_data_list = []
    for _ in range(500):
        board_data = random.choice(board_data_list)
        public_chat_room_data_list.append(generate_public_chat_room_data(board_data["id"], board_data["public_id"]))
    futures.append(data_to_csv_parallel(executor, "public_chat_room", public_chat_room_data_list,
            "script/public_chat_room_data.csv"))

    chat_message_data_list = []
    for _ in range(5000):
        public_chat_room_data = random.choice(public_chat_room_data_list)
        member_data = random.choice(member_data_list)
        chat_message_data_list.append(generate_chat_message_data(public_chat_room_data["id"], member_data["id"], public_chat_room_data["public_id"], member_data["public_id"]))
    futures.append(data_to_csv_parallel(executor, "chat_message", chat_message_data_list,
            "script/chat_message_data.csv"))

    draft_post_data_list = []
    for _ in range(1000):
        board_data = random.choice(board_data_list)
        post_category_data = random.choice(post_category_data_list)
        member_data = random.choice(member_data_list)
        draft_post_data_list.append(generate_draft_post_data(board_data["id"], member_data["id"], post_category_data["id"], board_data["public_id"], member_data["public_id"], post_category_data["public_id"]))
    futures.append(data_to_csv_parallel(executor,"draft_post", draft_post_data_list, "script/draft_post_data.csv"))

    video_data_list = []
    for _ in range(5000):
        member_data = random.choice(member_data_list)
        post_data = random.choice(post_data_list)
        video_data_list.append(generate_video_data(post_data["id"], member_data["id"], post_data["public_id"], member_data["public_id"]))
    futures.append(data_to_csv_parallel(executor, "video", video_data_list, "script/video_data.csv"))

    message_data_list = []
    for _ in range(5000):
        member_data1 = random.choice(member_data_list)
        member_data2 = random.choice(member_data_list)
        message_data_list.append(generate_message_data(member_data1["id"], member_data2["id"], member_data1["public_id"], member_data2["public_id"]))
    futures.append(data_to_csv_parallel(executor, "message", message_data_list, "script/message_data.csv"))

    return futures

def main():
    with ThreadPoolExecutor(max_workers=10) as executor:
        futures = bulk_data_generation(executor)

        # 모든 비동기 작업이 완료될 때까지 대기
        for future in as_completed(futures):
            try:
                future.result()  # 각 작업의 결과 가져오기 (여기서는 필요하지 않지만 오류 확인을 위해 사용)
            except Exception as e:
                print(f"Generated an exception: {e}")

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

    # ... 기존의 SQL 파일 생성 및 Docker 컨테이너 작업 ...

if __name__ == "__main__":
    main()
