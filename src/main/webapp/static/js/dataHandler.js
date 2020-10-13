export let dataHandler = {

    _get: function (url, callback) {
        fetch(url)
            .then((response) => response.json())
            .then((response) => callback(response))
    },

    getNews: function (page, callback) {
        page = parseInt(page) < 1 ? null : page;
        this._get(`/api/top?page=${page}`, (response) => {
            callback(response);
        });
    },


}