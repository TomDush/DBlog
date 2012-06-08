package fr.dush.test.dblog.aspects;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Named;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import etm.core.configuration.BasicEtmConfigurator;
import etm.core.configuration.EtmManager;
import etm.core.monitor.EtmMonitor;
import etm.core.monitor.EtmPoint;
import etm.core.renderer.SimpleTextRenderer;

@Named
@Aspect
public class TimingMonitor {

	private EtmMonitor monitor;

	@PostConstruct
	public void configure() {
		BasicEtmConfigurator.configure(true);
		monitor = EtmManager.getEtmMonitor();
		monitor.start();
	}

	@PreDestroy
	public void drop() {
		displayCounts();

	}

	public void displayCounts() {
		monitor.render(new SimpleTextRenderer());
		monitor.stop();
	}

	@Around("@annotation(fr.dush.test.dblog.aspects.MonitorExecutionTime)")
	public Object doBasicProfiling(ProceedingJoinPoint pjp) throws Throwable {

		EtmPoint point = monitor.createPoint(pjp.getSignature().getDeclaringTypeName() + ":" + pjp.getSignature().getName());

		try {
			return pjp.proceed();
		} finally {
			point.collect();

		}
	}
}
