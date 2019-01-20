package ru.staroverov.internship.test.Entity;
import javax.persistence.*;

@Entity
@Table(name = "part")
public class Part {


    @Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY )
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;


    @Column(name = "necessary")
    private boolean necessary;


   @Column(name = "counts")
    private int counts;

    public Part() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }



    public int getCounts() {
        return counts;
    }

    public void setCounts(int count) {
        this.counts = count;
    }

    public boolean isNecessary() {
        return necessary;
    }

    public void setNecessary(boolean necessary) {
        this.necessary = necessary;
    }

    @Override
    public String toString() {
        return "Part{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", necessary=" + necessary +
                ", counts=" + counts +
                '}';
    }
}
