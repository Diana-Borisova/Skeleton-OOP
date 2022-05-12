package fairyShop.repositories;

import fairyShop.models.Helper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public class HelperRepository implements Repository<Helper> {
    private Collection<Helper> helpers;

    public HelperRepository() {
        this.helpers = new ArrayList<>();
    }

    @Override
    public Collection<Helper> getModels() {
        return this.helpers.stream().collect(Collectors.toUnmodifiableList());
    }

    @Override
    public void add(Helper helper){
        this.helpers.add(helper);
    }

    @Override
    public boolean remove(Helper helper) {
        return this.helpers.remove(helper);
    }

    @Override
    public Helper findByName(String name) {
        return this.helpers.stream().filter(helper -> helper.getName().equals(name)).findFirst().orElse(null);
    }

    public Collection<Helper> getHelpers() {
        return helpers;
    }

    public void setHelpers(Collection<Helper> helpers) {
        this.helpers = helpers;
    }
}
