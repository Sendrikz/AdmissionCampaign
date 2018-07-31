package enums;

public enum Universities {
    NaUKMA("Національний університет «Києво-Могилянська академія»",
            "Григорія Сковороди, 2"),
    KPI("Національний технічний університет України " +
            "«Київський політехнічний інститут імені Ігоря Сікорського»",
            "просп. Перемоги, 37");

    private String name;
    private String address;

    Universities(String name, String adress) {
        this.name = name;
        this.address = adress;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }
}
