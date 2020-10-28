package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.SportTypeDao;
import com.codecool.shop.model.SportType;

import java.util.ArrayList;
import java.util.List;

public class SportTypeDaoMem implements SportTypeDao {



    private List<SportType> data = new ArrayList<>();
    private static SportTypeDaoMem instance = null;

    /* A private Constructor prevents any other class from instantiating.
     */
    private SportTypeDaoMem() {
    }

    public void setData(List<SportType> data) {
        this.data = data;
    }

    public static SportTypeDaoMem getInstance() {
        if (instance == null) {
            instance = new SportTypeDaoMem();
        }
        return instance;
    }

    @Override
    public void add(SportType sportType) {
        sportType.setId(data.size() + 1);
        data.add(sportType);
    }

    @Override
    public SportType find(int id) {
        return data.stream().filter(t -> t.getId() == id).findFirst().orElse(null);
    }

    @Override
    public void remove(int id) {
        data.remove(find(id));
    }

    @Override
    public List<SportType> getAll() {
        return data;
    }
}
