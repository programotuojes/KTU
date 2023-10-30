import csv


INPUT_FILE = 'data.original.csv'
OUTPUT_FILE = 'data.csv'
DISCARD_HEADERS = [
    'gameId',
    'blueWins',
    'blueWardsPlaced',
    'blueWardsDestroyed',
    'blueFirstBlood',
    'blueEliteMonsters',
    'blueAvgLevel',
    'blueTotalExperience',
    'blueTotalJungleMinionsKilled',
    'blueGoldDiff',
    'blueExperienceDiff',
    'blueCSPerMin',
    'blueGoldPerMin',
    'redWardsPlaced',
    'redWardsDestroyed',
    'redFirstBlood',
    'redEliteMonsters',
    'redAvgLevel',
    'redTotalExperience',
    'redTotalJungleMinionsKilled',
    'redGoldDiff',
    'redExperienceDiff',
    'redCSPerMin',
    'redGoldPerMin'
]


if __name__ == '__main__':
    data = open(INPUT_FILE)
    modified_data = open(OUTPUT_FILE, mode='w')

    reader = csv.reader(data)
    writer = csv.writer(modified_data)

    header = next(reader)
    discard_ids = [idx for idx, i in enumerate(header) if i in DISCARD_HEADERS]

    new_header = [i for i in header if i not in DISCARD_HEADERS]
    new_header.insert(0, 'won')
    new_header.insert(1, 'firstBlood')

    writer.writerow(new_header)

    for row in reader:
        won = 'blue' if row[1] == '1' else 'red'
        firstBlood = 'blue' if row[4] == '1' else 'red'

        new_row = [i for idx, i in enumerate(row) if idx not in discard_ids]
        new_row.insert(0, won)
        new_row.insert(1, firstBlood)

        writer.writerow(new_row)
