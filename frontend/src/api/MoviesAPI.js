import API from "./API";

export default class MoviesAPI {
  BASE_URL = "/movie";

  // Movie
  getFilm(id, successCallback, errorCallback) {
    let endpoint = this.BASE_URL + `/${id}`;
    API.request("GET", endpoint, successCallback, errorCallback);
  }

  // Search
  getSearch(successCallback, errorCallback, body) {
    let endpoint = this.BASE_URL + `/search`;
    API.request("POST", endpoint, successCallback, errorCallback, body);
  }

  getGenres(successCallback, errorCallback) {
    let endpoint = `/genre`;
    API.request("GET", endpoint, successCallback, errorCallback);
  }

}
