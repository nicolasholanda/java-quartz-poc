import job.GreetingJob;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Date;

public class Main {

    public static void main(String[] args) throws SchedulerException {

        // Schedulers are responsible for performing scheduling-related operations
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();

        JobDetail greetingJob = getGreetingJob();
        Trigger greetingTrigger = getGreetingTrigger();

        scheduler.scheduleJob(greetingJob, greetingTrigger);

        scheduler.start();
    }

    private static Trigger getGreetingTrigger() {
        // ScheduleBuilder is responsible for creating schedules with the specified properties
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInSeconds(5)
                .repeatForever();

        /* Trigger are used to trigger execution of jobs. It requires a schedule builder.
        It may also have a JobDataMap to pass parameters to jobs triggered by this trigger. */
        // Starts after 3 seconds
        // Ends after 40 seconds
        return TriggerBuilder.newTrigger()
                .withIdentity("myTrigger", "myGroup")
                .startAt(new Date(System.currentTimeMillis() + 3_000)) // Starts after 3 seconds
                .withSchedule(scheduleBuilder)
                .endAt(new Date(System.currentTimeMillis() + 15_000)) // Ends after 40 seconds
                .build();
    }

    private static JobDetail getGreetingJob() {
        /* Job executes a task defined in its execute method
        A job instance may have a map of parameters, called JobDataMap */
        return JobBuilder.newJob(GreetingJob.class)
                .withIdentity("myGreetingJob", "myGroup")
                .usingJobData("personName", "John Doe")
                .usingJobData("age", 25)
                .build();
    }
}
