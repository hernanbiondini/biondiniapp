package servicios;

import Modelo.Alumno;
import Modelo.Curso;
import Modelo.Docente;
import Modelo.ImparteCurso;
import Modelo.InscripcionesCurso;
import Modelo.Persona;
import controller.AlumnoJpaController;
import controller.ImparteCursoJpaController;
import controller.InscripcionesCursoJpaController;
import controller.PersonaJpaController;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author hernanbiondini
 */
public class Servicios {

    public String guardarAlunoPersona(Persona p, Alumno a) throws Exception {
        String resultado = "OK";
        //resultado = PersonaJpaController.getInstance().guardarAlunoPersona(p, a);
        PersonaJpaController.getInstance().guardarPersona(p);
        a.setIdpersona(p);
        AlumnoJpaController.getInstance().guardarAlumno(a);
        return resultado;
    }

    public Persona buscarPersona(Integer id) {
        return PersonaJpaController.getInstance().findPersona(id);
    }

    public void editarPersona(Persona p) throws Exception {
        PersonaJpaController.getInstance().updatePersona(p);
        AlumnoJpaController.getInstance().updateAlumno(p.getAlumno());
    }

    public void eliminarPersona(Persona p) throws Exception {
        AlumnoJpaController.getInstance().removeAlumno(p.getAlumno());
        PersonaJpaController.getInstance().removePersona(p);
    }

    public void iscribirseCurso(InscripcionesCurso ic) throws Exception {
        InscripcionesCursoJpaController.getInstance().create(ic);
    }

    // Inscripciones actuales a carreras y cursos,  estado de cursos anteriores, 
    // promedio general por carrera tomando los cursos ya aprobados
    public void estadoAcademico(Persona p) throws Exception {

    }

    // Para una asignatura(curso) dado, los alumnos inscriptos y el docente correspondiente
    public void estadoAsignatura(Curso c, Date fechaComienzoCurso, Date fechaImparteDesde, Date fechaImparteHasta) throws Exception {
        // Buscar alumnos inscriptos
        List<InscripcionesCurso> ic = new ArrayList<InscripcionesCurso>();
        ic = InscripcionesCursoJpaController.getInstance().getPorIdCursoYFecha(c.getIdentificador(), fechaComienzoCurso);
        for (InscripcionesCurso r : ic) {
            System.out.println("Legajo: " + r.getAlumno().getLegajo());
        }
        // Buscar docente
        ImparteCurso inscur = new ImparteCurso();
        inscur = ImparteCursoJpaController.getInstance().getPorIdCursoYFecha(c.getIdentificador(), fechaImparteDesde, fechaImparteHasta);
        Docente d  = new Docente();
        d = inscur.getDocente();
        System.out.println("Matricula: " + d.getMatricula());
        
    }

}
