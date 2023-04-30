

def read_titles():
    path = os.path.join(DATA_DIR, 'title_basics', 'data.tsv')
    print("Reading data from: ", path)
    with open(path, 'r') as f:
        for i in range(10):
            line = f.readline().strip()
            print(line)
            print(line.split('\t'))


def read_crew():
    path = os.path.join(DATA_DIR, 'title_crew', 'data.tsv')
    print("Reading data from: ", path)
    with open(path, 'r') as f:
        for i in range(10):
            line = f.readline().strip()
            print(line)
            print(line.split('\t'))


def read_principals():
    path = os.path.join(DATA_DIR, 'title_principals', 'data.tsv')
    print("Reading data from: ", path)
    with open(path, 'r') as f:
        for i in range(10):
            line = f.readline().strip()
            print(line)
            print(line.split('\t'))


def read_crew():
    path = os.path.join(DATA_DIR, 'title_crew', 'data.tsv')
    print("Reading data from: ", path)
    with open(path, 'r') as f:
        for i in range(10):
            line = f.readline().strip()
            print(line)
            print(line.split('\t'))


def read_names():
    path = os.path.join(DATA_DIR, 'name_basics', 'data.tsv')
    print("Reading data from: ", path)
    with open(path, 'r') as f:
        for i in range(10):
            line = f.readline().strip()
            print(line)
            print(line.split('\t'))


def read_ratings():
    path = os.path.join(DATA_DIR, 'title_ratings', 'data.tsv')
    print("Reading data from: ", path)
    with open(path, 'r') as f:
        for i in range(10):
            line = f.readline().strip()
            print(line)
            print(line.split('\t'))
