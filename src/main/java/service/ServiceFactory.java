package service;

/**
 * Created by Frost-D on 18/3/10.
 */
public class ServiceFactory {

    private static STService _stService;
    private static TaskService _taskService;
    private static DataCalcService _dataCalcService;
    private static VeriEPService _veriEPService;
    private static AutomationService _automationService;

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

    public static DataCalcService dataCalcService() {
        if (_dataCalcService == null) {
            _dataCalcService = new DataCalcService();
        }
        return _dataCalcService;
    }

    public static VeriEPService veriEPService() {
        if (_veriEPService == null) {
            _veriEPService = new VeriEPService();
        }
        return _veriEPService;
    }

    public static AutomationService automationService() {
        if (_automationService == null) {
            _automationService = new AutomationService();
        }
        return _automationService;
    }

}
