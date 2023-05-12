import API from "./API";

export default class RecommendationsAPI {
  BASE_URL = "/recommendations";

  constructor(username) {
    this.BASE_URL = this.BASE_URL + `/${username}`;
  }

  getFriendsFilms(level, successCallback, errorCallback) {
    let endpoint = this.BASE_URL + `/movies/friends/${level}`;
    API.request("GET", endpoint, successCallback, errorCallback);
  }

  getCountryFilms(successCallback, errorCallback) {
    let endpoint = this.BASE_URL + `/movies/country`;
    API.request("GET", endpoint, successCallback, errorCallback);
  }

  getAdviceFilms(successCallback, errorCallback) {
    let endpoint = this.BASE_URL + `/movies/advise`;
    API.request("GET", endpoint, successCallback, errorCallback);
  }
}
