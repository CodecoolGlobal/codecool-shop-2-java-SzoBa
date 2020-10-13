import {dataHandler} from "./dataHandler.js";

export let dom = {
    init: function () {
        this.addListeners();
    },

    addListeners: function () {
        document.getElementById("country-select").addEventListener("click", this.addListenerToCountrySelect);
        document.getElementById("sport-select").addEventListener("click", this.addListenerToSportSelect);
        this.addListenerToOdds();
    },

    addListenerToOdds: function () {
        const odds = document.querySelectorAll('.card-odds')
        for (const odd of odds) {
            odd.addEventListener("click", event => this.modifyCartItems(event));

        }

    },

    addListenerToCountrySelect: function () {
        let item = document.getElementById("country-select");
        dataHandler.getCountry(item.options[item.selectedIndex].value, (data) => {
            dom.clearMatches();
            dom.createMatches(data);
        })
    },

    addListenerToSportSelect() {
        let item = document.getElementById("sport-select");
        dataHandler.getSportType(item.options[item.selectedIndex].value, (data) => {
            dom.clearMatches();
            dom.createMatches(data);
        })
    },

    clearMatches: function () {
        document.getElementById("matches").innerHTML = "";

    },
    createMatches: function (matches) {
        let mainContent = "";
        for (let match of matches) {
            let matchContent = `
            <div class="col col-sm-12 col-md-6 col-lg-4">
                <div class="card">
                    <div class="card-header">
                        <h4 class="card-title">${match.homeTeam} - ${match.awayTeam}</h4>
                        <p class="card-text">${match.description}</p>
                    </div>
                    <div class="card-body">
                        <div class="card-odds">
                            <a class="btn btn-success" href="#">
                                <span>H</span>
                                <br>
                                    <span>${match.homeOdds}</span>
                            </a>
                        </div>
                        <div class="card-odds ${match.drawOdds === 1 ? "hidden" : ""}">
                            <a class="btn btn-success" href="#">
                                <span>H</span>
                                <br>
                                    <span>${match.drawOdds}</span>
                            </a>
                        </div>
                        <div class="card-odds">
                            <a class="btn btn-success" href="#">
                                <span>H</span>
                                <br>
                                    <span>${match.awayOdds}</span>
                            </a>
                        </div>
                    </div>
                </div>
            </div>`
            mainContent += matchContent;
        }
        document.getElementById("matches").insertAdjacentHTML("beforeend", mainContent);
    },

    modifyCartItems(event) {
        let matchID = event.target.closest(".card").dataset.matchid;
        let isAdded = event.target.closest("a").classList.contains("active");
        let outcome = event.target.closest(".card-odds").dataset.outcome;
        dataHandler.modifyCartItems(matchID, isAdded, outcome, () => {
            isAdded ? event.target.closest("a").classList.remove("active") :
                        event.target.closest("a").classList.add("active");
            let betCounter = document.querySelector(".cart-item-counter");
            if (isAdded) {
                betCounter.innerHTML = (parseInt(betCounter.innerHTML) - 1).toString();
            } else {
                betCounter.innerHTML = (parseInt(betCounter.innerHTML) + 1).toString();
            }
        })
    }
}
