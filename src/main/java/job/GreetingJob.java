package job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/*
* A simple job to greet people based on data from JsonDataMap
*/

public class GreetingJob implements Job {

    private int age;
    private String personName;

    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("Hello, " + personName + ". You are " + age + " years old!");
    }

    // Quartz automatically uses setter to provide values for fields based on JobDataMap

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public void setAge(int age) {
        this.age = age;
    }
}