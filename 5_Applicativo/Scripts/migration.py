from watches import OldWatchDatabase, WatchDatabase
from watches import Watch

watches = OldWatchDatabase().get_all_watches_in_db()
watch_db = WatchDatabase()
watches_info = []
watch_number = 0

def add_watch(watch_array):
    global watch_number

    watch = Watch(watch_array=watch_array)
    watch_db.add_watch(watch)
    
    print(f'[+] Added watch nr. {watch_number}')
    watch_number += 1

for watch_array in watches:
    try:
        add_watch(watch_array)
    except Exception as e:
        with open('errors.log', 'a') as f:
            f.write('----------\n')
            f.write(str(e))
            f.write('----------\n')
        print('[-] ERROR ')

  