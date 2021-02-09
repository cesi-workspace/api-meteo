package fr.cesi.meteo.configuration.route;

import fr.cesi.meteo.infrastructure.http.Router;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class RouterList {

    private final List<Router> routers;

    public RouterList() {
        routers = new ArrayList<>();

        routers.add(new ApiRouterList());
    }

}
