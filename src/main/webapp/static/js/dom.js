import {dataHandler} from "./dataHandler.js";
import {contentItems} from "./contentItems.js";

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
            this.addListenersToSportSelectItems();
        });
        document.getElementById("country-selection").addEventListener("click", () => {
            document.querySelector(".type-dropdown").classList.add("hidden");
            this.showDropdownMenu(".country-dropdown");
            this.addListenersToCountrySelectItems();
        });
        document.querySelector(".cart-container").addEventListener("click", this.loadCartContentOnOpen);
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

    removeListenerToOdds: function () {
        const odds = document.querySelectorAll('.card-odds')
        for (const odd of odds) {
            odd.removeEventListener("click", this.modifyCartItems);
        }
    },

    addListenersToCountrySelectItems: function () {
        let items = document.querySelectorAll(".country-type");
        items.forEach(item => item.addEventListener("click", this.addSelectedCountryListener))
    },

    addListenersToSportSelectItems() {
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

        dataHandler.getCountry(typeId, countryId, (data) => {
            dom.clearMatchesFromMainTable();
            dom.createMatchesOnMainTable(data);
            checkIfOddsAlreadySelected();
        })
    },

    closeDropdowns: function () {
        let typeDropdown = document.querySelector(".type-dropdown");
        let countryDropdown = document.querySelector(".country-dropdown");
        typeDropdown.classList.add("hidden");
        countryDropdown.classList.add("hidden");
    },

    loadCartContentOnOpen: function (event) {
        let content = document.querySelector(".cart-content");
        if (event.target.classList.contains("cart-container") || event.target.classList.contains("cart-item-counter")
            || event.target.classList.contains("cart-title")) {
            content.classList.toggle("hidden");
            if (!content.classList.contains("hidden")) {
                dataHandler.getCartContent((data) => {
                    dom.clearCartContentBody();
                    dom.createCartContent(data);
                })
                dom.removeListenerToOdds();
            } else {
                dom.addListenerToOdds();
            }
        }
    },

    clearMatchesFromMainTable: function () {
        document.getElementById("matches").innerHTML = "";

    },

    clearCartContentBody: function () {
        let content = document.querySelector(".cart-content-body");
        content.innerHTML = contentItems.clearCartContentBody();

    },

    createMatchesOnMainTable: function (matches) {
        let mainContent = "";
        for (let match of matches) {
            let matchContent = contentItems.mainTableMatch(match);
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

                changeBetCounter(isAdded);
            })
        }
    },

    createCartContent: function (data) {
        if (data != null) {
            this.createCartMatchesList(data.items);
            this.addCartConstantItems(data);
            this.addListenersToTrashBins();
            this.calculatedOddsInCartRefresh();
        } else {
            this.addFancyTextToEmptyCart();
        }
    },

    createCartMatchesList: function (matches) {
        let matchList = document.querySelector(".cart-content-matches ul");
        let content = "";
        for (let match of matches) {
            let addMatch = contentItems.cartMatch(match);
            content += addMatch;
        }

        matchList.insertAdjacentHTML("beforeend", content);
    },

    addFancyTextToEmptyCart: function () {
        let matchList = document.querySelector(".cart-content-matches ul");
        let matchContent = document.querySelector(".cart-content-matches");
        let addNoMatches = contentItems.emptyCartFancyText();
        matchList.insertAdjacentHTML("beforeend", addNoMatches);
        matchContent.classList.add("align-no-matches")
    },

    addCartConstantItems: function (data) {
        let content = document.querySelector(".cart-content-body");
        let items = contentItems.cartConstantItems(data);
        content.insertAdjacentHTML("beforeend", items);
        this.addListenersToBetInput();
        this.addListenerToConfirmButton();
    },

    addListenersToBetInput: function () {
        let input = document.querySelector("#betValue");
        input.addEventListener("keyup", (event) => {
            if (/^\d+$/.test(input.value)) {
                this.updatePossibleWinNumber(input.value);
            }
        })

    },

    addListenerToConfirmButton: function () {
        let button = document.querySelector(".go-to-checkout");
        button.addEventListener("click", this.confirmCart);
    },

    addListenersToTrashBins: function () {
        let trashBinContainers = document.querySelectorAll(".cart-content-matches ul li a");
        trashBinContainers.forEach(bin => bin.addEventListener("click", this.removeMatchItemFromCart))
    },

    confirmCart: function () {
        let betValue = document.querySelector("#betValue").value;
        let possibleWinAmount = document.querySelector("#possible-win-value").innerHTML;
        if (betValue > 0 && possibleWinAmount > 0) {
            let today = new Date();
            let dd = String(today.getDate()).padStart(2, '0');
            let mm = String(today.getMonth() + 1).padStart(2, '0');
            let yyyy = today.getFullYear();
            today = dd + "-" + mm + "-" + yyyy;
            let totalOdds = document.querySelector("#total-odds-value").innerHTML;
            let orderInfos = {"betValue" : betValue, "possibleWinAmount": possibleWinAmount, "date": today, "totalOdds": totalOdds};
            dataHandler.saveBet(orderInfos, (data) => {
                if (data === "Cart saved successfully") {
                    window.location.href = "/checkout";
                } else {
                    console.log(data);
                }
            })
        }
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
            let counter = document.querySelector(".cart-item-counter");
            changeBetCounter(true);
            if (parseInt(counter.innerHTML) < 1) {
                dom.clearCartContentBody();
                dom.addFancyTextToEmptyCart();
            }
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
        let allOdds = document.querySelectorAll(".cart-content-matches ul li span span");
        let valueOfCalculatedOdds = 1;
        allOdds.forEach(odd => {valueOfCalculatedOdds = valueOfCalculatedOdds * parseFloat(odd.innerHTML)});
        calculatedOdds.innerHTML = valueOfCalculatedOdds.toFixed(2);

    },

    updatePossibleWinNumber: function (bet){
        let possibleWinAmount = document.querySelector("#possible-win-value");

        let currentOdds = parseFloat(document.querySelector("#total-odds-value").innerHTML);
        possibleWinAmount.innerHTML = bet < 100 ? "0" : (currentOdds * bet).toFixed(0);
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
                            case ("Home"):
                                outcome = "home";
                                break;
                            case ("Draw"):
                                outcome = "draw";
                                break;
                            case ("Away"):
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

function changeBetCounter(isAdded) {
    let betCounter = document.querySelector(".cart-item-counter");
    if (isAdded) {
        betCounter.innerHTML = (parseInt(betCounter.innerHTML) - 1).toString();
    } else {
        betCounter.innerHTML = (parseInt(betCounter.innerHTML) + 1).toString();
    }
}
