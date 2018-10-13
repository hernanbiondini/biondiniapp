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
@Table(name = "docente")
@NamedQueries({
      @NamedQuery(name = "Docente.findAll", query = "SELECT a FROM Docente a")
    , @NamedQuery(name = "Docente.findByIdentificador", query = "SELECT a FROM Docente a WHERE a.identificador = :identificador")
    , @NamedQuery(name = "Docente.findByMatricula", query = "SELECT a FROM Docente a WHERE a.matricula = :matricula")})
public class Docente implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "identificador")
    private Integer identificador;
    @Basic(optional = false)
    @Column(name = "matricula")
    private int matricula;
    
    @JoinColumn(name = "idpersona", referencedColumnName = "identificador")
    @OneToOne
    private Persona idpersona;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "docente")
    private Collection<ImparteCurso> imparteCurso;

    public Docente() {
    }

    public Docente(int matricula, Persona idpersona) {
        this.matricula = matricula;
        this.idpersona = idpersona;
    }

    public Docente(Integer identificador) {
        this.identificador = identificador;
    }

    public Docente(Integer identificador, int matricula) {
        this.identificador = identificador;
        this.matricula = matricula;
    }

    public Integer getIdentificador() {
        return identificador;
    }

    public void setIdentificador(Integer identificador) {
        this.identificador = identificador;
    }

    public int getMatricula() {
        return matricula;
    }

    public void setMatricula(int matricula) {
        this.matricula = matricula;
    }

    public Persona getIdpersona() {
        return idpersona;
    }

    public void setIdpersona(Persona idpersona) {
        this.idpersona = idpersona;
    }

    public Collection<ImparteCurso> getImparteCurso() {
        return imparteCurso;
    }

    public void setImparteCurso(Collection<ImparteCurso> imparteCurso) {
        this.imparteCurso = imparteCurso;
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
        if (!(object instanceof Docente)) {
            return false;
        }
        Docente other = (Docente) object;
        if ((this.identificador == null && other.identificador != null) || (this.identificador != null && !this.identificador.equals(other.identificador))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.Docente[ identificador=" + identificador + " ]";
    }

}
