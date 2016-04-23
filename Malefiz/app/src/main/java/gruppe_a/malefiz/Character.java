package gruppe_a.malefiz;

/**
 * Created by MCLeite on 23.04.2016.
 */
public class Character {
    private int id;
    private boolean isSelected;
    private String imagePath;

    public Character(int id, String imagePath) {
        this.id = id;
        this.isSelected = false;
        this.imagePath = imagePath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
