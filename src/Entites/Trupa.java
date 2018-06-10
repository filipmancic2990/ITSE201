package Entites;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Filip
 */
@Entity
@Table(name = "trupa")
@XmlRootElement
public class Trupa {
    
    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;
    @Column(name = "naziv")
    private String name;

    public Trupa() {
    }

    public Trupa(String name) {
        this.name = name;
    }

    public Trupa(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Trupa{" + "id=" + id + ", name=" + name + '}';
    }
    
}
