package fairyShop.models;

import fairyShop.common.ExceptionMessages;

import java.util.ArrayList;
import java.util.Collection;


public abstract class BaseHelper implements Helper{

    private int energy;
    private Collection<Instrument> instruments;

    public BaseHelper(String name, int energy) {
        this.setName(name);
        this.setEnergy(energy);
        this.instruments = new ArrayList<>();
    }

    @Override
    public void work() {
    this.setEnergy(this.energy -10);
    }

    @Override
    public void addInstrument(Instrument instrument) {
    this.instruments.add(instrument);
    }

    @Override
    public boolean canWork() {
        return this.energy>0;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int getEnergy() {
        return this.energy;
    }

    @Override
    public Collection<Instrument> getInstruments() {
        return this.instruments;
    }

    public void setName(String name) {
        if (name==null || name.trim().isEmpty()){
            throw new NullPointerException(ExceptionMessages.HELPER_NAME_NULL_OR_EMPTY);
        }
        this.name = name;
    }

    public void setEnergy(int energy) {
        this.energy = Math.max(energy, 0);

    }

    public void setInstruments(Collection<Instrument> instruments) {
        this.instruments = instruments;
    }
}
