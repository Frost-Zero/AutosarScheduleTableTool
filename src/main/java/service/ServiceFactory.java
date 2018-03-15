package service;

/**
 * Created by Frost-D on 18/3/10.
 */
public class ServiceFactory {

    private static STService _stService;
    private static TaskService _taskService;

//    private static EPService _epService;

    public static STService STService() {
        if (_stService == null) {
            _stService = new STService();
        }
        return _stService;
    }

    public static TaskService taskService() {
        if (_taskService == null) {
            _taskService = new TaskService();
        }
        return _taskService;
    }

//    public static EPService EPService() {
//        if (_epService == null) {
//            _epService = new EPService();
//        }
//        return _epService;
//    }

}
