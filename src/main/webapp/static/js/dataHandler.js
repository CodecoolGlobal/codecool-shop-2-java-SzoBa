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

    modifyCartItems: function (matchID, isAdded, outcome, callback) {
        this._get(`/modifyCart?matchID=${matchID}&isAdded=${isAdded}&outcome=${outcome}`, (response) => {
            callback(response);
        });
    }


}