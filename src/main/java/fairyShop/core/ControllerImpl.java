package fairyShop.core;

import fairyShop.common.ConstantMessages;
import fairyShop.common.ExceptionMessages;
import fairyShop.models.*;
import fairyShop.repositories.HelperRepository;
import fairyShop.repositories.PresentRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class ControllerImpl implements Controller{
    private HelperRepository helperRepository;
    private PresentRepository presentRepository;
private int countDonePresents;
    private int brokenInstrument;
   private List<Helper> suitableHelpers;


    public ControllerImpl() {
        this.helperRepository = new HelperRepository();
        this.presentRepository = new PresentRepository();
        this.suitableHelpers = new ArrayList<>();
    }

    @Override
    public String addHelper(String type, String helperName) {
        if (!type.equals("Happy")&& !type.equals("Sleepy")){
            throw new IllegalArgumentException(ExceptionMessages.HELPER_TYPE_DOESNT_EXIST);
        }
        Helper helper = null;
        if (type.equals("Happy")){
            helper = new Happy(helperName);
        } else {

            helper = new Sleepy(helperName);
        }
        this.helperRepository.add(helper);
        return String.format(ConstantMessages.ADDED_HELPER, type, helperName);
    }

    @Override
    public String addInstrumentToHelper(String helperName, int power) {
if (this.helperRepository.getModels().stream().noneMatch(helper -> helper.getName().equals(helperName))){
    throw new IllegalArgumentException(ExceptionMessages.HELPER_DOESNT_EXIST);
}
        Instrument instrument = new InstrumentImpl(power);
Helper currentHelper = this.helperRepository.getModels().stream().filter(helper -> helper.getName().equals(helperName)).findFirst().orElse(null);
if (currentHelper !=null){
    currentHelper.addInstrument(instrument);
}

return String.format(ConstantMessages.SUCCESSFULLY_ADDED_INSTRUMENT_TO_HELPER, power,helperName);
    }

    @Override
    public String addPresent(String presentName, int energyRequired) {
        Present present = new PresentImpl(presentName,energyRequired);
        this.presentRepository.add(present);
        return String.format(ConstantMessages.SUCCESSFULLY_ADDED_PRESENT,presentName);
    }

    @Override
    public String craftPresent(String presentName) {
        suitableHelpers = this.helperRepository.getModels().stream().filter(helper -> helper.getEnergy()>50).collect(Collectors.toList());
         int countCraftedPresents = 0;
        if (suitableHelpers.isEmpty()){
            throw new IllegalArgumentException(ExceptionMessages.NO_HELPER_READY);

        }
        StringBuilder sb = new StringBuilder();
        for (Helper helper : suitableHelpers) {
            if (helper.canWork()) {
                Present currentPresent = this.presentRepository.getModels().stream()
                        .filter(present -> present.getName().equals(presentName)).findFirst().orElse(null);
                List<Instrument> instrumentList = helper.getInstruments().stream().filter(instrument -> !instrument.isBroken()).collect(Collectors.toList());
                for (Instrument instrument : instrumentList) {
                    while (!currentPresent.isDone() && !instrument.isBroken()) {
                        helper.work();
                        currentPresent.getCrafted();
                        instrument.use();

                        if (instrument.isBroken()) {
                            brokenInstrument++;

                        }
                        if (currentPresent.isDone()) {
                            countCraftedPresents++;
                            countDonePresents++;
                            break;
                        }

                    }
                    if (currentPresent.isDone()) {

                        break;
                    }
                }
            }

        }

        if (countCraftedPresents != 0) {
            sb.append(String.format(ConstantMessages.PRESENT_DONE, presentName, "done"));
        } else {
            sb.append(String.format(ConstantMessages.PRESENT_DONE, presentName, "not done"));

        }
        if (brokenInstrument == 0){
            sb.append(String.format(ConstantMessages.COUNT_BROKEN_INSTRUMENTS, 0)).append(System.lineSeparator());
        } else {
            sb.append(String.format(ConstantMessages.COUNT_BROKEN_INSTRUMENTS, brokenInstrument)).append(System.lineSeparator());
        }
        return sb.toString().trim();
    }
    @Override
    public String report() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%d presents are done!", this.countDonePresents)).append(System.lineSeparator());
        sb.append("Helpers info:").append(System.lineSeparator());
        for (Helper helper :this.helperRepository.getModels()) {
            sb.append(String.format("Name: %s", helper.getName())).append(System.lineSeparator());
            sb.append(String.format("Energy: %d", helper.getEnergy())).append(System.lineSeparator());
            sb.append(String.format("Instruments: %d not broken left",helper
                    .getInstruments().stream()
                    .filter(instrument -> !instrument.isBroken()).count())).append(System.lineSeparator());
        }

        return sb.toString().trim();
    }
}
