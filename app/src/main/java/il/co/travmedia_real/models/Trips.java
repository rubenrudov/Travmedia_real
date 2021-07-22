package il.co.travmedia_real.models;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Trips {

    private Trip[] summerTrips;
    private Trip[] fallTrips;
    private Trip[] winterTrips;
    private Trip[] springTrips;
    private String season;

    public Trips(String season) {
        this.season = season;

        // For now, all the trips are the same for all seasons

        switch (season) {
            case "summer":
                this.summerTrips = findTrips(this.season);
                break;
            case "fall":
                this.fallTrips = findTrips(this.season);
                break;
            case "winter":
                this.winterTrips = findTrips(this.season);
                break;
            default:
                this.springTrips = findTrips(this.season);
                break;
        }
    }

    private Trip[] findTrips(String season) {
        Trip[] arrTemp = new Trip[3];

        Trip t1 = new Trip(
                    "Amalfi coast - Italy",
                    "https://firebasestorage.googleapis.com/v0/b/travmedia-prod.appspot.com/o/ravello.jpg?alt=media&token=7d0e791a-fe3b-46ad-a860-f8b1d1998d05",
                    "The most common attractions of this coast are the magical roads next to its" +
                            "beautiful beaches and mounts but, there are a lot of places to visit and we'll tell you some of the most interesting" +
                            "place to visit during your stay there\n\n" +
                            "Ravello\n" +
                            "A lovely town on a top of a mount that has a view of the Salerno bay, you can visit in Villa Rufolo and " +
                            "visit some of the porcelain shops found there.\n\n" +
                            "Amalfi\n" +
                            "The road from Sorrento to Amalfi is considered to one of the most beautiful roads in the world" +
                            " in the town itself you can by spending time and enjoy the markets that offer you nice Italian local food," +
                            " going to the beautiful beaches and enjoy its pleasant water. and even take a ride on a boat to the Capri island"

            );
        arrTemp[0] = t1;
        Trip t2 = new Trip(
                    "Portugal - Lisbon",
                "https://firebasestorage.googleapis.com/v0/b/travmedia-prod.appspot.com/o/saojorge.jpg?alt=media&token=ffa9642b-b41f-4b5e-a797-4ff1ae09f9d4",
                    "In Portugal there are a lot of locations you should visit and it could take more than a single vacation for it, in this" +
                            "trip I've collected some places I've been to at this time of the year that I can personally recommend you\n" +
                            "Lisboa (Lisbon)\n" +
                            "The capital city of portugal, located next to the atlantic ocean and " +
                            "represents the perfect combination of culture and authenticity.\n\n" +
                            "St. jorge monastery\n" +
                            "A note for the beginning, I personally recommend you, avoid renting a car while you stay in Lisboa, " +
                            "the public transport in this city is good and you can buy the '7 Colinas' ticket for all public transports (6.30 € per day + 0.5 € for the ticket itself when you first purchase it)"+
                            " and traveling between locations.\n" +
                            "About St. jorge monastery, from this castle you can see a beautiful 360^ view on the city and travel nearby (I recommend to take the 28 electric train" +
                            "and leave it before the castle and go for a little walk next to the castle). " +
                            "After visiting the castle, you can travel around the nearby streets (for the lazy people among us, you can keep riding the 28 tram. it'll take you next to all the must see locations)\n\n" +
                            "Pasteis de Bele\n" +
                            "The most famous conditory in Lisboa, here you can buy the famous 'pastel the nata'\n\n" +
                            "Sintra\n" +
                            "About 30km from Lisboa, there is a tiny, magical town called Sintra that almost every tourist visits during his\\er stay in Lisboa\n" +
                            "Here you can find a super beautiful historical center that considered as a Unesco World Heritage Site\n\n" +
                            "Belem\n" +
                            "This place represents the Portuguese empire in its prime age, here you can visit in the Belem tower," +
                            " Monument for the Discoveries, and Jerónimos Monastery in which the famous explorer, Vasco de-Gama buried"

            );
        arrTemp[1] = t2;

        Trip t3 = new Trip(
                    "Azores",
                    "https://firebasestorage.googleapis.com/v0/b/travmedia-prod.appspot.com/o/azores.jpg?alt=media&token=9943d4a4-9355-4369-8fc5-d5bd8d8257b7",
                    "A group of 9 volcanic islands, one of the autonomous regions of Portugal that found in the middle of the atlantic ocean about 1400km from Lisboa" +
                            " and about 3900km from U.S's east coast-\n\n" +
                            "Sao Miguel island\n" +
                            "Pico island\n"

            );
        arrTemp[2] = t3;

        return arrTemp;
    }

    public Trip[] getTrips() {
        if (summerTrips != null) {
            return summerTrips;
        }
        else if (fallTrips != null) {
            return fallTrips;
        }
        else if (winterTrips != null) {
            return winterTrips;
        }
        else {
            return springTrips;
        }
    }
}
