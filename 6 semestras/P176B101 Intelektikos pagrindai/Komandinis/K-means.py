import pandas as pd
import matplotlib.pyplot as plt
from pandas.core.frame import DataFrame
from sklearn.cluster import KMeans

FILE_NAME = 'normalized_data.csv'
VISUALISE_SILHOUETTES = False
VISUALISE_INERTIA = False


#read data
def read_weather_data():
    data = pd.read_csv(FILE_NAME)
    return data


def k_means_clustering_two_columns (data, columnNames, number_of_clusters):
    df = DataFrame(data,columns=columnNames)
    kmeans = KMeans(n_clusters=number_of_clusters)
    fitted_kmeans = kmeans.fit(df)
    if(VISUALISE_INERTIA):
        visualise_inertia(df)
    if(VISUALISE_SILHOUETTES):
        visualise_silhouette_scores(df)
    centroids = fitted_kmeans.cluster_centers_
    plt.scatter(data[columnNames[0]], data[columnNames[1]], c= fitted_kmeans.labels_.astype(float), s=50, alpha=0.5)
    plt.scatter(centroids[:, 0], centroids[:, 1], c='red', s=50)
    print(centroids)
    plt.show()


def visualise_inertia(data):
    distortions = []
    for i in range(1, 11):
        km = KMeans(
            n_clusters=i, init='random',
            n_init=10, max_iter=300,
            tol=1e-04, random_state=0
        )
        km.fit(data)
        distortions.append(km.inertia_)

    # plot inertia graph
    plt.plot(range(1, 11), distortions, marker='o')
    plt.xlabel('Number of clusters')
    plt.ylabel('inertia')
    plt.show()


def visualise_silhouette_scores(data):
    from yellowbrick.cluster import SilhouetteVisualizer
    
    fig, ax = plt.subplots(2, 2, figsize=(15,8))
    for i in [2, 3, 4, 5]:
        km = KMeans(n_clusters=i, init='k-means++', n_init=10, max_iter=100, random_state=42)
        q, mod = divmod(i, 2)
        visualizer = SilhouetteVisualizer(km, colors='yellowbrick', ax=ax[q-1][mod])
        visualizer.fit(data)


def k_means_clustering_all_columns (data, number_of_clusters):
    kmeans = KMeans(n_clusters=number_of_clusters)
    fitted_kmeans = kmeans.fit(data)
    centroids = fitted_kmeans.cluster_centers_
    if(VISUALISE_INERTIA):
        visualise_inertia(data)
    if(VISUALISE_SILHOUETTES):
        visualise_silhouette_scores(data)
    print(centroids)


if __name__ == '__main__':
    data = read_weather_data()
    k_means_clustering_two_columns(data, ['Rainfall', 'MaxTemp'], 10)
    k_means_clustering_all_columns(data, 2)
