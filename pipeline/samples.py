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


def save(df, path, append=False):
    print("Sanitizing data")
    df = sanitize_df(df)
    print("Saving sample to: ", path)
    if not append:
        df.to_csv(path, sep=',', index=False)
    else:
        df.to_csv(path, sep=',', index=False, mode='a',
                  header=not os.path.exists(path))
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
        save_append = False
        path = os.path.join(PROCESSED_DIR, 'titles.csv')
        print("Reading data from: ", path)
        df = pd.read_csv(path, sep=',')
        print("Data read. Rows: ", len(df))
    else:
        save_append = True
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
    titles_save = save(df, os.path.join(
        PROCESSED_DIR, 'titles_info.csv'), save_append)
    titles_awards_save = save(award_df, os.path.join(
        PROCESSED_DIR, 'titles_awards.csv'), save_append)
    return [titles_save, titles_awards_save]


def get_people_info(df=None):
    if df is None:
        save_append = False
        path = os.path.join(PROCESSED_DIR, 'names.csv')
        print("Reading data from: ", path)
        df = pd.read_csv(path, sep=',')
        print("Data read. Rows: ", len(df))
    else:
        save_append = True
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
            award_imdb = award['imdb_id'] if 'imdb_id' in award else ''
            award_data.append([name, award_name, award_date, award_imdb])

    award_df = pd.DataFrame(award_data, columns=[
                            'nconst', 'name_award', 'received_on', 'imbd_id'])

    # Save
    print("Saving actor info and rewards...")
    names_save = save(df, os.path.join(
        PROCESSED_DIR, 'names_info.csv'), save_append)
    names_awards_save = save(award_df, os.path.join(
        PROCESSED_DIR, 'names_awards.csv'), save_append)
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


def get_titles_data(titles_df, titles_set):
    sample_titles_df = titles_df[titles_df['tconst'].isin(titles_set)]

    # Transform
    sample_titles_df['genres'] = sample_titles_df['genres'].str.replace(
        ',', ';')
    return sample_titles_df


def get_crew_data(titles=None, crew_df=None):
    if titles is None:
        print("Getting sample titles")
        titles = get_sample_titles()
        print("Sample titles: ", len(titles))
    else:
        print("Titles provided. Titles: ", len(titles))
    if crew_df is None:
        path = os.path.join(DATA_DIR, 'title_crew', 'data.tsv')
        print("Reading data from: ", path)
        crew_df = pd.read_csv(path, sep='\t')
        print("Data read. Rows: ", len(crew_df))
    else:
        print("Dataframe provided. Rows: ", len(crew_df))

    crew_df = crew_df[crew_df['tconst'].isin(titles)]
    return crew_df


def get_principals_data(titles_set=None, principles_df=None):
    if titles_set is None:
        print("Getting sample titles")
        titles_set = get_sample_titles()
        print("Sample titles: ", len(titles_set))
    else:
        print("Titles provided. Titles: ", len(titles_set))
    if principles_df is None:
        path = os.path.join(DATA_DIR, 'title_principals', 'data.tsv')
        print("Reading data from: ", path)
        principles_df = pd.read_csv(path, sep='\t')
        print("Data read. Rows: ", len(principles_df))
    else:
        print("Dataframe provided. Rows: ", len(principles_df))

    principles_df = principles_df[principles_df['tconst'].isin(titles_set)]
    return principles_df


def get_names_data(people=None, names_df=None):
    if people is None:
        print("Getting sample people")
        people = get_all_samples_people()
        print("Sample people: ", len(people))
    else:
        print("People provided. People: ", len(people))
    if names_df is None:
        path = os.path.join(DATA_DIR, 'name_basics', 'data.tsv')
        print("Reading data from: ", path)
        names_df = pd.read_csv(path, sep='\t')
        print("Data read. Rows: ", len(names_df))
    else:
        print("Dataframe provided. Rows: ", len(names_df))

    names_df = names_df[names_df['nconst'].isin(people)]
    return names_df


def get_titles_from_name_awards(names_awards_df=None):
    if names_awards_df is None:
        save_append = False
        path = os.path.join(PROCESSED_DIR, 'names_awards.csv')
        print("Reading data from: ", path)
        names_awards_df = pd.read_csv(path, sep=',')
        print("Data read. Rows: ", len(names_awards_df))
    else:
        save_append = True
        print("Dataframe provided. Rows: ", len(names_awards_df))

    # Get titles
    titles = set()
    for index, row in names_awards_df.iterrows():
        title = row['imbd_id']
        if pd.isna(title):
            continue
        if title not in titles:
            titles.add(title)
    print("Titles: ", len(titles))

    return titles


def get_names_from_crew(crew_df=None):
    names = set()
    for index, row in crew_df.iterrows():
        directors = row['directors']
        writers = row['writers']
        directors_names = directors.split(';')
        writers_names = writers.split(';')
        for name in directors_names:
            if name not in names:
                names.add(name)
        for name in writers_names:
            if name not in names:
                names.add(name)
    print("Crew Names", len(names))
    return names


def get_names_from_principals(principals_df=None):
    names = set()
    for index, row in principals_df.iterrows():
        name = row['nconst']
        if name not in names:
            names.add(name)
    print("Principal Names: ", len(names))
    return names


def recursive_data_completion(titles=None, names=None, names_awards=None, max_titles=SAMPLE_SIZE*2):

    print("Reading processed data")
    if titles is None:
        info_titles_df = pd.read_csv(os.path.join(
            PROCESSED_DIR, 'titles_info.csv'), sep=',')
        titles = set(info_titles_df['tconst'])
    if names is None:
        info_names_df = pd.read_csv(os.path.join(
            PROCESSED_DIR, 'names_info.csv'), sep=',')
        names = set(info_names_df['nconst'])
    if names_awards is None:
        names_awards = pd.read_csv(os.path.join(
            PROCESSED_DIR, 'names_awards.csv'), sep=',')

    print("Reading raw data")
    data_titles_df = pd.read_csv(os.path.join(
        DATA_DIR, 'title_basics', 'data.tsv'), sep='\t')
    print("Title data read. Rows: ", len(data_titles_df))
    data_crew_df = pd.read_csv(os.path.join(
        DATA_DIR, 'title_crew', 'data.tsv'), sep='\t')
    print("Crew data read. Rows: ", len(data_crew_df))
    data_principals_df = pd.read_csv(os.path.join(
        DATA_DIR, 'title_principals', 'data.tsv'), sep='\t')
    print("Principals data read. Rows: ", len(data_principals_df))
    data_names_df = pd.read_csv(os.path.join(
        DATA_DIR, 'name_basics', 'data.tsv'), sep='\t')
    print("Name data read. Rows: ", len(data_names_df))
    new_titles = set()
    new_names = set()

    print("Starting recursive data completion")
    count = 0
    while (len(titles) < max_titles):
        print(f"Iteration {count}")
        new_titles_from_awards = get_titles_from_name_awards(names_awards)

        new_titles = new_titles_from_awards - titles
        print(f"Got {len(new_titles)} new titles")

        if len(new_titles) == 0:
            break
        titles_df = get_titles_data(data_titles_df, new_titles)

        crews_df = get_crew_data(new_titles, data_crew_df)
        principals_df = get_principals_data(new_titles, data_principals_df)

        new_names_from_award_titles = get_names_from_crew(crews_df)
        new_names_from_award_principals = get_names_from_principals(
            principals_df)
        new_names = new_names_from_award_titles.union(
            new_names_from_award_principals) - names

        print(f"Got {len(new_names)} new names")
        if len(new_names) == 0:
            break

        names_df = get_names_data(new_names, data_names_df)

        get_titles_info(titles_df)
        _, names_awards = get_people_info(names_df)
        save(crews_df, os.path.join(PROCESSED_DIR, 'crews.csv'), True)
        save(principals_df, os.path.join(PROCESSED_DIR, 'principals.csv'), True)

        names = names.union(new_names)
        titles = titles.union(new_titles)
        count += 1


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
        ["Info Pipeline", info_pipeline],
        ["Recursive data completion", recursive_data_completion]


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
