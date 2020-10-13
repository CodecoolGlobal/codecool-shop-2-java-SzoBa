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
//        football.setId(1);
        sportTypeDataStore.add(football);
        SportType iceHockey = new SportType("Hockey", "Ice, love it!");
//        iceHockey.setId(2);
        sportTypeDataStore.add(iceHockey);
        SportType darts = new SportType("Darts", "Throw darts.");
//        darts.setId(3);
        sportTypeDataStore.add(darts);


        //setting up a new product category
        Country hungary = new Country("Hungary", "Best country - Hungary.");
//        hungary.setId(1);
        countryDataStore.add(hungary);
        Country england = new Country("England", "Tea for two.");
//        england.setId(2);
        countryDataStore.add(england);


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

//        for (int i = 0; i < matchDetailsDataStore.getAll().size(); i++) {
//            matchDetailsDataStore.getAll().get(i).setId(i+1);
//        }
    }

}
