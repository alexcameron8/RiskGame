package Map;

public class WorldMap {

    private Map worldMap;

    //The territories located in Australia
    private Territory easternAustralia, indonesia, newGuinea, westernAustralia;

    //The territories located in Asia
    private Territory afghanistan, china, india, irkutsk, japan, kamchatka, middleEast, mongolia, siam, siberia, ural, yakutsk;

    //The territories located in Africa
    private Territory congo, eastAfrica, egypt, madagascar, northAfrica, southAfrica;

    //The territories located in South America
    private Territory argentina, brazil, peru, venezuela;

    //The territories located in Europe
    private Territory greatBritain, iceland, northernEurope, scandinavia, southernEurope, ukraine, westernEurope;

    //The territories located in North America
    private Territory alaska, alberta, centralAmerica, easterUnitedStates, greenland, northwestTerritory, ontario, quebec, westernUnitedStates;

    public WorldMap(){
        worldMap = new Map();
        createTerritory();
        createAustralia();
    }

    private void createTerritory(){
        //Australia
        easternAustralia = new Territory("Eastern Australia");
        indonesia  = new Territory("Indonesia");
        newGuinea = new Territory("New Guinea");
        westernAustralia = new Territory("Western Australia");

        //Asia
        afghanistan = new Territory("Afghanistan");
        china = new Territory("China");
        india = new Territory("India");
        irkutsk = new Territory("Irkutsk");
        japan = new Territory("Japan");
        kamchatka = new Territory("Kamchatka");
        middleEast = new Territory("Middle East");
        mongolia = new Territory("Mongolia");
        siam = new Territory("Siam");
        siberia = new Territory("Siberia");
        ural = new Territory("Ural");
        yakutsk = new Territory("Yakutsk");

        //Africa
        congo = new Territory("Congo");
        eastAfrica = new Territory("East Africa");
        egypt = new Territory("Egypt");
        madagascar = new Territory("Madagascar");
        northAfrica = new Territory("North Africa");
        southAfrica = new Territory("South Africa");

        //South America
        argentina = new Territory("Argentina");
        brazil = new Territory("Brazil");
        peru = new Territory("Peru");
        venezuela = new Territory("Venezuela");

        //Europe
        greatBritain = new Territory("Great Britain");
        iceland = new Territory("Iceland");
        northernEurope = new Territory("NorthernEurope");
        scandinavia = new Territory("Scandinavia");
        southernEurope = new Territory("Southern Europe");
        ukraine = new Territory("Ukraine");
        westernEurope = new Territory("Western Europe");

        //North America
        alaska = new Territory("Alaska");
        alberta = new Territory("Alberta");
        centralAmerica = new Territory("Central America");
        easterUnitedStates = new Territory("Easter United States");
        greenland = new Territory("Greenland");
        northwestTerritory = new Territory("Northwest Territory");
        ontario = new Territory("Ontario");
        quebec = new Territory("Quebec");
        westernUnitedStates = new Territory("Western United States");
    }

    private void createAustralia(){
        Continent australia = new Continent("Australia");

        easternAustralia.addNeighbour(westernAustralia);
        easternAustralia.addNeighbour(newGuinea);

        indonesia.addNeighbour(siam);
        indonesia.addNeighbour(newGuinea);
        indonesia.addNeighbour(westernAustralia);

        newGuinea.addNeighbour(indonesia);
        newGuinea.addNeighbour(easternAustralia);
        newGuinea.addNeighbour(westernAustralia);

        westernAustralia.addNeighbour(indonesia);
        westernAustralia.addNeighbour(newGuinea);
        westernAustralia.addNeighbour(westernAustralia);

        australia.addTerritory(easternAustralia);
        australia.addTerritory(indonesia);
        australia.addTerritory(newGuinea);
        australia.addTerritory(westernAustralia);

        worldMap.addContinent(australia);
    }
}
