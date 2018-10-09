public enum CloudsCondition {
    White("белые облака"),
    Ashy("облака пепла");

    private final String name;

    CloudsCondition(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}

