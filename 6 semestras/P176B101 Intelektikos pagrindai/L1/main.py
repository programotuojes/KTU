import csv
import re
from pathlib import Path

import pandas as pd
import seaborn as sn
from matplotlib import pyplot as plt
from pandas.plotting import scatter_matrix

from calc import *
from helper import *

INPUT_FILE = 'data.csv'
INPUT_LIMIT = -1  # -1 to disable
NUMERICAL_COLUMNS = [
    'blue_kills',
    'blue_deaths',
    'blue_assists',
    'blue_total_gold',
    'blue_total_minions_killed',
    'red_kills',
    'red_deaths',
    'red_assists',
    'red_total_gold',
    'red_total_minions_killed'
]
CATEGORICAL_COLUMNS = [
    'won',
    'first_blood',
    'blue_dragons',
    'blue_heralds',
    'blue_towers_destroyed',
    'red_dragons',
    'red_heralds',
    'red_towers_destroyed'
]


def read_data():
    data = open(INPUT_FILE, mode='r')
    reader = csv.DictReader(data)
    count = 0

    columns = {
        'won': [],
        'first_blood': [],
        'blue_kills': [],
        'blue_deaths': [],
        'blue_assists': [],
        'blue_dragons': [],
        'blue_heralds': [],
        'blue_towers_destroyed': [],
        'blue_total_gold': [],
        'blue_total_minions_killed': [],
        'red_kills': [],
        'red_deaths': [],
        'red_assists': [],
        'red_dragons': [],
        'red_heralds': [],
        'red_towers_destroyed': [],
        'red_total_gold': [],
        'red_total_minions_killed': [],
    }

    for row in reader:
        columns['won'].append(row['won'])
        columns['first_blood'].append(row['firstBlood'])
        columns['blue_kills'].append(row['blueKills'])
        columns['blue_deaths'].append(row['blueDeaths'])
        columns['blue_assists'].append(row['blueAssists'])
        columns['blue_dragons'].append(row['blueDragons'])
        columns['blue_heralds'].append(row['blueHeralds'])
        columns['blue_towers_destroyed'].append(row['blueTowersDestroyed'])
        columns['blue_total_gold'].append(row['blueTotalGold'])
        columns['blue_total_minions_killed'].append(row['blueTotalMinionsKilled'])
        columns['red_kills'].append(row['redKills'])
        columns['red_deaths'].append(row['redDeaths'])
        columns['red_assists'].append(row['redAssists'])
        columns['red_dragons'].append(row['redDragons'])
        columns['red_heralds'].append(row['redHeralds'])
        columns['red_towers_destroyed'].append(row['redTowersDestroyed'])
        columns['red_total_gold'].append(row['redTotalGold'])
        columns['red_total_minions_killed'].append(row['redTotalMinionsKilled'])

        count += 1
        if INPUT_LIMIT != -1 and count >= INPUT_LIMIT:
            break

    return columns


def create_numerical_table(columns):
    Path('out/').mkdir(parents=True, exist_ok=True)
    file = open('out/numerical.csv', mode='w')
    writer = csv.writer(file)

    writer.writerow([
        'Atributo pavadinimas',
        'Kiekis',
        'Trūkstamos reikšmės, %',
        'Kardinalumas',
        'Minimali reikšmė',
        'Maksimali reikšmė',
        '1-asis kvartilis',
        '3-asis kvartilis',
        'Vidurkis',
        'Mediana',
        'Standartinis nuokrypis'
    ])

    for key in NUMERICAL_COLUMNS:
        col = get_fixed_column(columns, key)

        writer.writerow([
            key,
            len(col),
            '{:.1f}'.format(get_missing_percent(columns, key)),
            cardinality(col),
            min(col),
            max(col),
            first_quartile(col),
            third_quartile(col),
            '{:.3f}'.format(avg(col)),
            median(col),
            '{:.3f}'.format(std_dev(col))
        ])


def create_categorical_table(columns):
    Path('out/').mkdir(parents=True, exist_ok=True)
    file = open('out/categorical.csv', mode='w')
    writer = csv.writer(file)

    writer.writerow([
        'Atributo pavadinimas',
        'Kiekis',
        'Trūkstamos reikšmės, %',
        'Kardinalumas',
        'Moda',
        'Modos dažnumas',
        'Moda, %',
        '2-oji moda',
        '2-osios modos dažnumas',
        '2-oji moda, %',
    ])

    for key in CATEGORICAL_COLUMNS:
        col = get_fixed_column(columns, key)
        m1, m1_freq, m1_percent = mode(col)
        m2, m2_freq, m2_percent = mode2(col)

        writer.writerow([
            key,
            len(col),
            '{:.1f}'.format(get_missing_percent(columns, key)),
            cardinality(col),
            m1,
            m1_freq,
            '{:.1f}'.format(m1_percent),
            m2,
            m2_freq,
            '{:.1f}'.format(m2_percent)
        ])


def create_table(columns, fun):
    Path('out/').mkdir(parents=True, exist_ok=True)
    file = open('out/{}.csv'.format(fun.__name__), mode='w')
    writer = csv.writer(file)

    writer.writerow(['', *NUMERICAL_COLUMNS])

    for key1 in NUMERICAL_COLUMNS:
        row = [key1]

        for key2 in NUMERICAL_COLUMNS:
            row.append('{:.2f}'.format(fun(columns, key1, key2)))

        writer.writerow(row)


def draw_histogram(columns, key):
    plt.figure('Histograma "{}"'.format(key))
    plt.suptitle('Histograma "{}"'.format(key))
    plt.xlabel('Stulpelis "{}"'.format(key))
    plt.ylabel('Kiekis')
    plt.hist(columns[key], bins=len(set(columns[key])))


def draw_scatter_plot(columns, key1, key2):
    plt.figure('"{}" ir "{}" diagrama'.format(key1, key2))
    plt.suptitle('"{}" ir "{}" diagrama'.format(key1, key2))
    plt.scatter(columns[key1], columns[key2], alpha=0.4)
    plt.xlabel(key1)
    plt.ylabel(key2)


def draw_splom(columns, team):
    result = {}

    for key in columns:
        if team in key and key in NUMERICAL_COLUMNS:
            new_key = re.sub('blue_|red_|total_', '', key)
            result[new_key] = columns[key]

    scatter_matrix(pd.DataFrame(result))
    plt.suptitle('"{}" komanda'.format(team.capitalize()))


def draw_hist_by_won(columns, key):
    fig, axs = plt.subplots(2, sharex='all')
    fig.suptitle('Histogramos "{}" pagal "won"'.format(key))

    won = []
    lost = []
    for idx, i in enumerate(columns['won']):
        if i == 'blue':
            won.append(columns[key][idx])
        else:
            lost.append(columns[key][idx])

    axs[0].hist(won, bins='sturges')
    axs[1].hist(lost, bins='sturges')

    plt.xlabel(key)
    plt.ylabel('Kiekis')

    axs[0].set_title('blue won')
    axs[1].set_title('red won')


def draw_box_plot(columns, key, key_by='won'):
    pd_columns = ['blue_' + key, 'red_' + key]
    df = pd.DataFrame(columns)
    df.boxplot(column=pd_columns, by=key_by)
    plt.suptitle('"{}" sugrupuota pagal "{}"'.format(key, key_by))


def draw_correlation_matrix(columns):
    plt.figure('Koreliacijos matrica')
    plt.suptitle('Koreliacijos matrica')

    df = pd.DataFrame(columns, columns=NUMERICAL_COLUMNS)
    matrix = df.corr()
    sn.heatmap(matrix)


def normalize_data(columns):
    norm = {}

    for key in columns:
        if key in NUMERICAL_COLUMNS:
            min_v = min(columns[key])
            width = max(columns[key]) - min_v
            norm[key] = [(i - min_v) / width for i in columns[key]]

            # mean = avg(columns[key])
            # dev = std_dev(columns[key])
            # norm[key] = [(i - mean) / dev for i in columns[key]]
        else:
            norm[key] = columns[key]

    return norm


def cat_to_num(columns):
    result = {}

    for key in columns:
        if key in ['won', 'first_blood']:
            result['blue_' + key] = map(lambda cell: 1 if cell == 'blue' else 0, columns[key])
        else:
            result[key] = columns[key]

    return result


def main():
    columns = read_data()

    # 1. Create column data analysis tables
    create_numerical_table(columns)
    create_categorical_table(columns)

    # 2. Data manipulation
    columns = get_fixed_columns(columns)
    # columns = remove_outliers(columns, NUMERICAL_COLUMNS)
    # columns = cat_to_num(columns)
    # columns = normalize_data(columns)

    # 3. Draw histograms
    # for key in columns:
    #     draw_histogram(columns, key)

    # 4. Draw scatter plot matrix
    # draw_splom(columns, 'blue')
    # draw_splom(columns, 'red')

    # 5. Draw scatter plots
    # draw_scatter_plot(columns, 'blue_kills', 'blue_total_gold')
    # draw_scatter_plot(columns, 'blue_deaths', 'blue_total_gold')

    # 6. Draw histograms by 'won'
    # draw_hist_by_won(columns, 'blue_kills')
    # draw_hist_by_won(columns, 'blue_total_gold')

    # 7. Draw box plots
    # draw_box_plot(columns, 'kills')
    # draw_box_plot(columns, 'total_gold')

    # 8. Calculate covariance and correlation
    create_table(columns, covariance)
    create_table(columns, correlation)
    # draw_correlation_matrix(columns)

    plt.show()


if __name__ == '__main__':
    main()
