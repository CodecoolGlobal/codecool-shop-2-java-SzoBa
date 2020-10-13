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

        //setting up a new supplier
        SportType football = new SportType("Football", "Kick that ball, bro!");
        sportTypeDataStore.add(football);
        SportType iceHockey = new SportType("Hockey", "Ice, love it!");
        sportTypeDataStore.add(iceHockey);
        SportType darts = new SportType("Darts", "Throw darts.");
        sportTypeDataStore.add(darts);


        //setting up a new product category
        Country hungary = new Country("Hungary", "Best country - Hungary.");
        countryDataStore.add(hungary);
        Country england = new Country("England", "Tea for two.");
        countryDataStore.add(england);
        Country spain = new Country("Spain", "Arriva Dercsi:)");
        countryDataStore.add(spain);
        Country russia = new Country("Russia", "Voszem");
        countryDataStore.add(russia);


        //setting up products and printing it
        matchDetailsDataStore.add(new MatchDetails("First Match",
                "Budafoki MTE", "Diósgyőri VTK", "NB I",
                2.25f, 3.4f, 3.1f, hungary, football));

        matchDetailsDataStore.add(new MatchDetails("Second Match",
                "Budapest Honvéd", "MOL Fehérvár FC", "NB I",
                4f, 3.7f, 1.83f, hungary, football));

        matchDetailsDataStore.add(new MatchDetails("Third Match",
                "Everton", "Liverpool", "Premier League",
                3.75f, 4.2f, 1.8f, england, football));

        matchDetailsDataStore.add(new MatchDetails("Fourth Match",
                "Wright P", "Aspinall N", "Premier League",
                1.65f, 1.0f, 2.45f, england, darts));
        matchDetailsDataStore.add(new MatchDetails("Fifth Match",
                "Granada", "Sevilla", "LaLiga",
                4.0f, 3.5f, 1.9f, spain, football));
        matchDetailsDataStore.add(new MatchDetails("Sixth Match",
                "Dinamo Riga", "Locomotiv Yaroslavl", "KHL",
                3.25f, 1.0f, 1.35f, russia, iceHockey));
    }

}
