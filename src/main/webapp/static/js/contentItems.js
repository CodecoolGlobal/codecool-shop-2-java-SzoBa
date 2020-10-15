export let contentItems = {

    mainTableMatch: function (match) {
        return `
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
    },

    cartMatch: function (match) {
            return `<li class ="cart-item" data-matchid="${match.matchId}">
                <div>${match.home} - ${match.away}</div>
                <div>
                    Chosen: ${match.chosenOutcome}
                    <a><img src="/static/img/trashbin3.png" width="15" height="15" alt="delete-match"></a>
                </div>
                <span class="odds">Odds: <span>${match.odds}</span></span>
            </li>`
    },

    cartConstantItems: function (data) {
        return `<div class="cart-inputs">
                 <div>
                     <label class="bet-value-label" for="betValue">Current bet: </label>
                     <input class="bet-value-input" id="betValue" name="betValue" type="text" placeholder="Min 100">
                 </div>
                 <table class="cart-input-table">
                     <tr>
                         <td>Total odds: </td>
                         <td id="total-odds-value">${data.odds}</td>
                     </tr>
                     <tr>
                         <td>Possible win: </td>
                         <td id="possible-win-value">0</td>
                     </tr>
                 </table>
             </div>
             <div class="confirmation">
                <a><button class="go-to-checkout">Confirm Order</button></a>
             </div>`
    },

    clearCartContentBody: function () {
        return `<div class="cart-content-matches">
            <ul></ul>
            </div>`;
    },

    emptyCartFancyText: function () {
        return `<div class="no-matches">
                No matches added to cart yet. </br>
                Please choose to continue!
            </div>`
    }

}