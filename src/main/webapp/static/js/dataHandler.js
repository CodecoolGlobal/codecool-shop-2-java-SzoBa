export let dataHandler = {

    _get: function (url, callback) {
        fetch(url)
            .then((response) => response.json())
            .then((response) => callback(response))
    },

    getCountry: function (typeId, countryId, callback) {
        this._get(`/search?typeId=${typeId}&countryId=${countryId}`, (response) => {
            callback(response);
        });
    },

    modifyCartItems: function (matchID, isAdded, outcome, callback) {
        this._get(`/modifyCart?matchID=${matchID}&isAdded=${isAdded}&outcome=${outcome}`, (response) => {
            callback(response);
        });
    },

    getCartItems: function (callback) {
        this._get(`/getCartItems`, (response) => {
            callback(response);
        });
    },

    getCartContent: function (callback) {
        this._get(`/getCartItems`, (response) => {
            callback(response);
        })
    }
}