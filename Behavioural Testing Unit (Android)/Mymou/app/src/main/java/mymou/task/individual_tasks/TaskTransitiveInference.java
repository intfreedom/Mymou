package mymou.task.individual_tasks;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import mymou.R;
import mymou.Utils.UtilsSystem;
import mymou.preferences.PreferencesManager;
import mymou.task.backend.TaskInterface;
import mymou.task.backend.UtilsTask;
import java.util.Random;

public class TaskTransitiveInference extends Task implements View.OnClickListener{

    // Debug
    public static String TAG = "TaskTransitiveInference";

    // Identifier for which monkey is currently playing the task
    private static int current_monkey, monkey_o = 0, monkey_v = 1;

    //add
    private GradientDrawable drawable_red, drawable_grey;

    // Scalar if you want to increase the reward on a particular trial (by multiplication)
    private static int rew_scalar = 1;

    // Global task variables
    private static int num_cues = 2;  // The number of cues to be presented on each trial
    private static Button[] cues;  // List of all trial objects for an individual monkey
    private static Button[][] cues_all = {new Button[num_cues], new Button[num_cues]};  // All cues across all monkeys

    //start test, the number of trail trail_number, the number of right choice;
    private static int trail_number = 0;
    private static int right_choice = 0;
    private static double score = 0.0;
    private static PreferencesManager prefManager;
    private String start_sequence= "A";
    private static int right_picture_number = 0;
    private static int wrong_picture_number = 0;
    private static int last_n = 11;
    private static int location_choice = 0;

    /**
     * Function called when task first loaded (before the UI is loaded)
     * Loads the UI components (cues, background etc)
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_task_transitive_inference, container, false);//又activity_task_example修改；
    }

    /**
     *
     * Function called after the UI has been loaded
     * Once this is called you can then make any UI changes you want (moving cues around etc)
     *
     */
    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        logEvent(TAG+" started", callback);

        // Instantiate task objects
        assignObjects();

        // Find which monkey is playing (faceRecog result)
        current_monkey = getArguments().getInt("current_monkey");

        // Load cues for specific monkey, disable cues for other monkeys
        UtilsTask.toggleMonkeyCues(current_monkey, cues_all);
        cues = cues_all[current_monkey];

        // Activate cues
        UtilsSystem.setOnClickListenerLoop(cues, this);

        // Randomise cue locations
        //UtilsTask.randomlyPositionCues(cues,  new UtilsTask().getPossibleCueLocs(getActivity()));
        Random r= new Random();
        location_choice = r.nextInt(2);
        if (location_choice==1){
            cues[0].setX(200);//Monkey O Cue1
            cues[1].setX(600);//Monkey O Cue2
            cues[0].setY(700);//Monkey O Cue1
            cues[1].setY(700);//Monkey O Cue2
        }else {
            cues[1].setX(200);
            cues[0].setX(600);
            //cues[0].setX(800);//just test location;
            cues[1].setY(700);
            cues[0].setY(700);
        }

    }

    /**
     * Load objects of the task
     * In this case, it loads the different cues that have been specified for each monkey
     */
    private void assignObjects() {

        prefManager = new PreferencesManager(getContext());
        prefManager.TransitiveInference();
        // Monkey 0 cues
        cues_all[monkey_o][0] = getView().findViewById(R.id.buttonCue1MonkO);//Monkey O Cue1
        cues_all[monkey_o][1] = getView().findViewById(R.id.buttonCue2MonkO);//Monkey O Cue2

        if(trail_number >= 10){
            score = right_choice/trail_number;
        }

        Random random = new Random();
        int n; //随机选择10个图片中的一个，相邻两次不重复；public int nextInt(int n), value is [0, n)
        do{
            n = random.nextInt(10);
        }while (n==last_n);

        if(prefManager.start_sequence<10){//默认start_sequence=1,开始为A（如果有需要taskseting中设置一个开始的训练集），即1-10,
            start_sequence = "A";
        }else if(prefManager.start_sequence<20){
            start_sequence = "B";
        }else if(prefManager.start_sequence<30){
            start_sequence = "C";
        }else if(prefManager.start_sequence<40){
            start_sequence = "D";
        }else if(prefManager.start_sequence<50){
            start_sequence = "E";
        }else if(prefManager.start_sequence<60){
            start_sequence = "F";
        }else if(prefManager.start_sequence<70){
            start_sequence = "G";
        }

        int[] imageListMapA = {
                R.drawable.ti_1,
                R.drawable.ti_2,
                R.drawable.ti_3,
                R.drawable.ti_4,
                R.drawable.ti_5,
                R.drawable.ti_6,
                R.drawable.ti_7,
                R.drawable.ti_8,
                R.drawable.ti_9,
                R.drawable.ti_10,
        };

        int[] imageListMapB = {
                R.drawable.ti_11,
                R.drawable.ti_12,
                R.drawable.ti_13,
                R.drawable.ti_14,
                R.drawable.ti_15,
                R.drawable.ti_16,
                R.drawable.ti_17,
                R.drawable.ti_18,
                R.drawable.ti_19,
                R.drawable.ti_20,
        };

        int[] imageListMapC = {
                R.drawable.ti_21,
                R.drawable.ti_22,
                R.drawable.ti_23,
                R.drawable.ti_24,
                R.drawable.ti_25,
                R.drawable.ti_26,
                R.drawable.ti_27,
                R.drawable.ti_28,
                R.drawable.ti_29,
                R.drawable.ti_30,
        };

        int[] imageListMapD = {
                R.drawable.ti_31,
                R.drawable.ti_32,
                R.drawable.ti_33,
                R.drawable.ti_34,
                R.drawable.ti_35,
                R.drawable.ti_36,
                R.drawable.ti_37,
                R.drawable.ti_38,
                R.drawable.ti_39,
                R.drawable.ti_40,
        };

        int[] imageListMapE = {
                R.drawable.ti_41,
                R.drawable.ti_42,
                R.drawable.ti_43,
                R.drawable.ti_44,
                R.drawable.ti_45,
                R.drawable.ti_46,
                R.drawable.ti_47,
                R.drawable.ti_48,
                R.drawable.ti_49,
                R.drawable.ti_50,
        };

        int[] imageListMapF = {
                R.drawable.ti_51,
                R.drawable.ti_52,
                R.drawable.ti_53,
                R.drawable.ti_54,
                R.drawable.ti_55,
                R.drawable.ti_56,
                R.drawable.ti_57,
                R.drawable.ti_58,
                R.drawable.ti_59,
                R.drawable.ti_60,
        };

        int[] imageListMapG = {
                R.drawable.ti_61,
                R.drawable.ti_62,
                R.drawable.ti_63,
                R.drawable.ti_64,
                R.drawable.ti_65,
                R.drawable.ti_66,
                R.drawable.ti_67,
                R.drawable.ti_68,
                R.drawable.ti_69,
                R.drawable.ti_70,
        };

        int[][] image_array = {imageListMapA,imageListMapB,imageListMapC,imageListMapD,imageListMapE,imageListMapF,imageListMapG};
        int n_choice = random.nextInt(6);   //[0, 6), i.e.;

        switch (start_sequence) {
            // If they pressed the correct cue, then set the bool to true
            case "A":
                if(score>0.9){  //等于下一个序列；
                    cues_all[monkey_o][0].setBackgroundResource(imageListMapB[n]);
                    cues_all[monkey_o][1].setBackgroundResource(imageListMapC[n]);
                    right_picture_number = 11+n;
                    wrong_picture_number = 21+n;
                    start_sequence = "B";
                    score = 0.0;
                    right_choice = 0;
                    trail_number = 0;
                }else {//等于所选值；
                    cues_all[monkey_o][0].setBackgroundResource(imageListMapA[n]);
                    cues_all[monkey_o][1].setBackgroundResource(imageListMapB[n]);
                    right_picture_number = 1+n;
                    wrong_picture_number = 11+n;
                }
                break;
            case "B":
                if(score>0.9){
                    cues_all[monkey_o][0].setBackgroundResource(imageListMapC[n]);//等于下一个序列；
                    cues_all[monkey_o][1].setBackgroundResource(imageListMapD[n]);//Monkey O Cue2
                    right_picture_number = 21+n;
                    wrong_picture_number = 31+n;
                    start_sequence = "C";
                    score = 0.0;
                    right_choice = 0;
                    trail_number = 0;
                }else {
                    cues_all[monkey_o][0].setBackgroundResource(imageListMapB[n]);//等于所选值；
                    cues_all[monkey_o][1].setBackgroundResource(imageListMapC[n]);//Monkey O Cue2
                    right_picture_number = 11+n;
                    wrong_picture_number = 21+n;
                }
                break;
            case "C":
                if(score>0.9){
                    cues_all[monkey_o][0].setBackgroundResource(imageListMapD[n]);//等于下一个序列；
                    cues_all[monkey_o][1].setBackgroundResource(imageListMapE[n]);//Monkey O Cue2
                    right_picture_number = 31+n;
                    wrong_picture_number = 41+n;
                    start_sequence = "D";
                    score = 0.0;
                    right_choice = 0;
                    trail_number = 0;
                }else {
                    cues_all[monkey_o][0].setBackgroundResource(imageListMapC[n]);//等于所选值；
                    cues_all[monkey_o][1].setBackgroundResource(imageListMapD[n]);//Monkey O Cue2
                    right_picture_number = 21+n;
                    wrong_picture_number = 31+n;
                }
                break;
            case "D":
                if(score>0.9){
                    cues_all[monkey_o][0].setBackgroundResource(imageListMapE[n]);//等于下一个序列；
                    cues_all[monkey_o][1].setBackgroundResource(imageListMapF[n]);//Monkey O Cue2
                    right_picture_number = 41+n;
                    wrong_picture_number = 51+n;
                    start_sequence = "E";
                    score = 0.0;
                    right_choice = 0;
                    trail_number = 0;
                }else {
                    cues_all[monkey_o][0].setBackgroundResource(imageListMapD[n]);//等于所选值；
                    cues_all[monkey_o][1].setBackgroundResource(imageListMapE[n]);//Monkey O Cue2
                    right_picture_number = 31+n;
                    wrong_picture_number = 41+n;
                }
                break;
            case "E":
                if(score>0.9){
                    cues_all[monkey_o][0].setBackgroundResource(imageListMapF[n]);//等于下一个序列；
                    cues_all[monkey_o][1].setBackgroundResource(imageListMapG[n]);//Monkey O Cue2
                    right_picture_number = 51+n;
                    wrong_picture_number = 61+n;
                    start_sequence = "F";
                    score = 0.0;
                    right_choice = 0;
                    trail_number = 0;
                }else {
                    cues_all[monkey_o][0].setBackgroundResource(imageListMapE[n]);//等于所选值；
                    cues_all[monkey_o][1].setBackgroundResource(imageListMapF[n]);//Monkey O Cue2
                    right_picture_number = 41+n;
                    wrong_picture_number = 51+n;
                }
                break;
            default:
                if(score>0.9){
                    cues_all[monkey_o][0].setBackgroundResource(image_array[n_choice][n]);//等于所选值；
                    cues_all[monkey_o][1].setBackgroundResource(image_array[n_choice+1][n]);
                    right_picture_number = n_choice*10+1+n;
                    wrong_picture_number = (n_choice+1)*10+1+n;
                }else {
                    cues_all[monkey_o][0].setBackgroundResource(imageListMapF[n]);//等于所选值；
                    cues_all[monkey_o][1].setBackgroundResource(imageListMapG[n]);//Monkey O Cue2
                    right_picture_number = 51+n;
                    wrong_picture_number = 61+n;
                }
                break;
        }

        cues_all[monkey_v][0] = getView().findViewById(R.id.buttonCue1MonkV);
        cues_all[monkey_v][1] = getView().findViewById(R.id.buttonCue2MonkV);

        trail_number += 1;
    }

    /**
     * Called whenever a cue is pressed by a subject
     * So then loads the next appropriate stage of the task depending on what cue was selected
     */
    @Override
    public void onClick(View view) {

        // Always disable all cues after a press as monkeys love to cheat
        UtilsTask.toggleCues(cues, false);

        // Reset timer for idle timeout on each press
        callback.resetTimer_();

        // Now decide what to do based on what cue pressed
        boolean successfulTrial = false;
        int picture_number_pressed = wrong_picture_number;
        //String answer_results = "Wrong";
        switch (view.getId()) {//图片的ID????
            // If they pressed the correct cue, then set the bool to true
            case R.id.buttonCue1MonkO:
                successfulTrial = true;
                right_choice += 1;
                picture_number_pressed = right_picture_number;
                //answer_results = "Right";
                break;
            case R.id.buttonCue2MonkV:
                successfulTrial = true;
                break;
        }

        //String note = "PictureNumber:"+picture_number_pressed+", ("+right_picture_number+","+wrong_picture_number+")-"+answer_results;
        // Log screen press
        String note = "PictureNumber:"+picture_number_pressed;
        if(location_choice==0){//==0使，正确的选项在右边；
            note += ", ("+wrong_picture_number+","+right_picture_number+")";
        }else{
            note += ", ("+right_picture_number+","+wrong_picture_number+")";
        }
        logEvent(""+note, callback);

        // Tell parent (TrialManager.java) the outcome of the trial, which will then respond accordingly
        // (e.g. give reward if correct, set up for next trial etc)
        endOfTrial(successfulTrial, rew_scalar, callback);
    }

    /**
     * This is static code repeated in each task that enables communication between the individual
     * task and the parent TaskManager.java which handles the backend utilities (reward delivery,
     * selfie processing, intertrial intervals, etc etc)
     */
    TaskInterface callback;
    public void setFragInterfaceListener(TaskInterface callback) {
        this.callback = callback;
    }
}
