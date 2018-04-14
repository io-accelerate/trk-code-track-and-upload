package tdl.record_upload;

import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

@Slf4j
class MetricsReportingTask {
    private final Timer metricsTimer;
    private final StringBuilder displayBuffer;
    private final List<MonitoredSubject> monitoredSubjects;

    MetricsReportingTask(List<MonitoredSubject> monitoredSubjects) {
        this.metricsTimer = new Timer("Metrics");
        this.displayBuffer = new StringBuilder();
        this.monitoredSubjects = monitoredSubjects;
    }

    void scheduleReportMetricsEvery(Duration delayBetweenRuns) {
        metricsTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    displayErrors();
                    displayMetrics();
                } catch (Exception e) {
                    log.error("Unexpected problem while gathering metrics: {}", e.getMessage());
                }
            }
        }, 0, delayBetweenRuns.toMillis());
    }

    private void displayErrors() {
        for (MonitoredSubject monitoredSubject : monitoredSubjects) {
            monitoredSubject.displayErrors(log);
        }
    }

    private void displayMetrics() {
        displayBuffer.setLength(0);
        for (MonitoredSubject monitoredSubject : monitoredSubjects) {
            if (monitoredSubject.isActive()) {
                if (displayBuffer.length() > 0) {
                    displayBuffer.append(" | ");
                }

                monitoredSubject.displayMetrics(displayBuffer);
            }
        }
        if(displayBuffer.length() > 0){
            log.info(displayBuffer.toString());
        }
    }

    void cancel() {
        metricsTimer.cancel();
    }
}
