import {dataHandler} from "./dataHandler.js";

export let dom = {
    init: function () {
        this.addListeners();
    },

    addListeners: function () {
        document.getElementById("country-select").addEventListener("click", this.addListenerToCountrySelect);
        const odds = document.querySelectorAll('.card-odds')
        for (const odd of odds) {
            odd.addEventListener("click", event => this.modifyCartItems(event));

        }
    },

    addListenerToCountrySelect: function () {
        let item = document.getElementById("country-select");
        dataHandler.getCountry(item.options[item.selectedIndex].value, (data) => {
            console.log(data);
        })
    },

    modifyCartItems(event) {
        let matchID = event.target.closest(".card").dataset.matchid;
        let isAdded = event.target.classList.contains("active");
        let outcome = event.target.dataset.outcome;
        dataHandler.modifyCartItems(matchID, isAdded, outcome, data => console.log(data))
    }
}
