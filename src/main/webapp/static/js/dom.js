import {dataHandler} from "./dataHandler.js";

export let dom = {
    init: function () {
        this.addListeners();
    },

    addListeners: function () {
        document.getElementById("country-select").addEventListener("click", this.addListenerToCountrySelect);
    },

    addListenerToCountrySelect: function () {
        let item = document.getElementById("country-select");
        dataHandler.getCountry(item.options[item.selectedIndex].value, (data) => {
            console.log(data);
        })
    }

}
