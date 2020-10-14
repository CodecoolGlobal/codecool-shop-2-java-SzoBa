import {dataHandler} from "./dataHandler.js";

export let dom = {
    init: function () {
        this.addConstantListeners();
        this.addTemporaryListeners();
        checkIfOddsAlreadySelected();
    },

    addConstantListeners: function () {
        document.getElementById("type-selection").addEventListener("click", () => {
            document.querySelector(".country-dropdown").classList.add("hidden");
            this.showDropdownMenu(".type-dropdown");
            this.addListenerToSportSelect();
        });
        document.getElementById("country-selection").addEventListener("click", () => {
            document.querySelector(".type-dropdown").classList.add("hidden");
            this.showDropdownMenu(".country-dropdown");
            this.addListenerToCountrySelect();
        });
        document.querySelector(".cart-container").addEventListener("click", this.addListenerToCart);
        document.querySelector(".search").addEventListener("click", this.filterByCondition);
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
        let items = document.querySelectorAll(".country-type");
        items.forEach(item => item.addEventListener("click", this.addSelectedCountryListener))
    },

    addListenerToSportSelect() {
        let items = document.querySelectorAll(".sport-type");
        items.forEach(item => item.addEventListener("click", this.addSelectedTypeListener));
    },

    addSelectedTypeListener: function (event) {
        let mainItem = document.querySelector("#type-selection")
        mainItem.innerHTML = event.target.innerText;
        mainItem.dataset.value = event.target.value > -1 ? event.target.value : event.target.parentElement.value;
    },

    addSelectedCountryListener: function (event) {
        let mainItem = document.querySelector("#country-selection")
        mainItem.innerHTML = event.target.innerText;
        mainItem.dataset.value = event.target.value;
    },

    filterByCondition: function () {
        dom.closeDropdowns();

        let typeId = document.querySelector("#type-selection").dataset.value > 0 ?
            document.querySelector("#type-selection").dataset.value : 0;
        let countryId = document.querySelector("#country-selection").dataset.value > 0 ?
            document.querySelector("#country-selection").dataset.value : 0;
        console.log(typeId + " " + countryId);
        dataHandler.getCountry(countryId, (data) => {
            dom.clearMatches();
            dom.createMatches(data);
            checkIfOddsAlreadySelected();
        })
    },

    closeDropdowns: function () {
        let typeDropdown = document.querySelector(".type-dropdown");
        let countryDropdown = document.querySelector(".country-dropdown");
        typeDropdown.classList.add("hidden");
        countryDropdown.classList.add("hidden");
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
                        <p class="card-text">${match.leagueName}</p>
                    </div>
                    <div class="card-body">
                        <div class="card-odds" data-outcome="home">
                            <div class="btn btn-success">
                                <span>H</span>
                                <br>
                                    <span>${match.homeOdds % 1 === 0 ? match.homeOdds + '.0' : match.homeOdds}</span>
                            </div>
                        </div>
                        <div class="card-odds ${match.drawOdds === 1 ? "hidden" : ""}" data-outcome="draw">
                            <div class="btn btn-success">
                                <span>D</span>
                                <br>
                                    <span>${match.drawOdds % 1 === 0 ? match.drawOdds + '.0' : match.drawOdds}</span>
                            </div>
                        </div>
                        <div class="card-odds" data-outcome="away">
                            <div class="btn btn-success">
                                <span>V</span>
                                <br>
                                    <span>${match.awayOdds % 1 === 0 ? match.awayOdds + '.0' : match.awayOdds}</span>
                            </div>
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
        let isAdded = event.target.closest("div").classList.contains("active");
        let outcome = event.target.closest(".card-odds").dataset.outcome;

        if (!event.target.closest("div").classList.contains("inactive")) {
            dataHandler.modifyCartItems(matchID, isAdded, outcome, () => {
                isAdded ? event.target.closest("div").classList.remove("active") :
                    event.target.closest("div").classList.add("active");

                addOrRemoveInactiveFromOdds(event, isAdded);
                let betCounter = document.querySelector(".cart-item-counter");
                changeBetCounter(isAdded, betCounter);
            })
        }
    },

    createTicketData: function (data) {
        this.createCartMatchesList(data.items);
        this.addCartConstantItems(data);
        this.addListenersToTrashBins();
        this.calculatedOddsInCartRefresh();
    },

    createCartMatchesList: function (matches) {
        let matchList = document.querySelector(".cart-content-matches ul");
        let content = "";
        for (let match of matches) {
            let addMatch = `
            <li data-matchid="${match.matchId}">${match.home} - ${match.away} Odds <span>${match.odds}</span>
            <a><img src="/static/img/trashbin3.png" width="15" height="15" alt="delete-match"></a><br>Chosen: ${match.chosenOutcome}
            </li>`
            content += addMatch;
        }
        matchList.insertAdjacentHTML("beforeend", content);
    },

    addCartConstantItems: function (data) {
        let content = document.querySelector(".cart-content-body");
        let items =
            `<label for="betValue">Current bet:  </label>
             <input id="betValue" name="betValue" type="text" placeholder="${data.bet}">
             <br>
             <table>
             <tr><td>Total odds: </td><td id="total-odds-value">${data.odds}</td></tr>
             <tr><td>Possible win: </td><td id="possible-win-value">Kiszámolni</td></tr>
             </table>
             <a href="/"><button>Confirm Order</button></a>`
        content.insertAdjacentHTML("beforeend", items);
        this.addListenersToBetInput();
    },

    addListenersToBetInput: function () {
        let input = document.querySelector("#betValue");
        input.addEventListener("keydown", (event) => {
            if (/^\d+$/.test(input.value)) {
                this.updatePossibleWinNumber(input.value);

                //TODO save bet to database no?

            }
        })

    },

    addListenersToTrashBins: function () {
        let trashBinContainers = document.querySelectorAll(".cart-content-matches ul li a");
        trashBinContainers.forEach(bin => bin.addEventListener("click", this.removeMatchItemFromCart))
    },

    removeMatchItemFromCart: function (event) {
        let listItem = event.target.closest("li");
        let matchId = listItem.dataset.matchid;
        let isAdded = true;
        let outcome = "deletable";
        let cards = document.querySelectorAll(".card");
        cards.forEach(card => {
            if (card.dataset.matchid === matchId) {
                card.querySelectorAll(".btn").forEach(btn => btn.classList.remove("inactive", "active"));
            }
        })
        dataHandler.modifyCartItems(matchId, isAdded, outcome, () => {
            listItem.remove();
            dom.calculatedOddsInCartRefresh();

        })
    },

    showDropdownMenu(Type) {
        let dropdown = document.querySelector(Type);
        switch (dropdown.classList.contains("hidden")) {
            case true:
                dropdown.classList.remove("hidden");
                break;
            case false:
                dropdown.classList.add("hidden");
                break;
        }
    },

    calculatedOddsInCartRefresh: function () {
        let calculatedOdds = document.querySelector("#total-odds-value");
        let allOdds = document.querySelectorAll(".cart-content-matches ul li span");
        let valueOfCalculatedOdds = 1;
        allOdds.forEach(odd => {valueOfCalculatedOdds = valueOfCalculatedOdds * parseFloat(odd.innerHTML)});
        calculatedOdds.innerHTML = valueOfCalculatedOdds.toFixed(2);

    },

    updatePossibleWinNumber: function (bet){
        let possibleWinAmount = document.querySelector("#possible-win-value");

        //TODO update fields
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
                                cardOdd.querySelector("div").classList.add("active");
                            } else {
                                cardOdd.querySelector("div").classList.add("inactive");
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
        let matchOdds = event.target.closest(".card-body").querySelectorAll("div");
        for (let odd of matchOdds) {
            if (odd.classList.contains("inactive")) {
                odd.classList.remove("inactive");
            }
        }
    } else {
        let matchOdds = event.target.closest(".card-body").querySelectorAll("div");
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
