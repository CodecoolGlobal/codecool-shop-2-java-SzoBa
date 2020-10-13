export let dataHandler = {

    _get: function (url, callback) {
        fetch(url)
            .then((response) => response.json())
            .then((response) => callback(response))
    },

    getCountry: function (countryId, callback) {
        this._get(`/country?countryId=${countryId}`, (response) => {
            callback(response);
        });
    },


}