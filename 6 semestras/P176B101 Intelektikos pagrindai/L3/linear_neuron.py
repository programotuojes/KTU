import numpy as np


class AdaptiveLinearNeuron(object):
    def __init__(self, rate=0.01, niter=10, error_goal=0):
        self.rate = rate
        self.niter = niter
        self.error_goal = error_goal
        self.weight = []
        self.errors = []  # Number of misclassifications
        self.errors2 = []  # Error copy
        self.cost = []  # Cost function

    def fit(self, x, y):
        """
        Fit training data.

        :param x: Training vectors, shape: [#samples, #features]
        :param y: Target values, shape: [#samples]
        """

        self.weight = np.zeros(1 + x.shape[1])

        for i in range(self.niter):
            output = self.predict(x)
            errors = y - output
            self.errors2 = y - output
            self.weight[1:] += self.rate * x.T.dot(errors)
            self.weight[0] += self.rate * errors.sum()
            cost = (errors ** 2).sum() / 2.0
            self.cost.append(cost)

            mse = sum(map(lambda a: a ** 2, self.errors2)) / len(self.errors2)
            if mse <= self.error_goal:
                break

        return self

    def predict(self, x):
        return np.dot(x, self.weight[1:]) + self.weight[0]
