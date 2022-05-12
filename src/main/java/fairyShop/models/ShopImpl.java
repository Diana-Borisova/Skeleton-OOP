package fairyShop.models;

import java.util.List;
import java.util.stream.Collectors;

public class ShopImpl implements Shop{
    @Override
    public void craft(Present present, Helper helper) {
        for (Instrument instrument :helper.getInstruments()) {
            while (!instrument.isBroken() && helper.getEnergy()>0 && !present.isDone()){
                    helper.work();

            }

        }

    }
}