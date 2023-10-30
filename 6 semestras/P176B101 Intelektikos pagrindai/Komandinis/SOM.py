import matplotlib.pyplot as plt
import numpy as np
import pandas as pd
from sklearn_som.som import SOM

FILE_NAME = 'normalized_data.csv'
USED_COLUMNS = ['Rainfall', 'MaxTemp']
LEARNING_RATE = 0.5


def plot_som(data, clusters):
    plt.figure()

    data_som = SOM(m=clusters, n=1, dim=2, lr=LEARNING_RATE)
    data_som.fit(data)
    clusters = data_som.predict(data)

    for cluster in np.unique(clusters):
        plt.scatter(
            data[clusters == cluster, 0],
            data[clusters == cluster, 1],
            label='Cluster {}'.format(cluster),
            alpha=0.2
        )

    centroid_x = data_som.cluster_centers_[:, :, 0]
    centroid_y = data_som.cluster_centers_[:, :, 1]
    plt.scatter(centroid_x, centroid_y, marker='x', c='k', s=80, label='Centroid')

    plt.title('Clustered data')
    plt.xlabel(USED_COLUMNS[0])
    plt.ylabel(USED_COLUMNS[1])
    plt.legend()


def plot_inertia(data):
    plt.figure()

    x = []
    y = []

    for i in range(2, 11):
        data_som = SOM(m=i, n=1, dim=2, lr=LEARNING_RATE)
        data_som.fit(data, shuffle=False)
        x.append(i)
        y.append(data_som.inertia_)

    plt.plot(x, y, marker='o')
    plt.grid(linewidth=0.2)

    plt.title('Cluster and inertia relationship')
    plt.xlabel('Amount of clusters')
    plt.ylabel('Inertia')
    plt.ylim(bottom=0)


def main():
    data = pd.read_csv(FILE_NAME)[USED_COLUMNS]

    print('Creating clusters...', end='', flush=True)
    plot_som(data.values, 4)
    print(' Done.')

    print('Plotting inertia graph...', end='', flush=True)
    plot_inertia(data.values)
    print(' Done.')

    plt.show()


if __name__ == '__main__':
    main()
