/**
 *
 * @author hernanbiondini
 */
package Modelo;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author hernanbiondini
 */
@Entity
@Table(name = "curso")
@NamedQueries({
    @NamedQuery(name = "Curso.findAll", query = "SELECT c FROM Curso c")
    , @NamedQuery(name = "Curso.findByIdentificador", query = "SELECT c FROM Curso c WHERE c.identificador = :identificador")
    , @NamedQuery(name = "Curso.findByNombre", query = "SELECT c FROM Curso c WHERE c.nombre = :nombre")
    , @NamedQuery(name = "Curso.findByDescripcion", query = "SELECT c FROM Curso c WHERE c.descripcion = :descripcion")
    , @NamedQuery(name = "Curso.findByCupomaximo", query = "SELECT c FROM Curso c WHERE c.cupomaximo = :cupomaximo")
    , @NamedQuery(name = "Curso.findByAnio", query = "SELECT c FROM Curso c WHERE c.anio = :anio")})
public class Curso implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "identificador")
    private Integer identificador;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "descripcion")
    private String descripcion;
    @Basic(optional = false)
    @Column(name = "cupomaximo")
    private short cupomaximo;
    @Basic(optional = false)
    @Column(name = "anio")
    private short anio;
    @JoinColumn(name = "idcarrera", referencedColumnName = "identificador")
    @ManyToOne
    private Carrera idcarrera;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "curso")
    private Collection<InscripcionesCurso> inscripcionesCursoCollection;

    public Curso() {
    }

    public Curso(Integer identificador) {
        this.identificador = identificador;
    }

    public Curso(Integer identificador, String nombre, short cupomaximo, short anio) {
        this.identificador = identificador;
        this.nombre = nombre;
        this.cupomaximo = cupomaximo;
        this.anio = anio;
    }

    public Integer getIdentificador() {
        return identificador;
    }

    public void setIdentificador(Integer identificador) {
        this.identificador = identificador;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public short getCupomaximo() {
        return cupomaximo;
    }

    public void setCupomaximo(short cupomaximo) {
        this.cupomaximo = cupomaximo;
    }

    public short getAnio() {
        return anio;
    }

    public void setAnio(short anio) {
        this.anio = anio;
    }

    public Carrera getIdcarrera() {
        return idcarrera;
    }

    public void setIdcarrera(Carrera idcarrera) {
        this.idcarrera = idcarrera;
    }

    public Collection<InscripcionesCurso> getInscripcionesCursoCollection() {
        return inscripcionesCursoCollection;
    }

    public void setInscripcionesCursoCollection(Collection<InscripcionesCurso> inscripcionesCursoCollection) {
        this.inscripcionesCursoCollection = inscripcionesCursoCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (identificador != null ? identificador.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Curso)) {
            return false;
        }
        Curso other = (Curso) object;
        if ((this.identificador == null && other.identificador != null) || (this.identificador != null && !this.identificador.equals(other.identificador))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.Curso[ identificador=" + identificador + " ]";
    }
    
}
