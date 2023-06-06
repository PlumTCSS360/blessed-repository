package model;

public class ListItem {

    private String name;

    private boolean isDone;

    public ListItem(String name) {
        this.name = name;
        this.isDone = false;
    }

    public void markAsDone() {
        this.isDone = true;
    }

    @Override
    public String toString() {
        return name;
    }


}
