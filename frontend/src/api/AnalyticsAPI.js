import API from './API';


export default class AnalyticsAPI {
  BASE_URL = '/analytics/';

  constructor() { }

  getFriendsWatchedSeries(user, successCallback, errorCallback) {
    let query = this.BASE_URL + `friends/${user}/movies/series/complete`;
    API.request('GET', query, successCallback, errorCallback);
  }

  getWorkersCountry(successCallback, errorCallback) {
    let query = `/workers/country`;
    API.request('GET', query, successCallback, errorCallback);
  }

  getGenreAwards(limit, successCallback, errorCallback) {
    let query = `/awards/genre/${limit}`;
    API.request('GET', query, successCallback, errorCallback);
  }

  getWorkersAwards(limit, successCallback, errorCallback) {
    let query = `/awards/workers/${limit}`;
    API.request('GET', query, successCallback, errorCallback);
  }

  getWorkersAwards(limit, successCallback, errorCallback) {
    let query = `/awards/country/${limit}`;
    API.request('GET', query, successCallback, errorCallback);
  }
}
