package com.glsc.akkaflow;


import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.PoisonPill;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.glsc.akkaflow.beans.Task;
import com.glsc.akkaflow.extension.SpringExtension;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Random;

/**
 * Tool to trigger messages passed to actors.
 */
//@Configuration
//@EnableAutoConfiguration
//@ComponentScan("com.glsc.akkaflow.configuration")
@SpringBootApplication
public class AkkaApplication /*extends SpringBootServletInitializer*/ {


//    @Bean
//    public ServletRegistrationBean dispatcherRegistration(DispatcherServlet dispatcherServlet) {
//        ServletRegistrationBean registration = new ServletRegistrationBean(
//                dispatcherServlet);
//        registration.addUrlMappings("*.html");
//
//        return registration;
//    }


    public static void main(String[] args) throws Exception {

        ApplicationContext context =
                SpringApplication.run(AkkaApplication.class, args);

        ActorSystem system = context.getBean(ActorSystem.class);

        final LoggingAdapter log = Logging.getLogger(system, "Application");

        log.info("Starting up");

        SpringExtension ext = context.getBean(SpringExtension.class);

        // Use the Spring Extension to create props for a named actor bean
        ActorRef supervisor = system.actorOf(
                ext.props("supervisor").withMailbox("akka.priority-mailbox"), "supervisor");

        for (int i = 1; i <= 1000; i++) {
            Task task = new Task("payload " + i, new Random().nextInt(99));
//            log.info("TASK==> {}", task);
            supervisor.tell(task, ActorRef.noSender());
        }

        // Poison pill will be queued with a priority of 100 as the last
        // message
        supervisor.tell(PoisonPill.getInstance(), ActorRef.noSender());

        while (!supervisor.isTerminated()) {
            Thread.sleep(1000);
        }

        log.info("Created {} tasks", context.getBean(JdbcTemplate.class)
                .queryForObject("SELECT COUNT(*) FROM tasks", Integer.class));

        log.info("Shutting down");

        system.terminate();
        system.awaitTermination();
    }

//    @Override
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//        return application.sources(applicationClass);
//    }
//
//    private static Class<AkkaApplication> applicationClass = AkkaApplication.class;
}
