import os
os.environ['TF_CPP_MIN_LOG_LEVEL'] = '3'

import pandas as pd
from tensorflow.keras import Sequential
from tensorflow.keras.layers import Dense
from tensorflow.keras.optimizers import Adam
from sklearn.model_selection import KFold

INPUT_FILE = 'input/data.csv'
INPUT_LIMIT = 700
LEARNING_RATE = 0.1


def main():
    data = pd.read_csv(INPUT_FILE) \
        .head(INPUT_LIMIT) \
        .replace({'Won': {'blue': 1, 'red': 0}}) \
        .drop(columns=['Minions killed'])

    x = data.iloc[:, 1:].to_numpy()
    y = data.iloc[:, 0].to_numpy()

    model = Sequential()
    model.add(Dense(1, input_dim=x.shape[1]))
    model.add(Dense(1, activation='sigmoid'))
    model.compile(optimizer=Adam(learning_rate=LEARNING_RATE), loss='binary_crossentropy', metrics=['accuracy'])

    fold_num = 1
    test_loss = 0.0
    test_acc = 0.0

    for train, test in KFold(n_splits=10, shuffle=False).split(x, y):
        model.fit(x[train], y[train], batch_size=5, epochs=15, verbose=0)
        scores = model.evaluate(x[test], y[test], verbose=0)
        print('{:>2}. Score: {} of {:.2f}; {} of {:.2f}%'.format(
            fold_num, model.metrics_names[0], scores[0], model.metrics_names[1], scores[1] * 100))

        test_loss += scores[0]
        test_acc += scores[1]
        fold_num += 1

    print('\n--- Results ---')
    print('Average loss: {:.2f}'.format(test_loss / (fold_num - 1)))
    print('Average accuracy: {:.2f}%'.format(test_acc * 100 / (fold_num - 1)))

    data.insert(loc=1, column='Predicted won', value=model.predict(x))
    print('\n--- Data sample ---')
    print(data[500:510])

    print('\n--- Params ---')
    print('Input limit:', INPUT_LIMIT)
    print('Learning rate:', LEARNING_RATE)


if __name__ == '__main__':
    main()
