import { API_URL } from "@env";

export default class API {

  static async request(method, query, successCallback, errorCallback) {
    let url = API_URL + query;

    fetch(url, {
      method: method,
      headers: {
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin': '*',
      },
    })
      .then((response) => response.json())
      .then((json) => { 
        if ('function' === typeof successCallback) {
          successCallback(json['response']); 
        }
      })
      .catch((error) => {
        if ('function' === typeof errorCallback) {
          errorCallback(error);
        }
      });
  }
}
