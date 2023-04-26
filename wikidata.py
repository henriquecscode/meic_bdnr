import mkwikidata
import pandas as pd

def get_awards(imdb_id): 
    awards_query = f"""
        SELECT ?awardLabel ?dateLabel ?workLabel ?workIMDbId
        WHERE 
        {{
            ?p wdt:P345 "{imdb_id}" ;
               p:P166 ?s .
            ?s ps:P166 ?award .
            OPTIONAL {{ 
                ?s pq:P585 ?date .
            }}
            OPTIONAL {{
                ?s pq:P1686 ?work .    # P1686 = for work
                ?work wdt:P345 ?workIMDbId . 
            }}
            SERVICE wikibase:label {{ bd:serviceParam wikibase:language "[AUTO_LANGUAGE],en". }} # Helps get the label in your language, if not, then en language      
        }}
        """

    awards_result = mkwikidata.run_query(awards_query, params={ })

    return [{"name_award": x["awardLabel"]["value"], "received_on": x["dateLabel"]["value"] if "dateLabel" in x else None, "imdb_id":  x["workIMDbId"]["value"] if "workLabel" in x else None} for x in awards_result["results"]["bindings"]]

def actor_info(imdb_id):
    # Add country info
    query = f"""
        SELECT ?pLabel ?countryLabel
        WHERE 
        {{
            ?p wdt:P345 "{imdb_id}" ;
                wdt:P27 ?country ;
            SERVICE wikibase:label {{ bd:serviceParam wikibase:language "[AUTO_LANGUAGE],en". }} # Helps get the label in your language, if not, then en language      
        }}
        """
    query_result = mkwikidata.run_query(query, params={ })

    data = [{"name" : x["pLabel"]["value"], "country" : x["countryLabel"]["value"]} for x in query_result["results"]["bindings"]]

    # Add awards info
    data[0]["awards"] = get_awards(imdb_id)
    
    df = pd.DataFrame(data)
    print(df)


#title P1476, summary P921 (subject?), duration P2047, release P577
def film_info(imdb_id):
    query = f"""
        SELECT ?filmLabel ?seriesLabel ?nrSerieLabel ?countryLabel ?genreLabel ?locationLabel ?producerCompanyLabel
        WHERE 
        {{
            ?film wdt:P345 "{imdb_id}" ;
                wdt:P136 ?genre  ;   # P136 = genre
                wdt:P495 ?country ;  # P495 = country
                wdt:P272 ?producerCompany ; # P272 = production company
                wdt:P915 ?location ; # P915 = filming location

            OPTIONAL {{
                ?film p:P179 ?s .   
                ?s ps:P179 ?series .   # P179 = part of series
                ?s pq:P1545 ?nrSerie
            }}
            SERVICE wikibase:label {{ bd:serviceParam wikibase:language "[AUTO_LANGUAGE],en". }} # Helps get the label in your language, if not, then en language      
        }}
        """
    
    query_result = mkwikidata.run_query(query, params={ })

    data = [{"film" : x["filmLabel"]["value"], "series" : x["seriesLabel"]["value"] if "seriesLabel" in x else None, "seriesNr" : x["nrSerieLabel"]["value"]  if "nrSerieLabel" in x else None, "country" :  x["countryLabel"]["value"]} for x in query_result["results"]["bindings"]]
    data[0]["genres"] = list(set([x["genreLabel"]["value"] for x in query_result["results"]["bindings"]]))
    data[0]["producer company"] = list(set([x["producerCompanyLabel"]["value"] for x in query_result["results"]["bindings"]]))
    data[0]["location"] = list(set([x["locationLabel"]["value"] for x in query_result["results"]["bindings"]]))
    data[0]["awards"] = get_awards(imdb_id)

    df = pd.DataFrame(data)
    
    print(df.iloc[:1])


# Jennifer Lawrence Info
actor_info("nm2225369")

# Avatar Info
film_info("tt0499549")