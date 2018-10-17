/**
 *
 * @author hernanbiondini
 */
package Modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author hernanbiondini
 */
@Embeddable
public class InscripcionesCursoPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "idalumno")
    private int idalumno;
    @Basic(optional = false)
    @Column(name = "idcurso")
    private int idcurso;
    @Basic(optional = false)
    @Column(name = "fechainicio")
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;

    public InscripcionesCursoPK() {
    }

    public InscripcionesCursoPK(int idalumno, int idcurso, Date fechaInicio) {
        this.idalumno = idalumno;
        this.idcurso = idcurso;
        this.fechaInicio = fechaInicio;
    }

    public int getIdalumno() {
        return idalumno;
    }

    public void setIdalumno(int idalumno) {
        this.idalumno = idalumno;
    }

    public int getIdcurso() {
        return idcurso;
    }

    public void setIdcurso(int idcurso) {
        this.idcurso = idcurso;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.idalumno;
        hash = 97 * hash + this.idcurso;
        hash = 97 * hash + Objects.hashCode(this.fechaInicio);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final InscripcionesCursoPK other = (InscripcionesCursoPK) obj;
        if (this.idalumno != other.idalumno) {
            return false;
        }
        if (this.idcurso != other.idcurso) {
            return false;
        }
        if (!Objects.equals(this.fechaInicio, other.fechaInicio)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "InscripcionesCursoPK{" + "idalumno=" + idalumno + ", idcurso=" + idcurso + ", fechaInicio=" + fechaInicio + '}';
    }
    
}
