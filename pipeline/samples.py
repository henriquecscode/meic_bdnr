import pandas as pd
import os

ROOT_DIR = '.'
DATA_DIR = os.path.join(ROOT_DIR, 'data')
PROCESSED_DIR = os.path.join(DATA_DIR, 'processed')

SAMPLE_SIZE = 100

### Titles
def sample_titles(sample_size = SAMPLE_SIZE):
    path = os.path.join(DATA_DIR, 'title_basics', 'data.tsv')
    print("Reading data from: ", path)
    df = pd.read_csv(path, sep='\t')
    print("Data read. Rows: ", len(df))
    print("Sample size: ", sample_size)
    input_df = df.sample(n = sample_size, random_state = 1)
    input_df['genres'] = input_df['genres'].str.replace(',', ';')
    input_df.to_csv(os.path.join(PROCESSED_DIR, 'titles.csv'), sep=',', index=False)
    return True

if __name__ == '__main__':
    sample_titles()
    print('HI')