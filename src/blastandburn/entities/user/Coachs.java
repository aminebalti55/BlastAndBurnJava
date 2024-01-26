package blastandburn.entities.user;

import java.sql.Date;

/**
 *
 * @author fatma
 */
public class Coachs extends User{  
    
    private String speciality;
    private boolean canCreateSession;


    public Coachs(int userId, String email, String firstName, String lastName) {
        super(userId, email, firstName, lastName);
    }
    
    
    //getters & setters

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public boolean isCanCreateSession() {
        return canCreateSession;
    }

    public void setCanCreateSession(boolean canCreateSession) {
        this.canCreateSession = canCreateSession;
    }

  
    @Override
    public String toString() {
        return "Coachs{" + "speciality=" + speciality + ", canCreateSession=" + canCreateSession + '}';
    }
    
    
    
  
}
