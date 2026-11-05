package utils.data;

import utils.classesandenums.Classes;
import utils.classesandenums.Enums;

public class Data {
    /* GAME */
    public static Classes.Upgrade[] upgrs = {
        /* AUD */
        new Classes.Upgrade("Aussie Spirit", 50, Enums.Currency.AUD, 2, false, 0),
        new Classes.Upgrade("Sausage Sizzle", 100, Enums.Currency.AUD, 1, false, 1),
        new Classes.Upgrade("$$$ Didgeridoo", 200, Enums.Currency.AUD, 4, false, 2),
        new Classes.Upgrade("Buy Bunnings", 1000, Enums.Currency.AUD, 4, false, 3),
        new Classes.Upgrade("Annex NZ", 10000, Enums.Currency.AUD, 1, false, 4),
        new Classes.Upgrade("Reboot Card", 1000000, Enums.Currency.AUD, 1, false, 5),
        /* NZD */
        new Classes.Upgrade("Buy Raro makers", 500, Enums.Currency.NZD, 2, false, 6),
        new Classes.Upgrade("P. Jackson Movie", 1500, Enums.Currency.NZD, 2, false, 7),
        new Classes.Upgrade("Own Bluey", 4444, Enums.Currency.NZD, 2, false, 8),
        new Classes.Upgrade("Open a Timezone", 7500, Enums.Currency.NZD, 2, false, 9),
        new Classes.Upgrade("To be continued.", Long.MAX_VALUE, Enums.Currency.NZD, 1, false, 10)
    };

    /* DISPLAY */
    public static Classes.UpgradeDisplay[] auUpgradeDisplays = new Classes.UpgradeDisplay[6];

    public static Classes.UpgradeDisplay[] nzUpgradeDisplays = new Classes.UpgradeDisplay[6];
}
