package uk.co.compendiumdev.challenge;

import uk.co.compendiumdev.thingifier.Thingifier;
import uk.co.compendiumdev.thingifier.application.MainImplementation;
import uk.co.compendiumdev.thingifier.application.ThingifierRestServer;
import uk.co.compendiumdev.thingifier.application.routehandlers.ShutdownRouteHandler;

public class Main {

    public static void main(String[] args) {


        MainImplementation app = new MainImplementation();
        Thingifier thingifier = new ChallengeApiModel().get();
        app.registerModel("challengeapi", thingifier);

        // add any additional thingifier configurations here if more needed than model has defined
        app.setDefaultsFromArgs(args);

        app.configurePortAndDefaultRoutes();
        app.setupBuiltInConfigurableRoutes();

        // setup routes required for challenges
        ChallengeRouteHandler challenger = new ChallengeRouteHandler(thingifier);
        challenger.configureRoutes();

        app.addAdditionalRoutes(challenger.getRoutes());

        app.chooseThingifier();
        // can set profile by adding more configs, or just
        // app.setProfileToUse(aProfile)
        app.configureThingifierWithProfile();

        app.setupDefaultGui();
        challenger.setupGui(app.getGuiManagement());

        final ThingifierRestServer restServer = app.startRestServer();

        app.addBuiltInArgConfiguredHooks();

        challenger.addHooks(restServer);

    }
}
