from watches import OldWatchDatabase

watches = OldWatchDatabase().get_all_watches_in_db()
watches_info = []

for watch in watches:
    values = watch[5].split('\n')
    values.pop(len(values) - 1)  # last line is blank

    for value in values:
        header = value.split('¦')[0]

        if header not in watches_info:
            watches_info.append(header)
        if header == 'ADLC':
            #print(watch[3])  # reference
            print(value.split('¦')[1])

print('\n'.join(watches_info))
print('Amount: ' + str(len(watches_info)))
