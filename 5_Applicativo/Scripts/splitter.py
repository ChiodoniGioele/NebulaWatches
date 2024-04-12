import sqlite3
from watches import OldWatchDatabase

FILE_SPLITS = 5

old_db = OldWatchDatabase()
total_rows = old_db.get_watches_count()

for i in range(0, FILE_SPLITS):
    split_filepath = old_db.db_file_path.replace('.db', f'_{i+1}_part.db')
    split_db = OldWatchDatabase(db_file_path=split_filepath)

    split_db.create_db_watch_table()

    amount = total_rows / FILE_SPLITS
    watches = old_db.get_watches_by_limit(amount, amount * i)

    for watch in watches:
        split_db.add_db_watch(watch[0], watch[1], watch[2], watch[3], watch[4], watch[5],
                              watch[6])
        
    split_db.close_db_connection()

old_db.close_db_connection()