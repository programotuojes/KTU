import random


count = 100
car_names = ['Honda', 'Toyota', 'Mazda', 'Citroen', 'Audi', 'Daihatsu', 'Mitsubishi', 'Volvo', 'Renault', 'BMW', 'Lincoln', 'Suzuki', 'AMC', 'Subaru']


def get_filename(i):
    return 'IFF-8-7_KlevinskasG_L1_dat_' + str(i) + '.txt'


def str_to_int(str):
    total = 0

    for i in range(len(str)):
        total += ord(str[i])

    return total


def is_filtered(mileage, fuel_econ, name):
    result = mileage * fuel_econ + str_to_int(name)
    return (ord(str(result)[0]) - ord('0')) % 2 != 0


def generate_file(ver):
    written = 0
    f = open(get_filename(ver), 'w')
    f.write(str(count) + '\n')

    while written < count:
        mileage = random.randint(1000, 600000)
        fuel_econ = random.randint(30, 140) / 10.0
        name = car_names[random.randrange(len(car_names))]

        if ver == 1:
            if is_filtered(mileage, fuel_econ, name):
                continue
        elif ver == 3:
            if not is_filtered(mileage, fuel_econ, name):
                continue

        f.write('{} {} {}\n'.format(mileage, fuel_econ, name))
        written += 1

    f.close()


generate_file(1)
generate_file(2)
generate_file(3)
