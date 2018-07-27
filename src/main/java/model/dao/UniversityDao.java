package model.dao;

import model.enteties.University;

import java.util.ArrayList;

public interface UniversityDao {

    ArrayList<University> getAll();

    void add(University university);

    void update(int id, String name, String address);

    University findById(int id);

    void deleteById(int id);
    void clearAllUniversities();
}
