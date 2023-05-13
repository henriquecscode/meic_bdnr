export default class API {
  static async request(
    method,
    endpoint,
    successCallback,
    errorCallback,
    data = null
  ) {
    fetch(endpoint, {
      method: method,
      headers: {
        "Content-Type": "application/json",
        "Access-Control-Allow-Origin": "*",
      },
      body: data ? JSON.stringify(data) : null,
    })
      .then((response) => response.json())
      .then((json) => {
        if (json.status === 500) {
          if ("function" === typeof errorCallback) {
            errorCallback(json);
          }
        } else {
          if ("function" === typeof successCallback) {
            successCallback(json);
          }
        }
      })
      .catch((error) => {
        if ("function" === typeof errorCallback) {
          errorCallback(error);
        }
      });
  }
}
