import re

INPUT_FILE = 'input.xml'
OUTPUT_FILE = 'output.xml'

src = open(INPUT_FILE, 'r').readlines()
dest = open(OUTPUT_FILE, 'w')

i = 0
while i < len(src):
    if '<FVALUE>' in src[i] and '<FVALUE>' in src[i + 1]:
        f1 = re.findall('>(.+?)<', src[i])[0]
        f2 = re.findall('>(.+?)<', src[i + 1])[0]

        padding = src[i].index('<') * ' '

        dest.write('{}<FVALUE>{}..{}</FVALUE>\n'.format(padding, f1, f2))
        i += 1
    else:
        dest.write(src[i])

    i += 1
