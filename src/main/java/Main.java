import job.GreetingJob;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Date;

public class Main {

    public static void main(String[] args) throws SchedulerException {

        // Schedulers are responsible for performing scheduling-related operations
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();

        // Using a trigger with a simple schedule
        JobDetail simpleGreetingJob = getGreetingJob("simpleGreetingJob");
        Trigger simpleGreetingTrigger = getSimpleGreetingTrigger();

        scheduler.scheduleJob(simpleGreetingJob, simpleGreetingTrigger);

        // Using a trigger with cron expression schedule
        JobDetail cronGreetingJob = getGreetingJob("cronGreetingJob");
        Trigger cronGreetingTrigger = getCronGreetingTrigger();

        scheduler.scheduleJob(cronGreetingJob, cronGreetingTrigger);

        scheduler.start();
    }

    private static JobDetail getGreetingJob(String name) {
        /* Job executes a task defined in its execute method
        A job instance may have a map of parameters, called JobDataMap */
        return JobBuilder.newJob(GreetingJob.class)
                .withIdentity(name, "myGroup")
                .usingJobData("personName", "John Doe")
                .usingJobData("age", 25)
                .build();
    }

    // Returns a Trigger using Simple schedule
    private static Trigger getSimpleGreetingTrigger() {
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

    // Returns a Trigger using Cron schedule
    private static Trigger getCronGreetingTrigger() {
        // Runs every 10 seconds
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("0/10 * * * * ?");

        return TriggerBuilder.newTrigger()
                .withIdentity("myCronTrigger", "myGroup")
                .withSchedule(scheduleBuilder)
                .build();
    }
}
