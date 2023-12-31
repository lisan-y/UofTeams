package log_in.interface_adapters;

import log_in.use_case.LogInInputBoundary;
import log_in.use_case.LogInRequestModel;

/**
 * takes user data and coverts in into information that can be passed to the Use Case
 */
public class LogInController {

    private final LogInInputBoundary interactor;


    public LogInController(LogInInputBoundary interactor){
        this.interactor = interactor;

    }

    //Initializes the LogIn use case
    public void logInInitializer(LogInUserInputData data) {
        LogInRequestModel requestModel = new LogInRequestModel(data.getEmail(), data.getPass());
        interactor.logIn(requestModel);
    }
}


