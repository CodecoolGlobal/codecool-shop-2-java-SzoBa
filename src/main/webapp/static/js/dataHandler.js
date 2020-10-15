export let dataHandler = {

    _get: function (url, callback) {
        fetch(url)
            .then((response) => response.json())
            .then((response) => callback(response))
    },

    _post: function (url, data, callback) {
        fetch(url, {
            method: 'POST',
            credentials: 'same-origin',
            headers: {
                'Accept': 'application/json, text/plain, */*',
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(data),
        })
            .then(response => response.json())
            .then((result) => callback(result))
            .catch((error) => {
                console.error('Error:', error);
            })
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
    },
    saveBet(orderInfos, callback) {
        this._post('/save_bet', orderInfos, (response) => {
            callback(response);
        })

    }
}