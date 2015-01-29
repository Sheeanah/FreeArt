package modele;

import javax.persistence.*;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Dorian on 16/01/2015.
 */
@Entity
public class Image {
    private int id;
    private String image;
    private Date dateajout;
    private String titre;
    private String description;
    private int auteur;
    private int categorie;



    private Set<Tag> tags = new HashSet<Tag>(0);

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "image")
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Basic
    @Column(name = "dateajout")
    public Date getDateajout() {
        return dateajout;
    }

    public void setDateajout(Date dateajout) {
        this.dateajout = dateajout;
    }

    @Basic
    @Column(name = "titre")
    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    @Basic
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Image image1 = (Image) o;

        if (id != image1.id) return false;
        if (dateajout != null ? !dateajout.equals(image1.dateajout) : image1.dateajout != null) return false;
        if (description != null ? !description.equals(image1.description) : image1.description != null) return false;
        if (image != null ? !image.equals(image1.image) : image1.image != null) return false;
        if (titre != null ? !titre.equals(image1.titre) : image1.titre != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (image != null ? image.hashCode() : 0);
        result = 31 * result + (dateajout != null ? dateajout.hashCode() : 0);
        result = 31 * result + (titre != null ? titre.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    @Basic
    @Column(name = "auteur")
    public int getAuteur() {
        return auteur;
    }

    public void setAuteur(int auteur) {
        this.auteur = auteur;
    }

    @Basic
    @Column(name = "categorie")
    public int getCategorie() {
        return categorie;
    }

    public void setCategorie(int categorie) {
        this.categorie = categorie;
    }


}
