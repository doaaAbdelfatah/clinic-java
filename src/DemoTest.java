import db.Clinic;
import db.DBConfig;
import db.Patient;

import java.util.ArrayList;

public class DemoTest {

    private String doctorName;

    public DemoTest() {
        Patient p = new Patient();
        p.setId(10);
        System.out.println(p.getName());

        ArrayList<Clinic> arrayList =new ArrayList<>();
        ArrayList<Patient> patientArrayList = new ArrayList<>();


        for (Clinic c :arrayList){

        }


    }
}
