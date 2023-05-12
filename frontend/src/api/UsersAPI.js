import API from "./API";

export default class UsersAPI {
  PROFILE_BASE_URL = "/profile";

  constructor(username) {
    this.PROFILE_BASE_URL = this.PROFILE_BASE_URL + `/${username}`;
  }

  getProfile(successCallback, errorCallback) {
    let endpoint = this.PROFILE_BASE_URL;
    API.request("GET", endpoint, successCallback, errorCallback);
  }

  addFriend(friend, successCallback, errorCallback) {
    let endpoint = this.PROFILE_BASE_URL + `/friends/${friend}/add`;
    API.request("POST", endpoint, successCallback, errorCallback);
  }

  removeFriend(friend, successCallback, errorCallback) {
    let endpoint = this.PROFILE_BASE_URL + `/friends/${friend}/remove`;
    API.request("POST", endpoint, successCallback, errorCallback);
  }
}
