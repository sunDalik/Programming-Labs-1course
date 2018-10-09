public class Sea extends Nature{

    private int power_limit;
    Wave wave;

    Sea(){}

    public Sea(int pow_lim){
        power_limit = pow_lim;
    }

    @Override
    public void goWild(){
        power_limit += Math.random() * 20;
    }

    @Override
    public void calmDown() {
        power_limit -= 10;
        if(power_limit < 10) {
            power_limit = 10;
        }
    }

    public void create_waves(int number_of_waves, int power, String loc){
        int n;

        for(n = 1; n <= number_of_waves; n++){
            wave = new Wave(n, n*power, loc);
            if(n*power <= power_limit && n > 6){
                wave.goWild();
            }
            if(n*power > power_limit || n == number_of_waves) { break; }
        }
        System.out.print(n + " вал");
        wave.calmDown();
    }

   @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Sea sea = (Sea) o;

        if (power_limit != sea.power_limit) return false;
        return wave != null ? wave.equals(sea.wave) : sea.wave == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + power_limit;
        result = 31 * result + (wave != null ? wave.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Мощь: " + power_limit + ".";
    }
}
