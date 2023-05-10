import API from "./API";

export default class AnalyticsAPI {
  BASE_URL = "/analytics";

  // constructor() { }

  getFriendsWatchedSeries(user, successCallback, errorCallback) {
    let endpoint = this.BASE_URL + `/friends/${user}/movies/series/complete`;
    API.request("GET", endpoint, successCallback, errorCallback);
  }

  getWorkersCountry(successCallback, errorCallback) {
    let endpoint = this.BASE_URL + `/workers/country`;
    API.request("GET", endpoint, successCallback, errorCallback);
  }

  getGenreAwards(limit, successCallback, errorCallback) {
    let endpoint = this.BASE_URL + `/awards/genre/${limit}`;
    API.request("GET", endpoint, successCallback, errorCallback);
  }

  getWorkersAwards(limit, successCallback, errorCallback) {
    let endpoint = this.BASE_URL + `/awards/workers/${limit}`;
    API.request("GET", endpoint, successCallback, errorCallback);
  }

  getCountryAwards(limit, successCallback, errorCallback) {
    let endpoint = this.BASE_URL + `/awards/country/${limit}`;
    API.request("GET", endpoint, successCallback, errorCallback);
  }
}
