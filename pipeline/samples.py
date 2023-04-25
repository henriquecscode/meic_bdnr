import pandas as pd
import os
from wikidata import *

ROOT_DIR = '.'
DATA_DIR = os.path.join(ROOT_DIR, 'data')
PROCESSED_DIR = os.path.join(DATA_DIR, 'processed')

SAMPLE_SIZE = 100

# Titles


def get_sample_titles():
    titles = set()
    path = os.path.join(PROCESSED_DIR, 'titles.csv')
    with open(path, 'r') as f:
        f.readline()
        while True:
            line = f.readline()
            if not line:
                break
            line = line.strip().split(',')
            title = line[0]
            titles.add(title)
    return titles


def get_sample_principals_people():
    people = set()
    path = os.path.join(PROCESSED_DIR, 'principals.csv')
    with open(path, 'r') as f:
        f.readline()
        while True:
            line = f.readline()
            if not line:
                break
            line = line.strip().split(',')
            principal = line[2]
            people.add(principal)
    return people


def get_sample_crew_people():
    people = set()
    path = os.path.join(PROCESSED_DIR, 'crew.csv')
    with open(path, 'r') as f:
        f.readline()
        while True:
            line = f.readline()
            if not line:
                break
            line = line.strip().split(',')
            directors = line[1].split(';')
            writers = line[2].split(';')
            people.update(directors)
            people.update(writers)
    return people


def get_all_samples_people():
    people = set()
    people.update(get_sample_principals_people())
    people.update(get_sample_crew_people())
    return people


def get_movies():
    # Read data
    path = os.path.join(DATA_DIR, 'title_basics', 'data.tsv')
    print("Reading data from: ", path)
    df = pd.read_csv(path, sep='\t')
    print("Data read. Rows: ", len(df))
    # Filter by movies
    df = df[df['titleType'] == 'movie']
    return df


def save_titles(df):

    # Save
    save_path = os.path.join(PROCESSED_DIR, 'titles.csv')
    df.to_csv(save_path,
              sep=',', index=False)
    print("Sample saved to: ", save_path)


def sample_titles(sample_size=SAMPLE_SIZE):

    df = get_movies()

    # Sample
    df = df.sample(n=sample_size, random_state=1)
    print("Sample size: ", sample_size)

    # Transform
    df['genres'] = df['genres'].str.replace(',', ';')
    save_titles(df)

    return df


def get_ratings():
    # Read data
    path = os.path.join(DATA_DIR, 'title_ratings', 'data.tsv')
    print("Reading data from: ", path)
    df = pd.read_csv(path, sep='\t')
    print("Data read. Rows: ", len(df))
    return df


def sample_better_titles(sample_size=SAMPLE_SIZE):
    df = get_movies()
    rating_df = get_ratings()

    # Merge
    df = pd.merge(df, rating_df, on='tconst', how='left')

    # Filter by rating
    df = df[df['averageRating'] >= 8.5]

    # Filter by number of votes
    df = df[df['numVotes'] >= 2000]

    print("Data filtered. Rows: ", len(df))
    # Sample
    df = df.sample(n=sample_size, random_state=1)
    print("Sample size: ", sample_size)

    # Transform
    df['genres'] = df['genres'].str.replace(',', ';')

    save_titles(df)
    return df

def sample_best_titles(sample_size=SAMPLE_SIZE):
    df = get_movies()
    rating_df = get_ratings()

    # Merge
    df = pd.merge(df, rating_df, on='tconst', how='left')

    # Order by rating and get the SAMPLE_size best
    df = df.sort_values(by=['averageRating'], ascending=False)
    df = df.head(sample_size)

    print("Data filtered. Rows: ", len(df))

    # Transform
    df['genres'] = df['genres'].str.replace(',', ';')

    save_titles(df)
    return df

def sample_principals():
    titles = get_sample_titles()
    path = os.path.join(DATA_DIR, 'title_principals', 'data.tsv')
    print("Reading data from: ", path)

    with open(path, 'r', encoding="utf8") as f:
        columns = f.readline().strip().split('\t')
        df = pd.DataFrame(columns=columns)
        data = []
        while True:
            line = f.readline()
            if not line:
                break
            line = line.strip().split('\t')
            if line[0] in titles:
                # Add to df
                line[5] = line[5].replace("\"", "").replace(
                    "[", "").replace("]", "")
                data.append(line)
    print("Data read. Rows: ", len(data))
    print("Creating dataframe...")
    df = pd.DataFrame(data, columns=columns)
    print("Dataframe created. Rows: ", len(df))
    # Save

    print("Saving sample...")
    save_path = os.path.join(PROCESSED_DIR, 'principals.csv')
    df.to_csv(save_path,
              sep=',', index=False)
    print("Sample saved to: ", save_path)
    return True


def sample_crew():
    titles = get_sample_titles()
    path = os.path.join(DATA_DIR, 'title_crew', 'data.tsv')
    print("Reading data from: ", path)

    with open(path, 'r', encoding="utf8") as f:
        columns = f.readline().strip().split('\t')
        df = pd.DataFrame(columns=columns)
        data = []
        while True:
            line = f.readline()
            if not line:
                break
            line = line.strip().split('\t')
            if line[0] in titles:
                # Add to df
                line[1] = line[1].replace(',', ';').replace('\"', '')
                line[2] = line[2].replace(',', ';').replace('\"', '')
                data.append(line)
    print("Data read. Rows: ", len(data))
    print("Creating dataframe...")
    df = pd.DataFrame(data, columns=columns)
    print("Dataframe created. Rows: ", len(df))

    # Save
    print("Saving sample...")
    save_path = os.path.join(PROCESSED_DIR, 'crew.csv')
    df.to_csv(save_path,
              sep=',', index=False)
    print("Sample saved to: ", save_path)
    return True


def sample_names():
    people = get_all_samples_people()
    path = os.path.join(DATA_DIR, 'name_basics', 'data.tsv')
    print("Reading data from: ", path)

    with open(path, 'r', encoding="utf8") as f:
        columns = f.readline().strip().split('\t')
        df = pd.DataFrame(columns=columns)
        data = []
        while True:
            line = f.readline()
            if not line:
                break
            line = line.strip().split('\t')
            if line[0] in people:
                # Add to df
                line[4] = line[4].replace(',', ';')
                line[5] = line[5].replace(',', ';')
                data.append(line)

    print("Data read. Rows: ", len(data))
    print("Creating dataframe...")
    df = pd.DataFrame(data, columns=columns)
    print("Dataframe created. Rows: ", len(df))

    # Save
    print("Saving sample...")
    save_path = os.path.join(PROCESSED_DIR, 'names.csv')
    df.to_csv(save_path,
              sep=',', index=False)


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


def get_titles_info(df=None):
    if df is None:
        path = os.path.join(PROCESSED_DIR, 'titles.csv')
        print("Reading data from: ", path)
        df = pd.read_csv(path, sep=',')
        print("Data read. Rows: ", len(df))
    else:
        print("Dataframe provided. Rows: ", len(df))
    df['wiki_film'] = ''
    df['wiki_series'] = ''
    df['wiki_country'] = ''
    df['wiki_genres'] = ''
    df['wiki_producer_company'] = ''
    df['wiki_location'] = ''
    # itter over df
    award_data = []
    for index, row in df.iterrows():
        title = row['tconst']
        info = film_info(title)
        row['wiki_film'] = info['film'] if 'film' in info else ''
        row['wiki_series'] = info['series'] if 'series' in info else ''
        row['wiki_country'] = info['country'] if 'country' in info else ''
        row['wiki_genres'] = info['genres'] if 'genres' in info else ''
        # row['wiki_genres'] = row['wiki_genres'].replace(',', ';')
        row['wiki_producer_company'] = info['producer_company'] if 'producer_company' in info else ''
        # row['wiki_producer_company'] = row['wiki_producer_company'].replace(',', ';')
        row['wiki_location'] = info['location'] if 'location' in info else ''
        if 'awards' not in info:
            continue
        for award in info['awards']:
            award_name = award['name_award'] if 'name_award' in award else ''
            award_date = award['received_on'] if 'received_on' in award else ''
            award_imdb = award['imbd_id'] if 'imbd_id' in award else ''
            award_data.append([title, award_name, award_date, award_imdb])

    award_df = pd.DataFrame(award_data, columns=[
                            'tconst', 'name_award', 'received_on', 'imbd_id'])

    # Save
    print("Saving film info and rewards...")
    df.to_csv(os.path.join(PROCESSED_DIR, 'film_info.csv'),
              sep=',', index=False)
    award_df.to_csv(os.path.join(
        PROCESSED_DIR, 'film_awards.csv'), sep=',', index=False)
    print("Saved")


def get_people_info(df = None):
    if df is None:
        path = os.path.join(PROCESSED_DIR, 'names.csv')
        print("Reading data from: ", path)
        df = pd.read_csv(path, sep=',')
        print("Data read. Rows: ", len(df))
    else:
        print("Dataframe provided. Rows: ", len(df))
    df['wiki_name'] = ''
    df['wiki_country'] = ''

    award_data = []
    for index, row in df.iterrows():
        name = row['nconst']
        info = actor_info(name)
        row['wiki_name'] = info['name'] if 'name' in info else ''
        row['wiki_country'] = info['country'] if 'country' in info else ''
        if 'awards' not in info:
            continue
        for award in info['awards']:
            award_name = award['name_award'] if 'name_award' in award else ''
            award_date = award['received_on'] if 'received_on' in award else ''
            award_imdb = award['imbd_id'] if 'imbd_id' in award else ''
            award_data.append([name, award_name, award_date, award_imdb])

    award_df = pd.DataFrame(award_data, columns=[
                            'nconst', 'name_award', 'received_on', 'imbd_id'])

    # Save
    print("Saving actor info and rewards...")
    df.to_csv(os.path.join(PROCESSED_DIR, 'actor_info.csv'),
              sep=',', index=False)
    award_df.to_csv(os.path.join(
        PROCESSED_DIR, 'actor_awards.csv'), sep=',', index=False)
    print("Saved")


def pipeline():
    # title_df = sample_better_titles()
    title_df = sample_best_titles()
    sample_principals()
    sample_crew()
    names_df = sample_names()
    get_titles_info(title_df)
    get_people_info(names_df)


if __name__ == '__main__':
    # Create a menu with the function sample titles
    op = -1
    while op != 0:
        print("1. Sample titles")
        print("2. Sample principals")
        print("3. Sample crew")
        print("4. Sample names")
        print("5. Get sample people")
        print("6. Get titles info")
        print("7. Sample better titles")
        print("8. Read titles")
        print("10. Read ratings")
        print("11. Pipeline")
        print("12. Get people info")
        print("0. Exit")
        op = int(input("Enter an option: "))
        if op == 1:
            sample_titles()
        if op == 2:
            # read_principals()
            sample_principals()
        if op == 3:
            # read_crew()
            sample_crew()
        if op == 4:
            # read_names()
            sample_names()
        if op == 5:
            get_sample_crew_people()
        if op == 6:
            get_titles_info()
        if op == 7:
            sample_better_titles()
        if op == 8:
            read_titles()
        if op == 10:
            read_ratings()
        if op == 11:
            pipeline()
        if op == 12:
            get_people_info()
