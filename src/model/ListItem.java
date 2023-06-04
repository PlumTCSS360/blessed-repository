package model;

public class ListItem implements Comparable<ListItem>{

    private String name;
    private Integer priority;
    private boolean isDone;

    public ListItem(String name, Integer priority) {
        this.name = name;
        this.priority = priority;
        this.isDone = false;
    }

    public void markAsDone() {
        this.isDone = true;
    }

    @Override
    public String toString() {
        return name + " [" + priority + "]";
    }

    @Override
    public int compareTo(ListItem other) {
        return this.priority.compareTo(other.priority);
    }

}
