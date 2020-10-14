import {dataHandler} from "./dataHandler.js";

export let dom = {
    init: function () {
        this.addConstantListeners();
        this.addTemporaryListeners();
    },

    addConstantListeners: function () {
        document.getElementById("country-select").addEventListener("click", this.addListenerToCountrySelect);
        document.getElementById("sport-select").addEventListener("click", this.addListenerToSportSelect);
        document.querySelector(".cart-container").addEventListener("click", this.addListenerToCart);
    },

    addTemporaryListeners: function () {
        this.addListenerToOdds();

    },

    addListenerToOdds: function () {
        const odds = document.querySelectorAll('.card-odds')
        for (const odd of odds) {
            odd.addEventListener("click", this.modifyCartItems);
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

    addListenerToCart: function (event) {
        let content = document.querySelector(".cart-content");
        if (event.target.classList.contains("cart-container") || event.target.classList.contains("cart-item-counter")
            || event.target.classList.contains("cart-title")) {
            content.classList.toggle("hidden");
        }
        if (!content.classList.contains("hidden")) {
            //Currently just one player, no need for option, only when login implemented
            dataHandler.getCartContent(id, (data) => {
                dom.clearTicketContentBody();
                dom.createTicketData(data);
            })
        }
    },

    clearMatches: function () {
        document.getElementById("matches").innerHTML = "";

    },

    clearTicketContentBody: function () {
        let content = document.querySelector(".cart-content");
        content.innerHTML = "";
    },

    createMatches: function (matches) {
        console.log(matches)
        let mainContent = "";
        for (let match of matches) {
            let matchContent = `
            <div class="col col-sm-12 col-md-6 col-lg-4">
                <div class="card" data-matchId="${match.id}">
                    <div class="card-header">
                        <h4 class="card-title">${match.homeTeam} - ${match.awayTeam}</h4>
                        <p class="card-text">${match.description}</p>
                    </div>
                    <div class="card-body">
                        <div class="card-odds" data-outcome="home">
                            <a class="btn btn-success" href="#">
                                <span>H</span>
                                <br>
                                    <span>${match.homeOdds}</span>
                            </a>
                        </div>
                        <div class="card-odds ${match.drawOdds === 1 ? "hidden" : ""}" data-outcome="draw">
                            <a class="btn btn-success" href="#">
                                <span>H</span>
                                <br>
                                    <span>${match.drawOdds}</span>
                            </a>
                        </div>
                        <div class="card-odds" data-outcome="away">
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
        this.addTemporaryListeners();
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
    },

    createTicketData: function () {
        let content = document.querySelector(".cart-content");
    }
}
