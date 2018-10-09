public enum SunCondition {
    Dawn("красивые рассветы"),
    Sunset("чудесные темно-багровые закаты");

    private final String name;

    SunCondition(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}