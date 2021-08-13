package declarativex.delivery;

import java.util.logging.Logger;


public class AppLog {
    private Logger log = null;

    public AppLog(Class<?> newsServiceTestPart2Class) {

        log=Logger.getLogger(newsServiceTestPart2Class.getName());
    }


    public void warning(Throwable t) {
        log.warning(t.toString());
    }
    public void warn(Throwable t) {
        log.warning(t.toString());
    }
    public void error(Throwable t) {
        log.severe(t.toString());
    }

    public void info(String msg) {
        System.out.println("info printing" + msg);
        log.info(msg);
    }
}
