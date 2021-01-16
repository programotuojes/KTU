x_file = open('X.txt', 'r')
y_file = open('Y.txt', 'r')

parsed = open('coords.txt', 'w')

x_coords = x_file.readline().split(',')
y_coords = y_file.readline().split(',')

for i in range(len(x_coords)):
    parsed.write('{} {}\n'.format(
        int(float(x_coords[i]) * 1000),
        int(float(y_coords[i]) * 1000)
    ))
