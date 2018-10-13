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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author hernanbiondini
 */
@Entity
@Table(name = "alumno")
@NamedQueries({
    @NamedQuery(name = "Alumno.findAll", query = "SELECT a FROM Alumno a")
    , @NamedQuery(name = "Alumno.findByIdentificador", query = "SELECT a FROM Alumno a WHERE a.identificador = :identificador")
    , @NamedQuery(name = "Alumno.findByLegajo", query = "SELECT a FROM Alumno a WHERE a.legajo = :legajo")})
public class Alumno implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "identificador")
    private Integer identificador;
    @Basic(optional = false)
    @Column(name = "legajo")
    private int legajo;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "alumno")
    private Collection<InscripcionesCurso> inscripcionesCursoCollection;
   
    @JoinColumn(name = "idpersona", referencedColumnName = "identificador")
    @OneToOne
    private Persona idpersona;
   
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "alumno")
    private Collection<InscripcionesCarrera> inscripcionesCarreraCollection;

    public Alumno() {
    }

    public Alumno(int legajo, Persona idpersona) {
        this.legajo = legajo;
        this.idpersona = idpersona;
    }
    
    public Alumno(Integer identificador) {
        this.identificador = identificador;
    }

    public Alumno(Integer identificador, int legajo) {
        this.identificador = identificador;
        this.legajo = legajo;
    }

    public Integer getIdentificador() {
        return identificador;
    }

    public void setIdentificador(Integer identificador) {
        this.identificador = identificador;
    }

    public int getLegajo() {
        return legajo;
    }

    public void setLegajo(int legajo) {
        this.legajo = legajo;
    }

    public Collection<InscripcionesCurso> getInscripcionesCursoCollection() {
        return inscripcionesCursoCollection;
    }

    public void setInscripcionesCursoCollection(Collection<InscripcionesCurso> inscripcionesCursoCollection) {
        this.inscripcionesCursoCollection = inscripcionesCursoCollection;
    }

    public Persona getIdpersona() {
        return idpersona;
    }

    public void setIdpersona(Persona idpersona) {
        this.idpersona = idpersona;
    }

    public Collection<InscripcionesCarrera> getInscripcionesCarreraCollection() {
        return inscripcionesCarreraCollection;
    }

    public void setInscripcionesCarreraCollection(Collection<InscripcionesCarrera> inscripcionesCarreraCollection) {
        this.inscripcionesCarreraCollection = inscripcionesCarreraCollection;
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
        if (!(object instanceof Alumno)) {
            return false;
        }
        Alumno other = (Alumno) object;
        if ((this.identificador == null && other.identificador != null) || (this.identificador != null && !this.identificador.equals(other.identificador))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.Alumno[ identificador=" + identificador + " ]";
    }
    
}
