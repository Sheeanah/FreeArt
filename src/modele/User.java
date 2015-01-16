package modele;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Date;

/**
 * Created by Dorian on 16/01/2015.
 */
@Entity
public class User {
    private int id;
    private String login;
    private String mdp;
    private Date dateinscri;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "login")
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Basic
    @Column(name = "mdp")
    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    @Basic
    @Column(name = "dateinscri")
    public Date getDateinscri() {
        return dateinscri;
    }

    public void setDateinscri(Date dateinscri) {
        this.dateinscri = dateinscri;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != user.id) return false;
        if (dateinscri != null ? !dateinscri.equals(user.dateinscri) : user.dateinscri != null) return false;
        if (login != null ? !login.equals(user.login) : user.login != null) return false;
        if (mdp != null ? !mdp.equals(user.mdp) : user.mdp != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (mdp != null ? mdp.hashCode() : 0);
        result = 31 * result + (dateinscri != null ? dateinscri.hashCode() : 0);
        return result;
    }
}
