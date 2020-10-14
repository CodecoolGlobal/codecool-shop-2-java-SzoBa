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
            //refreshSelected
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
            if (!content.classList.contains("hidden")) {
                dataHandler.getCartContent((data) => {
                    dom.clearTicketContentBody();
                    dom.createTicketData(data);
                })
            }
        }
    },

    clearMatches: function () {
        document.getElementById("matches").innerHTML = "";

    },

    clearTicketContentBody: function () {
        let content = document.querySelector(".cart-content-body");
        content.innerHTML =
            `<div class="cart-content-matches">
            <ul></ul>
            </div>`;
    },

    createMatches: function (matches) {
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
                                <span>D</span>
                                <br>
                                    <span>${match.drawOdds}</span>
                            </a>
                        </div>
                        <div class="card-odds" data-outcome="away">
                            <a class="btn btn-success" href="#">
                                <span>V</span>
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

        if (!event.target.closest("a").classList.contains("inactive")) {
            dataHandler.modifyCartItems(matchID, isAdded, outcome, () => {
                isAdded ? event.target.closest("a").classList.remove("active") :
                    event.target.closest("a").classList.add("active");

                addOrRemoveInactiveFromOdds(event, isAdded);
                let betCounter = document.querySelector(".cart-item-counter");
                changeBetCounter(isAdded, betCounter);
            })
        }
    },

    createTicketData: function (data) {
        this.createCartMatchesList(data.items);
        this.addCartConstantItems(data);
    },

    createCartMatchesList: function (matches) {
        let matchList = document.querySelector(".cart-content-matches ul");
        let content = "";
        for (let match of matches) {
            let addMatch = `
            <li>${match.home} - ${match.away} Chosen: ${match.chosenOutcome}, Odds ${match.odds}
            <a><img src="/static/img/trashbin3.png"" width="20" height="20"
    alt="delete-tag"></a></li>`
            content += addMatch;
        }
        matchList.insertAdjacentHTML("beforeend", content);
    },

    addCartConstantItems: function (data) {
        let content = document.querySelector(".cart-content-body");
        let items =
            `<label for="betValue">Current bet:  </label>
             <input id="betValue" name="betValue" type="text" placeholder="${data.bet}">
             <a href="/"><button>Confirm Order</button></a>`
        content.insertAdjacentHTML("beforeend", items);
        this.addListenersToBetInput();
    },

    addListenersToBetInput: function () {
        let input = document.querySelector("#betValue");
        input.addEventListener("keydown", (event) => {
            if (event.keyCode === 13 && /^\d+$/.test(input.value)) {
                //TODO save bet to database
            }
        })

    }
}

function addOrRemoveInactiveFromOdds(event, isAdded) {
    if (isAdded) {
        let matchOdds = event.target.closest(".card-body").querySelectorAll("a");
        for (let odd of matchOdds) {
            if (odd.classList.contains("inactive")) {
                odd.classList.remove("inactive");
            }
        }
    } else {
        let matchOdds = event.target.closest(".card-body").querySelectorAll("a");
        for (let odd of matchOdds) {
            if (!odd.classList.contains("active")) {
                odd.classList.add("inactive");
            }
        }
    }
}

function changeBetCounter(isAdded, betCounter) {
    if (isAdded) {
        betCounter.innerHTML = (parseInt(betCounter.innerHTML) - 1).toString();
    } else {
        betCounter.innerHTML = (parseInt(betCounter.innerHTML) + 1).toString();
    }
}
