from watches import Watch, OldWatchDatabase, WatchDatabase

db = WatchDatabase()

print(db.brand_exists('A. Lange & Söhne'))
db.add_family('TEST', db.add_brand('A. Lange & Söhne'))