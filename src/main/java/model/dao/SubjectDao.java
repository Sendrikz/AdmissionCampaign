package model.dao;

import model.enteties.Subject;

import java.util.ArrayList;

public interface SubjectDao {

    ArrayList<Subject> getAll();

    void add(Subject subject);

    void update(int id, String name, int duration);

    Subject findById(int id);

    void deleteById(int id);
    void clearAllSubjects();
}
