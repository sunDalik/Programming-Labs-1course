public class Volcano extends Nature{
    private int power;
    private boolean isActive;

    public Volcano(int pow){
        power = pow;
        if(power >= 35){
            isActive = true;
            //Если силы накопилось много, то начинаем извержение
        }
        if(power < 35){
            isActive = false;
            //а если ее мало, то вулкан затухает.
        }

        if (isActive) { goWild(); }
        else { calmDown(); }
    }

    @Override
    public void goWild(){
        power += Math.random() * 40;
        System.out.print(" начал извергаться ");
    }

    @Override
    public void calmDown() {
        power = 0;
        System.out.print(" закончил извергаться ");
    }

    public void produce(String[] elements){
            super.changeSky(elements[0], elements[1]);

        if (isActive) {
            System.out.print("и активно выбрасывать ");
        }
        else System.out.print("и оставил после себя ");

        if (elements.length == 1 && !isActive) {
            System.out.println("лишь " + elements[0] + ".");
        }
        else if (elements.length == 2 && !isActive) {
            System.out.println("лишь " + elements[0] + " да " + elements[1] + ".");
        }
        else {
            for (int i = 0; i < elements.length; i++){
                if (i == elements.length - 2){
                    System.out.print(elements[i] + " и ");
                }
                else if (i == elements.length - 1){
                    System.out.println(elements[i] + ".");
                }
                else {
                    System.out.print(elements[i] + ", ");
                }
            }
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Volcano volcano = (Volcano) o;

        return isActive == volcano.isActive;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (isActive ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Мощь: " + power+ ".";
    }
}


