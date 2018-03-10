package service;

/**
 * Created by Frost-D on 18/3/10.
 */
public class ServiceFactory {

    private static STService _stService;

//    private static EPService _epService;

    public static STService STService() {
        if (_stService == null) {
            _stService = new STService();
        }
        return _stService;
    }

//    public static EPService EPService() {
//        if (_epService == null) {
//            _epService = new EPService();
//        }
//        return _epService;
//    }

}
