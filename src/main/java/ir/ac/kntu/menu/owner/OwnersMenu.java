package ir.ac.kntu.menu.owner;

import ir.ac.kntu.menu.Menu;
import ir.ac.kntu.models.Owner;
import ir.ac.kntu.models.ShopsDBReference;

public class OwnersMenu extends Menu {

    private final Owner owner;

    private final ShopsDBReference shopsDBReference;

    public OwnersMenu(Owner owner, ShopsDBReference shopsDBReference) {
        this.owner = owner;
        this.shopsDBReference = shopsDBReference;
    }

    @Override
    public void show() {

    }
}
