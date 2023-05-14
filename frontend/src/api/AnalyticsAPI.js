import API from "./API";

export default class AnalyticsAPI {
  BASE_URL = "/analytics";

  // Friends
  getFriendsWatchedSeries(user, successCallback, errorCallback) {
    let endpoint = this.BASE_URL + `/friends/${user}/series/complete`;
    API.request("GET", endpoint, successCallback, errorCallback);
  }

  // Cast
  getWorkersCountry(successCallback, errorCallback) {
    let endpoint = this.BASE_URL + `/workers/country`;
    API.request("GET", endpoint, successCallback, errorCallback);
  }

  // Awards
  getGenreAwards(limit, successCallback, errorCallback) {
    let endpoint = this.BASE_URL + `/awards/genre/${limit}`;
    API.request("GET", endpoint, successCallback, errorCallback);
  }

  getWorkersAwards(limit, successCallback, errorCallback) {
    let endpoint = this.BASE_URL + `/awards/worker/${limit}`;
    API.request("GET", endpoint, successCallback, errorCallback);
  }

  getCountryAwards(limit, successCallback, errorCallback) {
    let endpoint = this.BASE_URL + `/awards/country/${limit}`;
    API.request("GET", endpoint, successCallback, errorCallback);
  }
}
