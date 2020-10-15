package com.codecool.shop.config;

import com.codecool.shop.dao.CountryDao;
import com.codecool.shop.dao.MatchDetailsDao;
import com.codecool.shop.dao.SportTypeDao;
import com.codecool.shop.dao.implementation.CountryDaoMem;
import com.codecool.shop.dao.implementation.MatchDetailsDaoMem;
import com.codecool.shop.dao.implementation.SportTypeDaoMem;
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
        MatchDetailsDao matchDetailsDataStore = MatchDetailsDaoMem.getInstance();
        CountryDao countryDataStore = CountryDaoMem.getInstance();
        SportTypeDao sportTypeDataStore = SportTypeDaoMem.getInstance();

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
        SportType another = new SportType("Handball", "ico-sport ico-sport-handball");
        sportTypeDataStore.add(another);

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




        matchDetailsDataStore.add(new MatchDetails("Fourth Match",
                "Wright P", "Aspinall N", "Premier League",
                1.65f, 1.0f, 2.45f, england, darts));

        matchDetailsDataStore.add(new MatchDetails("Sixth Match",
                "Dinamo Riga", "Locomotiv Yaroslavl", "KHL",
                3.25f, 1.0f, 1.35f, russia, iceHockey));
    }

}
