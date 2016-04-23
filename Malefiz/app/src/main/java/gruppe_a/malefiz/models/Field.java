package gruppe_a.malefiz.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by worm on 13.04.16.
 */
public class Field {
    private int id;
    private List<Field> children;

    public Field(int id) {
        this.id = id;
        this.children = new ArrayList<Field>();
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Field> getChildren() {
        return children;
    }

    public void setChildren(List<Field> children) {
        this.children = children;
    }
}
