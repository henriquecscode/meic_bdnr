export default class API {
  static async request(method, endpoint, successCallback, errorCallback, data = null) {
    fetch(endpoint, {
      method: method,
      headers: {
        "Content-Type": "application/json",
        "Access-Control-Allow-Origin": "*",
      },
      body: JSON.stringify(data),
    })
      .then((response) => response.json())
      .then((json) => {
        if ("function" === typeof successCallback) {
          successCallback(json);
        }
      })
      .catch((error) => {
        if ("function" === typeof errorCallback) {
          errorCallback(error);
        }
      });
  }
}
