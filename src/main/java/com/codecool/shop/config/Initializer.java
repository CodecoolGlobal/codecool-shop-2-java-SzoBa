package com.codecool.shop.config;

import com.codecool.shop.dao.CountryDao;
import com.codecool.shop.dao.MatchDetailsDao;
import com.codecool.shop.dao.SportTypeDao;
import com.codecool.shop.dao.jdbc_implementation.GameDatabaseManager;
import com.codecool.shop.model.Country;
import com.codecool.shop.model.MatchDetails;
import com.codecool.shop.model.SportType;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class Initializer implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        GameDatabaseManager gameDatabaseManager = GameDatabaseManager.getInstance();
        MatchDetailsDao matchDetailsDataStore = gameDatabaseManager.getMatchDetailsDao();
        CountryDao countryDataStore = gameDatabaseManager.getCountryDao();
        SportTypeDao sportTypeDataStore = gameDatabaseManager.getSportTypeDao();

        //setting up a new sport
        SportType football = new SportType("Football", "ico-sport ico-sport-soccer");
        sportTypeDataStore.add(football);
        SportType tennis = new SportType("Tennis", "ico-sport ico-sport-tennis");
        sportTypeDataStore.add(tennis);
        SportType basketball = new SportType("Basketball", "ico-sport ico-sport-basketball");
        sportTypeDataStore.add(basketball);
        SportType iceHockey = new SportType("Hockey", "ico-sport ico-sport-ice-hockey");
        sportTypeDataStore.add(iceHockey);
        SportType handball = new SportType("Handball", "ico-sport ico-sport-handball");
        sportTypeDataStore.add(handball);
        SportType darts = new SportType("Darts", "ico-sport ico-sport-darts");
        sportTypeDataStore.add(darts);
        SportType badminton = new SportType("Badminton", "ico-sport ico-sport-badminton");
        sportTypeDataStore.add(badminton);


        //setting up a new country
        Country international = new Country("International", "Best country - Hungary.");
        countryDataStore.add(international);
        Country hungary = new Country("Hungary", "Best country - Hungary.");
        countryDataStore.add(hungary);
        Country england = new Country("England", "Tea for two.");
        countryDataStore.add(england);
        Country france = new Country("France", "Best country - Hungary.");
        countryDataStore.add(france);
        Country spain = new Country("Spain", "Arriva Dercsi:)");
        countryDataStore.add(spain);
        Country russia = new Country("Russia", "Voszem");
        countryDataStore.add(russia);

        //setting up matches and printing them

            //international football matches
        matchDetailsDataStore.add(new MatchDetails("1. Match",
                "Italy", "Netherlands", "UEFA Nations League A, Gr. 1",
                1.95f, 3.3f, 4.0f, international, football));

        matchDetailsDataStore.add(new MatchDetails("2. Match",
                "England", "Denmark", "UEFA Nations League A, Gr. 2",
                1.83f, 3.4f, 4.5f, international, football));

        matchDetailsDataStore.add(new MatchDetails("3. Match",
                "Iceland", "Belgium", "UEFA Nations League A, Gr. 2",
                11.0f, 5.5f, 1.28f, international, football));

        matchDetailsDataStore.add(new MatchDetails("4. Match",
                "Russia", "Hungary", "UEFA Nations League B, Gr. 3",
                1.4f, 4.5f, 8.0f, international, football));


            //hungary football matches
        matchDetailsDataStore.add(new MatchDetails("5. Match",
                "Budafoki MTE", "Diósgyőri VTK", "NB I",
                2.25f, 3.4f, 3.1f, hungary, football));

        matchDetailsDataStore.add(new MatchDetails("6. Match",
                "Budapest Honvéd", "MOL Fehérvár FC", "NB I",
                4f, 3.7f, 1.83f, hungary, football));

        matchDetailsDataStore.add(new MatchDetails("7. Match",
                "Puskás Akadémia", "MTK Budapest", "NB I",
                1.75f, 3.75f, 4.33f, hungary, football));

            //england football matches
        matchDetailsDataStore.add(new MatchDetails("8. Match",
                "Everton", "Liverpool", "Premier League",
                3.75f, 4.2f, 1.8f, england, football));

        matchDetailsDataStore.add(new MatchDetails("9. Match",
                "Chelsea", "Southampton", "Premier League",
                1.45f, 4.75f, 6.5f, england, football));

        matchDetailsDataStore.add(new MatchDetails("10. Match",
                "Manchester City", "Arsenal", "Premier League",
                1.4f, 5.25f, 7.0f, england, football));

            //france football matches
        matchDetailsDataStore.add(new MatchDetails("11. Match",
                "Stade de Reims", "Lorient", "Ligue 1",
                2.1f, 3.4f, 3.6f, france, football));

        matchDetailsDataStore.add(new MatchDetails("12. Match",
                "Olympique de Marseille", "Bordeaux", "Ligue 1",
                2.0f, 3.5f, 3.8f, france, football));

            //spain football matches
        matchDetailsDataStore.add(new MatchDetails("13. Match",
                "Granada", "Sevilla", "LaLiga",
                4.0f, 3.5f, 1.9f, spain, football));

        matchDetailsDataStore.add(new MatchDetails("14. Match",
                "Getafe", "Barcelona", "LaLiga",
                5.0f, 3.6f, 1.72f, spain, football));

            //russian football matches
        matchDetailsDataStore.add(new MatchDetails("15. Match",
                "Zenit St. Petersburg", "FC Sochi", "Premier League",
                1.36f, 4.5f, 8.5f, russia, football));



        //Tennis games
            //international tennis
        matchDetailsDataStore.add(new MatchDetails("16. Match",
                "Zverev A.", "Verdasco F.", "ATP",
                1.2f, 1.0f, 4.33f, international, tennis));

        matchDetailsDataStore.add(new MatchDetails("17. Match",
                "Simon G.", "Bautista Agut R.", "ATP",
                3.0f, 1.0f, 1.36f, international, tennis));


        // basketball
            //international basketball
        matchDetailsDataStore.add(new MatchDetails("18. Match",
                "Barca Basket", "Panathinaikos BC", "Euroleague",
                1.18f, 1.0f, 5.25f, international, basketball));

            //spain basket
        matchDetailsDataStore.add(new MatchDetails("19. Match",
                "Movistar E.", "Gran Canaria", "Liga ACB",
                1.66f, 1.0f, 2.3f, spain, basketball));

        // hockey
            //russia
        matchDetailsDataStore.add(new MatchDetails("20. Match",
                "Dinamo Riga", "Locomotiv Yaroslavl", "KHL",
                3.25f, 1.0f, 1.35f, russia, iceHockey));

        matchDetailsDataStore.add(new MatchDetails("21. Match",
                "Kunlun Red Star", "Salavat Yulaev Ufa", "KHL",
                3.8f, 1.0f, 1.28f, russia, iceHockey));

        // handball
            //international handball
        matchDetailsDataStore.add(new MatchDetails("22. Match",
                "FC Porto Sofarma", "MOL Pick Szeged", "Champions League, Group A",
                1.18f, 13.0f, 8.0f, international, handball));

            //france handball
        matchDetailsDataStore.add(new MatchDetails("23. Match",
                "Cesson Rennes MHB", "Limoges Handball 87", "LNH Starligue",
                1.8f, 7.5f, 2.7f, france, handball));

        // darts
            //england darts
        matchDetailsDataStore.add(new MatchDetails("24. Match",
                "Wright P.", "Aspinall N.", "Premier League, Playoffs",
                1.65f, 1.0f, 2.45f, england, darts));

        matchDetailsDataStore.add(new MatchDetails("25. Match",
                "Durrant G.", "Anderson G.", "Premier League, Playoffs",
                1.85f, 1.0f, 2.3f, england, darts));

        // badminton
            //international
        matchDetailsDataStore.add(new MatchDetails("26. Match",
                "Ho-Shue J.", "Srikanth K.", "Denmark Open 2020, MS",
                2.7f, 1.0f, 1.4f, international, badminton));

        matchDetailsDataStore.add(new MatchDetails("27. Match",
                "Popov T.", "Jorgensen J.", "Denmark Open 2020, MS",
                1.65f, 1.0f, 2.45f, international, badminton));

        matchDetailsDataStore.add(new MatchDetails("28. Match",
                "Yang B.", "Weisskirchen M.", "Denmark Open 2020, MS",
                1.95f, 1.0f, 2.25f, international, badminton));

    }

}
