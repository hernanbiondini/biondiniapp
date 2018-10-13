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
public class InscripcionesCursoPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "idalumno")
    private int idalumno;
    @Basic(optional = false)
    @Column(name = "idcurso")
    private int idcurso;
    @Basic(optional = false)
    @Column(name = "fechainscripcion")
    @Temporal(TemporalType.DATE)
    private Date fechainscripcion;

    public InscripcionesCursoPK() {
    }

    public InscripcionesCursoPK(int idalumno, int idcurso, Date fechainscripcion) {
        this.idalumno = idalumno;
        this.idcurso = idcurso;
        this.fechainscripcion = fechainscripcion;
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
        hash += (int) idcurso;
        hash += (fechainscripcion != null ? fechainscripcion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InscripcionesCursoPK)) {
            return false;
        }
        InscripcionesCursoPK other = (InscripcionesCursoPK) object;
        if (this.idalumno != other.idalumno) {
            return false;
        }
        if (this.idcurso != other.idcurso) {
            return false;
        }
        if ((this.fechainscripcion == null && other.fechainscripcion != null) || (this.fechainscripcion != null && !this.fechainscripcion.equals(other.fechainscripcion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.InscripcionesCursoPK[ idalumno=" + idalumno + ", idcurso=" + idcurso + ", fechainscripcion=" + fechainscripcion + " ]";
    }
    
}
