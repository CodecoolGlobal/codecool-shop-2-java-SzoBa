import {dataHandler} from "./dataHandler.js";

export let dom = {
    init: function () {
        this.addConstantListeners();
        this.addTemporaryListeners();
        checkIfOddsAlreadySelected();
    },

    addConstantListeners: function () {
        document.getElementById("country-select").addEventListener("click", this.addListenerToCountrySelect);
        document.getElementById("sport-select").addEventListener("click", this.addListenerToSportSelect);
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
            checkIfOddsAlreadySelected();
        })
    },

    addListenerToSportSelect() {
        let item = document.getElementById("sport-select");
        dataHandler.getSportType(item.options[item.selectedIndex].value, (data) => {
            dom.clearMatches();
            dom.createMatches(data);
            checkIfOddsAlreadySelected();
        })
    },

    clearMatches: function () {
        document.getElementById("matches").innerHTML = "";

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
    }
}

function checkIfOddsAlreadySelected() {
    dataHandler.getCartItems((data) => {
        if (data !== null) {
            document.querySelector(".cart-item-counter").innerHTML = (data["items"].length).toString();
            let matches = document.querySelectorAll(".card");
            for (let match of matches) {
                for (let cartItem of data["items"]) {
                    if (cartItem["matchId"] === parseInt(match.dataset.matchid)) {
                        let outcome;
                        switch (cartItem["chosenOutcome"]) {
                            case ("Hazai"):
                                outcome = "home";
                                break;
                            case ("Döntetlen"):
                                outcome = "draw";
                                break;
                            case ("Vendég"):
                                outcome = "away";
                                break;
                        }
                        let cardOdds = match.querySelectorAll(".card-odds");
                        for (let cardOdd of cardOdds) {
                            if (cardOdd.dataset.outcome === outcome) {
                                cardOdd.querySelector("a").classList.add("active");
                            } else {
                                cardOdd.querySelector("a").classList.add("inactive");
                            }
                        }
                    }
                }
            }

        } else {
            document.querySelector(".cart-item-counter").innerHTML = "0";
        }
    })
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
