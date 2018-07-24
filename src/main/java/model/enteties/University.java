package model.enteties;

import java.util.Objects;

public class University {

    private int id;
    private String name;
    private String adress;

    public University (String name, String adress) {
        this.name = name;
        this.adress = adress;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }

        University uni = (University) obj;

        return this.id == uni.id && this.name.equals(uni.name) &&
                this.adress.equals(uni.adress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, adress);
    }

}
