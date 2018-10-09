public class Wave extends Sea {
    private int power;
    private int number;
    private String location;


    public Wave(int n, int pow, String loc){
        number = n;
        power = pow;
        location = loc;
    }

    static public Wave fromCsv(String a){
        String b[] = a.split(",");
        return new Wave(Integer.getInteger(b[0]), Integer.getInteger(b[1]), b[2]);
    }


    public String toCsv(){
        return Integer.toString(power) + "," + Integer.toString(number) + "," + location;
    }

    @Override
    public void calmDown() {
        for(;power > 10;){
            power--;
        }
        System.out.print(" обессилел и присмирел");
        if(location.equals("бухта")){
            System.out.println(" в бухте.");
        }
        else System.out.println(" где-то в море.");
    }

    @Override
    public void goWild() {
        power += Math.random() * number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Wave wave = (Wave) o;

        if (power != wave.power) return false;
        if (number != wave.number) return false;
        return location != null ? location.equals(wave.location) : wave.location == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + power;
        result = 31 * result + number;
        result = 31 * result + (location != null ? location.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return number + " вал с силой " + power + ". Местоположение: " + location;
    }
}
