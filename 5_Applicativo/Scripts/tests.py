from watches import OldWatchDatabase

watches = OldWatchDatabase().get_all_watches_in_db()
watches_info = []

for watch in watches:
    values = watch[5].split('\n')
    values.pop(len(values) - 1)  # last line is blank

    amount_prices = 0
    prices = []

    for value in values:
        header = value.split('¦')[0]

        if header not in watches_info:
            watches_info.append(header)
        if header == 'retail_price ':
            #print('---------------------')
            #print(watch[3] + '\n')  # reference
            #print(value.split('¦')[1])
            value = value.split('¦')[1]
            if value != ' ,NULL':
                amount_prices += 1
                prices.append(value)

    if amount_prices > 1:
        print(watch[3] + '\n')
        print(prices)
        print('---------')
        input()

print('\n'.join(watches_info))
print('Amount: ' + str(len(watches_info)))
