package mymou.task.backend;

public interface TaskInterface {
    void resetTimer_();
    void trialEnded_(String outcome, double rew_scalar);
    void logEvent_(String event);
}
