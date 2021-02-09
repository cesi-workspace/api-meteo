package fr.cesi.infrastructure.http;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import fr.cesi.infrastructure.configuration.RouterList;
import fr.cesi.infrastructure.http.annotation.Action;
import lombok.Getter;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Dispatcher implements HttpHandler {

    @Getter
    private final Map<String, String> actionMap = new HashMap<>();

    public void registerAllRoutes(RouterList routerList) {
        List<Router> routers = routerList.getRouters();

        for (Router router : routers) {
            String domain = router.getDomain();

            if (Arrays.asList(-1, 0).contains(domain.lastIndexOf('/')))
                domain += "/";

            for (Map.Entry<String, Class<?>> stringControllerEntry : router.getControllers().entrySet()) {
                Class<?> controller = stringControllerEntry.getValue();

                String fullPath = domain + stringControllerEntry.getKey();
                fullPath = fullPath.replaceAll("//", ""); // On enlève les double / si il y en a après la concaténation

                Method[] methods = controller.getMethods();
                for (Method method : methods) {
                    Action action = method.getAnnotation(Action.class);

                    if (action != null)
                        actionMap.put(fullPath + action.path(), controller.getName() + "@" + method.getName());
                }
            }
        }

        actionMap.forEach((key, value) -> System.out.println(key + " => " + value));
    }

    @Override
    public void handle(HttpExchange exchange) {
        Response response = null;
        String path = exchange.getRequestURI().getPath();

        for (Map.Entry<String, String> pathActionEntry : actionMap.entrySet()) {
            if (path.equalsIgnoreCase(pathActionEntry.getKey())) {
                try {
                    String[] controllerAtAction = pathActionEntry.getValue().split("@");
                    Class<?> controllerClass = Class.forName(controllerAtAction[0]);
                    Method method = controllerClass.getMethod(controllerAtAction[1], Request.class);
                    Action action = method.getAnnotation(Action.class);

                    if (action != null && exchange.getRequestMethod().equalsIgnoreCase(action.method().name()))
                        response = (Response) method.invoke(
                                controllerClass.newInstance(),
                                new Request(queryToMap(exchange.getRequestURI().getQuery()))
                        );
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
;
        if (response == null)
            response = Response.builder()
                    .statusCode(404)
                    .build();

        response.sendResponse(exchange);
    }

    private Map<String, String> queryToMap(String query) {
        Map<String, String> result = new HashMap<>();

        if (query == null || query.length() == 0)
            return result;

        for (String param : query.split("&")) {
            String[] pair = param.split("=");
            if (pair.length > 1) {
                result.put(pair[0], pair[1]);
            } else {
                result.put(pair[0], "");
            }
        }
        return result;
    }

}
