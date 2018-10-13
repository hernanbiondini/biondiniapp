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
public class ImparteCursoPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "iddocente")
    private int iddocente;
    @Basic(optional = false)
    @Column(name = "idcurso")
    private int idcurso;
    @Basic(optional = false)
    @Column(name = "fechadesde")
    @Temporal(TemporalType.DATE)
    private Date fechaDesde;
    @Basic(optional = false)
    @Column(name = "fechahasta")
    @Temporal(TemporalType.DATE)
    private Date fechaHasta;

    public ImparteCursoPK() {
    }

    public int getIddocente() {
        return iddocente;
    }

    public void setIddocente(int iddocente) {
        this.iddocente = iddocente;
    }

    public int getIdcurso() {
        return idcurso;
    }

    public void setIdcurso(int idcurso) {
        this.idcurso = idcurso;
    }

    public Date getFechaDesde() {
        return fechaDesde;
    }

    public void setFechaDesde(Date fechaDesde) {
        this.fechaDesde = fechaDesde;
    }

    public Date getFechaHasta() {
        return fechaHasta;
    }

    public void setFechaHasta(Date fechaHasta) {
        this.fechaHasta = fechaHasta;
    }

    public ImparteCursoPK(int iddocente, int idcurso, Date fechaDesde, Date fechaHasta) {
        this.iddocente = iddocente;
        this.idcurso = idcurso;
        this.fechaDesde = fechaDesde;
        this.fechaHasta = fechaHasta;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + this.iddocente;
        hash = 89 * hash + this.idcurso;
        hash = 89 * hash + Objects.hashCode(this.fechaDesde);
        hash = 89 * hash + Objects.hashCode(this.fechaHasta);
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
        final ImparteCursoPK other = (ImparteCursoPK) obj;
        if (this.iddocente != other.iddocente) {
            return false;
        }
        if (this.idcurso != other.idcurso) {
            return false;
        }
        if (!Objects.equals(this.fechaDesde, other.fechaDesde)) {
            return false;
        }
        if (!Objects.equals(this.fechaHasta, other.fechaHasta)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ImparteCursoPK{" + "iddocente=" + iddocente + ", idcurso=" + idcurso + ", fechaDesde=" + fechaDesde + ", fechaHasta=" + fechaHasta + '}';
    }

}
