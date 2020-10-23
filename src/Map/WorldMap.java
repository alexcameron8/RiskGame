package Map;
/**
 * WorldMap is a default map for the game of Risk
 *
 * @version 1.9
 * Thomas Dunnigan
 * 101115382
 */

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


    /**
     * create a defult preset world map
     */
    public WorldMap(){
        worldMap = new Map();
        createTerritory();
        worldMap.addContinent(createAustralia());
        worldMap.addContinent(createAsia());
        worldMap.addContinent(createAfrica());
        worldMap.addContinent(createEurope());
        worldMap.addContinent(createNorthAmerica());
        worldMap.addContinent(createSouthAmerica());
    }

    /**
     * return the map
     *
     * @return Map
     */
    public Map getWorldMap(){
        return worldMap;
    }

    /**
     * create all the territories
     */
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

    /**
     * create a continent asia
     * add all the neighbours for each territory in asia
     *
     * @return asia
     */
    private Continent createAsia(){
        Continent asia = new Continent("Asia");

        afghanistan.addNeighbours(ukraine,ural,china,india,middleEast);
        china.addNeighbours(siam,india,afghanistan,ural,siberia,mongolia);
        india.addNeighbours(middleEast,afghanistan,china,siam);
        irkutsk.addNeighbours(yakutsk,kamchatka,mongolia,siberia);
        japan.addNeighbours(kamchatka,mongolia);
        kamchatka.addNeighbours(yakutsk,irkutsk,mongolia,japan,alaska);
        middleEast.addNeighbours(eastAfrica,egypt,southernEurope,ukraine,afghanistan,india);
        mongolia.addNeighbours(japan,kamchatka,irkutsk,siberia,china);
        siam.addNeighbours(india,china,indonesia);
        siberia.addNeighbours(ural,china,mongolia,irkutsk,yakutsk);
        ural.addNeighbours(ukraine,afghanistan,china,siberia);
        yakutsk.addNeighbours(siberia,irkutsk,kamchatka);

        asia.addTerritories(afghanistan,china,india,irkutsk,japan,kamchatka,middleEast,mongolia,siam,siberia,ural,yakutsk);

        return asia;
    }

    /**
     * create a continent Australia
     * add all the neighbours for each territory in Australia
     *
     * @return Australia
     */
    private Continent createAustralia(){
        Continent australia = new Continent("Australia");

        easternAustralia.addNeighbours(westernAustralia,newGuinea);
        indonesia.addNeighbours(siam,newGuinea,westernAustralia);
        newGuinea.addNeighbours(indonesia,easternAustralia,westernAustralia);
        westernAustralia.addNeighbours(indonesia,newGuinea,westernAustralia);

        australia.addTerritories(easternAustralia,indonesia,newGuinea,westernAustralia);
        return australia;
    }

    /**
     * create a continent Africa
     * add all the neighbours for each territory in Africa
     *
     * @return Africa
     */
    private Continent createAfrica(){
        Continent africa = new Continent("Africa");

        congo.addNeighbours(eastAfrica,southAfrica,northAfrica);
        eastAfrica.addNeighbours(egypt,northAfrica,congo,southAfrica,madagascar,middleEast);
        egypt.addNeighbours(southernEurope,northAfrica,eastAfrica,middleEast);
        madagascar.addNeighbours(southAfrica,eastAfrica);
        northAfrica.addNeighbours(brazil,westernEurope,southernEurope,egypt,eastAfrica,congo);
        southAfrica.addNeighbours(congo,eastAfrica,madagascar);

        africa.addTerritories(congo,eastAfrica,egypt,madagascar,northAfrica,southAfrica);

        return africa;
    }

    /**
     * create a continent South America
     * add all the neighbours for each territory in South America
     *
     * @return South America
     */
    private Continent createSouthAmerica(){
        Continent southAmerica = new Continent("South America");

        argentina.addNeighbours(peru,brazil);
        brazil.addNeighbours(peru,venezuela,argentina,northAfrica);
        peru.addNeighbours(venezuela,brazil,peru);
        venezuela.addNeighbours(centralAmerica,brazil,peru);

        southAmerica.addTerritories(argentina, brazil, peru, venezuela);
        return southAmerica;
    }

    /**
     * create a continent Europe
     * add all the neighbours for each territory in Europe
     *
     * @return Europe
     */
    private Continent createEurope(){
        Continent europe = new Continent("Europe");

        greatBritain.addNeighbours(iceland,scandinavia,northernEurope,westernEurope);
        iceland.addNeighbours(greatBritain, greenland, scandinavia);
        northernEurope.addNeighbours(ukraine,scandinavia,greatBritain,westernEurope,southernEurope);
        scandinavia.addNeighbours(greatBritain,iceland,northernEurope,ukraine);
        southernEurope.addNeighbours(westernEurope,northernEurope,ukraine,middleEast,egypt,northAfrica);
        ukraine.addNeighbours(scandinavia,northernEurope,southernEurope,middleEast,argentina,ural);
        westernEurope.addNeighbours(greatBritain,northernEurope,southernEurope,northAfrica);

        europe.addTerritories(greatBritain,iceland,northernEurope,scandinavia,southernEurope,ukraine,westernEurope);
        return europe;
    }

    /**
     * create a continent North America
     * add all the neighbours for each territory in North America
     *
     * @return North America
     */
    private Continent createNorthAmerica(){
        Continent NorthAmerica = new Continent("North America");

        alaska.addNeighbours(northwestTerritory,alberta,kamchatka);
        alberta.addNeighbours(ontario,northwestTerritory,westernUnitedStates,alaska);
        centralAmerica.addNeighbours(westernUnitedStates,venezuela,easterUnitedStates);
        easterUnitedStates.addNeighbours(quebec,ontario,westernUnitedStates,centralAmerica);
        greenland.addNeighbours(quebec,northwestTerritory,ontario,iceland);
        northwestTerritory.addNeighbours(alaska,alberta,ontario,greenland);
        ontario.addNeighbours(northwestTerritory,alberta,westernUnitedStates,easterUnitedStates,quebec,greenland);
        quebec.addNeighbours(easterUnitedStates,ontario,greenland);
        westernUnitedStates.addNeighbours(alberta,ontario,easterUnitedStates,centralAmerica);

        NorthAmerica.addTerritories(alaska, alberta, centralAmerica, easterUnitedStates, greenland, northwestTerritory, ontario, quebec, westernUnitedStates);
        return NorthAmerica;
    }
}