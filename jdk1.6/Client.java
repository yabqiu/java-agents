public class Client {

    public static void main(String[] args) throws InterruptedException {
        System.out.println(new TransClass().getNumber());
        int count = 0;
        while (true) {
            Thread.sleep(500);
            count ++;
            int number = new TransClass().getNumber();
            System.out.println(number);

            if( number == 3 || count >=10) {
                break;
            }
        }
    }
}
