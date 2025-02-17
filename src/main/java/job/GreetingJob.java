package job;

import org.quartz.*;

/*
* A simple job to greet people based on data from JsonDataMap
*/

public class GreetingJob implements Job {

    private int age;
    private String personName;


    public void execute(JobExecutionContext context) throws JobExecutionException {
        TriggerKey triggerKey = context.getTrigger().getKey();
        System.out.printf("[%s] Hello, %s. You are %s years old!%n", triggerKey, personName, age);
    }

    // Quartz automatically uses setter to provide values for fields based on JobDataMap

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public void setAge(int age) {
        this.age = age;
    }
}