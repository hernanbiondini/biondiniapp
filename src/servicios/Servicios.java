
package servicios;

import Modelo.Alumno;
import Modelo.Persona;
import controller.AlumnoJpaController;
import controller.PersonaJpaController;

/**
 *
 * @author hernanbiondini
 */
public class Servicios {

    public String guardarAlunoPersona(Persona p, Alumno a)throws Exception{
        String resultado = "OK";
        //resultado = PersonaJpaController.getInstance().guardarAlunoPersona(p, a);
        PersonaJpaController.getInstance().guardarPersona(p);
        a.setIdpersona(p);
        AlumnoJpaController.getInstance().guardarAlumno(a);
        return resultado;
    }
    
    
    public Persona buscarPersona(Integer id){  
        return PersonaJpaController.getInstance().findPersona(id);
}

 public void editarPersona(Persona p) throws Exception{  
        PersonaJpaController.getInstance().updatePersona(p); 
        AlumnoJpaController.getInstance().updateAlumno(p.getAlumno()); 
}
 
 public void eliminarPersona(Persona p) throws Exception{  
         AlumnoJpaController.getInstance().removeAlumno(p.getAlumno()); 
         PersonaJpaController.getInstance().removePersona(p);        
}
 
}