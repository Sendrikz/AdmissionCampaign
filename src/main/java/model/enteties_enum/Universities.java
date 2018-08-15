package model.enteties_enum;

public enum Universities {
    NaUKMA("Національний університет «Києво-Могилянська академія»",
            "Григорія Сковороди, 2", "Київ"),
    KPI("Національний технічний університет України " +
            "«Київський політехнічний інститут імені Ігоря Сікорського»",
            "просп. Перемоги, 37", "Київ");

    private String name;
    private String address;
    private String city;

    Universities(String name, String adress, String city) {
        this.name = name;
        this.address = adress;
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }
}
