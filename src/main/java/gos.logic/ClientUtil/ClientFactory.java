package gos.logic.ClientUtil;

public class ClientFactory {

    private ClientFactory() {
    }

    public static Client createRequestWithToken(String token) {
        return new Client(token);
    }
}
