/**
 *
 * @author hernanbiondini
 */
package Modelo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author hernanbiondini
 */
@Entity
@Table(name = "inscripciones_carrera")
@NamedQueries({
    @NamedQuery(name = "InscripcionesCarrera.findAll", query = "SELECT i FROM InscripcionesCarrera i")
    , @NamedQuery(name = "InscripcionesCarrera.findByIdalumno", query = "SELECT i FROM InscripcionesCarrera i WHERE i.inscripcionesCarreraPK.idalumno = :idalumno")
    , @NamedQuery(name = "InscripcionesCarrera.findByIdcarrera", query = "SELECT i FROM InscripcionesCarrera i WHERE i.inscripcionesCarreraPK.idcarrera = :idcarrera")
    , @NamedQuery(name = "InscripcionesCarrera.findByFechainscripcion", query = "SELECT i FROM InscripcionesCarrera i WHERE i.inscripcionesCarreraPK.fechainscripcion = :fechainscripcion")})
public class InscripcionesCarrera implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected InscripcionesCarreraPK inscripcionesCarreraPK;
    @JoinColumn(name = "idalumno", referencedColumnName = "identificador", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Alumno alumno;
    @JoinColumn(name = "idcarrera", referencedColumnName = "identificador", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Carrera carrera;

    public InscripcionesCarrera() {
    }

    public InscripcionesCarrera(InscripcionesCarreraPK inscripcionesCarreraPK) {
        this.inscripcionesCarreraPK = inscripcionesCarreraPK;
    }

    public InscripcionesCarrera(int idalumno, int idcarrera, Date fechainscripcion) {
        this.inscripcionesCarreraPK = new InscripcionesCarreraPK(idalumno, idcarrera, fechainscripcion);
    }

    public InscripcionesCarreraPK getInscripcionesCarreraPK() {
        return inscripcionesCarreraPK;
    }

    public void setInscripcionesCarreraPK(InscripcionesCarreraPK inscripcionesCarreraPK) {
        this.inscripcionesCarreraPK = inscripcionesCarreraPK;
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }

    public Carrera getCarrera() {
        return carrera;
    }

    public void setCarrera(Carrera carrera) {
        this.carrera = carrera;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (inscripcionesCarreraPK != null ? inscripcionesCarreraPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InscripcionesCarrera)) {
            return false;
        }
        InscripcionesCarrera other = (InscripcionesCarrera) object;
        if ((this.inscripcionesCarreraPK == null && other.inscripcionesCarreraPK != null) || (this.inscripcionesCarreraPK != null && !this.inscripcionesCarreraPK.equals(other.inscripcionesCarreraPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.InscripcionesCarrera[ inscripcionesCarreraPK=" + inscripcionesCarreraPK + " ]";
    }
    
}
