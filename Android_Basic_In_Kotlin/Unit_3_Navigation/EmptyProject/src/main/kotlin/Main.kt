

class Singleton{

    private static Singelton instance;

    private Singelton(){

    }

    public static  Singleton getInstance(){

        if(instance==null)
            instance = new Singelton();

        return instance;
    }
}