
import se.wegelius.olpstudenthandler.model.CourseBranch;
import se.wegelius.olpstudenthandler.dao.CourseBranchDao;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author asawe
 */
public class Main {
    public static void main(String[] args){
        CourseBranchDao dao = new CourseBranchDao();
        dao.save(new CourseBranch("Test Branch"));
    }
}
