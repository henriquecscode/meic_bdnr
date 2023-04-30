import pandas as pd
import os
from read import *
from wikidata import *

ROOT_DIR = '.'
DATA_DIR = os.path.join(ROOT_DIR, 'data')
PROCESSED_DIR = os.path.join(DATA_DIR, 'processed')

SAMPLE_SIZE = 100

# Titles


def sanitize_string(string):
    return string.replace(",", "").replace('"', '').replace("'", "")


def sanitize_row(row):
    return pd.Series([sanitize_string(value) if type(value) is str else value for value in row])


def sanitize_df(df):
    return df.apply(sanitize_row, axis=0)


def save(df, path):
    print("Sanitizing data")
    df = sanitize_df(df)
    print("Saving sample to: ", path)
    df.to_csv(path, sep=',', index=False)
    print("Sample saved")
    return df


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
    return save(df, save_path)


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

    return save_titles(df)


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
                for i, string in enumerate(line):
                    line[i] = sanitize_string(string)

                data.append(line)
    print("Data read. Rows: ", len(data))
    print("Creating dataframe...")
    df = pd.DataFrame(data, columns=columns)
    print("Dataframe created. Rows: ", len(df))
    # Save

    save_path = os.path.join(PROCESSED_DIR, 'principals.csv')
    return save(df, save_path)


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
    save_path = os.path.join(PROCESSED_DIR, 'crew.csv')
    return save(df, save_path)


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
    save_path = os.path.join(PROCESSED_DIR, 'names.csv')
    return save(df, save_path)


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
        row['wiki_genres'] = ';'.join(
            info['genres']) if 'genres' in info else ''
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
    titles_save = save(df, os.path.join(PROCESSED_DIR, 'titles_info.csv'))
    titles_awards_save = save(award_df, os.path.join(
        PROCESSED_DIR, 'titles_awards.csv'))
    return [titles_save, titles_awards_save]


def get_people_info(df=None):
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
    names_save = save(df, os.path.join(PROCESSED_DIR, 'names_info.csv'))
    names_awards_save = save(award_df, os.path.join(
        PROCESSED_DIR, 'names_awards.csv'))
    return [names_save, names_awards_save]


def pipeline():
    # title_df = sample_better_titles()
    title_df = sample_best_titles()
    sample_principals()
    sample_crew()
    names_df = sample_names()
    get_titles_info(title_df)
    get_people_info(names_df)
    print("Pipeline finished.")


def info_pipeline():
    title_df = pd.read_csv(os.path.join(PROCESSED_DIR, 'titles.csv'), sep=',')
    names_df = pd.read_csv(os.path.join(PROCESSED_DIR, 'names.csv'), sep=',')
    get_titles_info(title_df)
    get_people_info(names_df)
    print("Info pipeline finished.")


if __name__ == '__main__':
    options = [
        ["Sample titles", sample_titles],
        ["Sample better titles", sample_better_titles],
        ["Sample best titles", sample_best_titles],
        ["Sample principals", sample_principals],
        ["Sample crew", sample_crew],
        ["Sample names", sample_names],
        ["Get sample people", get_sample_crew_people],
        ["Get titles info", get_titles_info],
        ["Get people info", get_people_info],
        ["Read titles", read_titles],
        ["Read ratings", read_ratings],
        ["Pipeline", pipeline],
        ["Info Pipeline", info_pipeline]


    ]
    # Create a menu with the function sample titles
    op = -1
    while op != 0:
        print("Options:")
        for i, option in enumerate(options):
            print(i+1, "-", option[0])
        print("0 - Exit")
        try:
            op = int(input("Enter an option: "))
        except Exception as e:
            print("Invalid option")
            continue
        try:
            if op == 0:
                break
            options[op-1][1]()
        except Exception as e:
            print(e)
            continue
    print("Exiting")
