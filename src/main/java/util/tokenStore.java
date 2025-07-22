package util;

public class tokenStore {
    private String token;
    private static  final tokenStore
            INSTANCE= new tokenStore();

    private tokenStore(){}

    public static tokenStore
    getInstance(){
        return INSTANCE;
    }

    public void setToken(String t) {
        this.token = t;
    }

    public String getToken() {
        return this.token;
    }
}