package software.ulpgc.katas;

import software.ulpgc.katas.adapters.RandomUserBuilder;
import software.ulpgc.katas.domain.model.User;
import software.ulpgc.katas.ports.UserBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Main {
    static void main() {
        UserBuilder userBuilder = new RandomUserBuilder();
        for (User user : userBuilder.build(5)) {
            System.out.println(user.name() + " - " + user.gender());
        }
    }
}
