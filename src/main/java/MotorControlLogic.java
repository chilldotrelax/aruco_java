// Author: Andy Huang
// E-Mail: ahuang2@hawaii.edu
// Last Revision: 9 - 21 - 26


public class MotorControlLogic {

    private MotorControlLogic(){}

    public static double[] computeMotorSpeed(double err_x, int curr_size, int target_size){
        final double[] returned_Arr = new double[2];

        final double KP_TURN = 0.6;
        final double KP_DRIVE = 0.005;

        double leftMotor,rightMotor;

        double fwdSpeed = 0.0;

        // Calculations to correct error.
        final double TURN_SPEED = KP_TURN * err_x;
        final int SIZE_ERROR = target_size - curr_size;

        if (Math.abs(SIZE_ERROR) < 15){
            fwdSpeed = 0.0;
        }
        else{
            fwdSpeed = KP_DRIVE * SIZE_ERROR;
            //
        }

        returned_Arr[0] = fwdSpeed + TURN_SPEED;
        returned_Arr[1] = fwdSpeed - TURN_SPEED;

        return returned_Arr;
    }

}
