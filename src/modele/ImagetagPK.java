package modele;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by Dorian on 16/01/2015.
 */
public class ImagetagPK implements Serializable {
    private int imageid;
    private int tagid;

    @Column(name = "imageid")
    @Id
    public int getImageid() {
        return imageid;
    }

    public void setImageid(int imageid) {
        this.imageid = imageid;
    }

    @Column(name = "tagid")
    @Id
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

        ImagetagPK that = (ImagetagPK) o;

        if (imageid != that.imageid) return false;
        if (tagid != that.tagid) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = imageid;
        result = 31 * result + tagid;
        return result;
    }
}
