/**
 *
 * @author hernanbiondini
 */
package Modelo;

import java.io.Serializable;
import java.util.Date;
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
public class InscripcionesCarreraPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "idalumno")
    private int idalumno;
    @Basic(optional = false)
    @Column(name = "idcarrera")
    private int idcarrera;
    @Basic(optional = false)
    @Column(name = "fechainscripcion")
    @Temporal(TemporalType.DATE)
    private Date fechainscripcion;

    public InscripcionesCarreraPK() {
    }

    public InscripcionesCarreraPK(int idalumno, int idcarrera, Date fechainscripcion) {
        this.idalumno = idalumno;
        this.idcarrera = idcarrera;
        this.fechainscripcion = fechainscripcion;
    }

    public int getIdalumno() {
        return idalumno;
    }

    public void setIdalumno(int idalumno) {
        this.idalumno = idalumno;
    }

    public int getIdcarrera() {
        return idcarrera;
    }

    public void setIdcarrera(int idcarrera) {
        this.idcarrera = idcarrera;
    }

    public Date getFechainscripcion() {
        return fechainscripcion;
    }

    public void setFechainscripcion(Date fechainscripcion) {
        this.fechainscripcion = fechainscripcion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idalumno;
        hash += (int) idcarrera;
        hash += (fechainscripcion != null ? fechainscripcion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InscripcionesCarreraPK)) {
            return false;
        }
        InscripcionesCarreraPK other = (InscripcionesCarreraPK) object;
        if (this.idalumno != other.idalumno) {
            return false;
        }
        if (this.idcarrera != other.idcarrera) {
            return false;
        }
        if ((this.fechainscripcion == null && other.fechainscripcion != null) || (this.fechainscripcion != null && !this.fechainscripcion.equals(other.fechainscripcion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.InscripcionesCarreraPK[ idalumno=" + idalumno + ", idcarrera=" + idcarrera + ", fechainscripcion=" + fechainscripcion + " ]";
    }
    
}
