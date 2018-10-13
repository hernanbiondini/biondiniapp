/**
 *
 * @author hernanbiondini
 */
package Modelo;

import java.io.Serializable;
import java.util.Objects;
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
@Table(name = "imparte_curso")
@NamedQueries({
    @NamedQuery(name = "ImparteCurso.findAll", query = "SELECT ic FROM ImparteCurso ic")
    , @NamedQuery(name = "ImparteCurso.findByIddocente", query = "SELECT ic FROM ImparteCurso ic WHERE ic.imparteCursoPK.iddocente = :iddocente")
    , @NamedQuery(name = "ImparteCurso.findByIdcurso", query = "SELECT ic FROM ImparteCurso ic WHERE ic.imparteCursoPK.idcurso = :idcurso")
    , @NamedQuery(name = "ImparteCurso.findByFechadesde", query = "SELECT ic FROM ImparteCurso ic WHERE ic.imparteCursoPK.fechaDesde = :fechadesde")
    , @NamedQuery(name = "ImparteCurso.findByFechahasta", query = "SELECT ic FROM ImparteCurso ic WHERE ic.imparteCursoPK.fechaHasta = :fechahasta")

})
public class ImparteCurso implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ImparteCursoPK imparteCursoPK;
    @JoinColumn(name = "iddocente", referencedColumnName = "identificador", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Docente docente;
    @JoinColumn(name = "idcurso", referencedColumnName = "identificador", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Curso curso;

    public ImparteCurso() {
    }

    public ImparteCursoPK getImparteCursoPK() {
        return imparteCursoPK;
    }

    public void setImparteCursoPK(ImparteCursoPK imparteCursoPK) {
        this.imparteCursoPK = imparteCursoPK;
    }

    public Docente getDocente() {
        return docente;
    }

    public void setDocente(Docente docente) {
        this.docente = docente;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + Objects.hashCode(this.imparteCursoPK);
        hash = 59 * hash + Objects.hashCode(this.docente);
        hash = 59 * hash + Objects.hashCode(this.curso);
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
        final ImparteCurso other = (ImparteCurso) obj;
        if (!Objects.equals(this.imparteCursoPK, other.imparteCursoPK)) {
            return false;
        }
        if (!Objects.equals(this.docente, other.docente)) {
            return false;
        }
        if (!Objects.equals(this.curso, other.curso)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ImparteCurso{" + "imparteCursoPK=" + imparteCursoPK + ", docente=" + docente + ", curso=" + curso + '}';
    }

}
