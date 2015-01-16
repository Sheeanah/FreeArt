package modele;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

/**
 * Created by Dorian on 16/01/2015.
 */
@Entity
@IdClass(ImagetagPK.class)
public class Imagetag {
    private int imageid;
    private int tagid;

    @Id
    @Column(name = "imageid")
    public int getImageid() {
        return imageid;
    }

    public void setImageid(int imageid) {
        this.imageid = imageid;
    }

    @Id
    @Column(name = "tagid")
    public int getTagid() {
        return tagid;
    }

    public void setTagid(int tagid) {
        this.tagid = tagid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Imagetag imagetag = (Imagetag) o;

        if (imageid != imagetag.imageid) return false;
        if (tagid != imagetag.tagid) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = imageid;
        result = 31 * result + tagid;
        return result;
    }
}
