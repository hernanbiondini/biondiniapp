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
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author hernanbiondini
 */
@Entity
@Table(name = "inscripciones_curso")
@NamedQueries({
    @NamedQuery(name = "InscripcionesCurso.findAll", query = "SELECT i FROM InscripcionesCurso i")
        , @NamedQuery(name = "InscripcionesCurso.findByIdalumno", query = "SELECT i FROM InscripcionesCurso i WHERE i.inscripcionesCursoPK.idalumno = :idalumno")
        , @NamedQuery(name = "InscripcionesCurso.findByIdcurso", query = "SELECT i FROM InscripcionesCurso i WHERE i.inscripcionesCursoPK.idcurso = :idcurso")
})

public class InscripcionesCurso implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected InscripcionesCursoPK inscripcionesCursoPK;
    @JoinColumn(name = "idalumno", referencedColumnName = "identificador", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Alumno alumno;
    @JoinColumn(name = "idcurso", referencedColumnName = "identificador", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Curso curso;
    @Basic(optional = false)
    @Column(name = "resultado")
    private String resultado;
    @Basic(optional = false)
    @Column(name = "nota")
    private int nota;
    @Basic(optional = false)
    @Column(name = "fechainscripcion")
    @Temporal(TemporalType.DATE)
    private Date fechaInscripcion;

    public InscripcionesCurso() {
    }

    public InscripcionesCurso(InscripcionesCursoPK inscripcionesCursoPK, Alumno alumno, Curso curso, String resultado, int nota, Date fechaInscripcion) {
        this.inscripcionesCursoPK = inscripcionesCursoPK;
        this.alumno = alumno;
        this.curso = curso;
        this.resultado = resultado;
        this.nota = nota;
        this.fechaInscripcion = fechaInscripcion;
    }

    public InscripcionesCursoPK getInscripcionesCursoPK() {
        return inscripcionesCursoPK;
    }

    public void setInscripcionesCursoPK(InscripcionesCursoPK inscripcionesCursoPK) {
        this.inscripcionesCursoPK = inscripcionesCursoPK;
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public int getNota() {
        return nota;
    }

    public void setNota(int nota) {
        this.nota = nota;
    }

    public Date getFechaInscripcion() {
        return fechaInscripcion;
    }

    public void setFechaInscripcion(Date fechaInscripcion) {
        this.fechaInscripcion = fechaInscripcion;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.inscripcionesCursoPK);
        hash = 89 * hash + Objects.hashCode(this.alumno);
        hash = 89 * hash + Objects.hashCode(this.curso);
        hash = 89 * hash + Objects.hashCode(this.resultado);
        hash = 89 * hash + this.nota;
        hash = 89 * hash + Objects.hashCode(this.fechaInscripcion);
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
        final InscripcionesCurso other = (InscripcionesCurso) obj;
        if (this.nota != other.nota) {
            return false;
        }
        if (!Objects.equals(this.resultado, other.resultado)) {
            return false;
        }
        if (!Objects.equals(this.inscripcionesCursoPK, other.inscripcionesCursoPK)) {
            return false;
        }
        if (!Objects.equals(this.alumno, other.alumno)) {
            return false;
        }
        if (!Objects.equals(this.curso, other.curso)) {
            return false;
        }
        if (!Objects.equals(this.fechaInscripcion, other.fechaInscripcion)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "InscripcionesCurso{" + "inscripcionesCursoPK=" + inscripcionesCursoPK + ", alumno=" + alumno + ", curso=" + curso + ", resultado=" + resultado + ", nota=" + nota + ", fechaInscripcion=" + fechaInscripcion + '}';
    }

}
