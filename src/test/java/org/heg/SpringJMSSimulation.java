package org.heg;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import java.time.Duration;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

/**
 * Cette classe test le endpoint rest /isalive
 * du server spring3-jms
 */
public class SpringJMSSimulation extends Simulation {

    HttpProtocolBuilder httpProtocol = http
            .baseUrl("http://localhost:8088") // URL de base
            .inferHtmlResources();

    ScenarioBuilder scn = scenario("SpringJMSSimulation")
            .exec(
                    http("get isAlive")
                            .get("/isalive") // Chemin de la requête
                            .check(status().is(200))
            );

    {
        setUp(
                scn.injectOpen(
                        atOnceUsers(10), // Démarrer avec 10 utilisateurs
                        rampUsers(100).during(Duration.ofSeconds(30)) // Augmenter jusqu'à 100 utilisateurs en 30 secondes
                ).protocols(httpProtocol)
        );
    }
}
