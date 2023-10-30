import numpy as np
from matplotlib import pyplot as plt


def activity(years, spots):
    plt.plot(years, spots)

    plt.title('Amount of sun spots over time')
    plt.xlabel('Year')
    plt.ylabel('Amount of sun spots')


def plot_3d(p, t):
    fig, ax = plt.subplots(subplot_kw=dict(projection='3d'))
    ax.scatter([i[0] for i in p], [i[1] for i in p], t)

    ax.set_title('3D plot of sun spot amounts')
    ax.set_xlabel('Amount on year 1')
    ax.set_ylabel('Amount on year 2')
    ax.set_zlabel('Amount on year 3')


def verify(t, prediction, years, title):
    fig, ax = plt.subplots()
    real_data, = ax.plot(years, t)
    predicted, = ax.plot(years, prediction)

    ax.set_title(title)
    ax.set_xlabel('Year')
    ax.set_ylabel('Amount of sun spots')
    ax.legend([real_data, predicted], ['Real data', 'Predicted'])


def error(e, years):
    fig, ax = plt.subplots()
    ax.plot(years, e)

    plt.title('Error graph')
    plt.xlabel('Year')
    plt.ylabel('Error')


def error_hist(e):
    fig, ax = plt.subplots()
    ax.hist(e)

    plt.title('Error histogram')


def show():
    plt.show()
