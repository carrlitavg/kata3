package software.ulpgc.katas.adapters;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import software.ulpgc.katas.domain.model.User;
import software.ulpgc.katas.ports.UserBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class RandomUserBuilder implements UserBuilder {
    private final static String base = "https://randomuser.me/api/";

    private final HttpClient client;

    public RandomUserBuilder() {
        this.client = HttpClient.newHttpClient();
    }

    @Override
    public List<User> build(int count) {
        try {
            return deserialize(load(uriFor(count)));
        } catch (URISyntaxException | InterruptedException | IOException e) {
            return List.of();
        }
    }


    private List<User> deserialize(String json) throws URISyntaxException {
        JsonArray results = new Gson().fromJson(json, JsonObject.class).get("results").getAsJsonArray();
        return deserialize(results);
    }

    private List<User> deserialize(JsonArray results) throws URISyntaxException {
        List<User> list = new ArrayList<>();
        for (JsonElement result : results) {
            list.add(deserialize(result));
            
        }
        return list;
    }

    private User deserialize(JsonElement element) throws URISyntaxException {
        return deserialize(new Gson().fromJson(element, software.ulpgc.katas.adapters.dtos.User.class))
    }

    private User deserialize(software.ulpgc.katas.adapters.dtos.User user) throws URISyntaxException {
        return new User (
                user.name.first + " "+ user.name.last,
                User.Gender.valueOf(user.gender),
                pictureOf(user.picture)
        );
    }

    private byte[] pictureOf(software.ulpgc.katas.adapters.dtos.User.Picture picture) throws URISyntaxException {
        return readPicture(picture.large);
    }

    private byte[] readPicture(String large) throws URISyntaxException {
        return toBytes(client.send(requestForPicture(large), HttpResponse.BodyHandlers.ofString().ofInputStream()).body());
    }

    private byte[] toBytes(InputStream inputStream) throws IOException {
        return inputStream.readAllBytes();
    }

    private Object requestForPicture(String uri) throws URISyntaxException {
        return HttpRequest.newBuilder().GET().uri(new URI(uri)).build();
    }

    private String load(URI uri) throws IOException, InterruptedException {
        return client.send(requestFor(uri), HttpResponse.BodyHandlers.ofString()).body();
    }

    private HttpRequest requestFor(URI uri) {
        return HttpRequest.newBuilder().GET().uri(uri).build();
    }

    private Object uriFor(int count) throws URISyntaxException {
        return new URI(base + "?results" + count);
    }
}
